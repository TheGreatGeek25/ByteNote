package bytenote.update;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class UpdateMain extends Application {

	public static final String name = "ByteNote Update";
	public static final double minHeight = 200;
	public static final double minWidth = 400;
	public static final double prefHeight = minHeight;
	public static final double prefWidth = minWidth;
	
	public static File jarFile;
	public static URL updateSite;
	public static File downloadFile;
	
	public ProgressBar loading;
	public BorderPane bp;
	public DownloadTask download;
	public Button cancelButton;
	public Label loadingLabel;
	public VBox loadingVBox;
	public Stage stage;
	
	private boolean setOnClose;
	

	public static void main(String[] args) throws MalformedURLException {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {}
		if(args.length < 2) {
			throw new IllegalArgumentException("Invalid number of parameters. There must be at least two parameters.");
		}
		File jarFile = new File(args[0]);
		UpdateMain.jarFile = jarFile;
		URL url = new URL(args[1]);
		updateSite = url;
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		primaryStage.setTitle(name);
		primaryStage.getIcons().add( new Image(this.getClass().getResourceAsStream("logo32.png")) );
		primaryStage.setResizable(true);
		primaryStage.setMinHeight(minHeight);
		primaryStage.setMinWidth(minWidth);
		primaryStage.setHeight(prefHeight);
		primaryStage.setWidth(prefWidth);
		
		bp = new BorderPane();
		loading = new ProgressBar(0);
		loading.setPrefHeight(30);
		loadingLabel = new Label();
		loadingLabel.setPrefHeight(20);
		loadingLabel.setAlignment(Pos.CENTER);
		loadingVBox = new VBox(loading, loadingLabel);
		loadingVBox.setVisible(true);
		bp.setCenter(loadingVBox);
//		jarFile.delete();
		downloadFile = Files.createTempFile("ByteNote", ".jar").toFile();
		download = new DownloadTask(new URL(updateSite.toString()+"/updateJar.jar"), downloadFile);
		
		cancelButton = new Button("Cancel");
		cancelButton.setPrefSize(primaryStage.getWidth(), 50);
		cancelButton.setTextAlignment(TextAlignment.CENTER);
		cancelButton.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(download != null) {
					download.cancel();
					download = null;
				}
				System.exit(0);
			}
		});
		cancelButton.setCancelButton(true);
		bp.setBottom(cancelButton);
		
		Scene scene = new Scene(bp, primaryStage.getWidth(), primaryStage.getHeight());
		primaryStage.setScene(scene);
		
		primaryStage.show();
		
		C57runService c57run = new C57runService();
		c57run.setRestartOnFailure(true);	
		c57run.start();
		
		runTask(download);
	}
	
	public void c57run() {
		if(!setOnClose) {
			stage.setOnCloseRequest( new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					cancelButton.fire();
				}
			});
			setOnClose = true;
		}
		cancelButton.setPrefWidth(stage.getWidth());
		loadingLabel.setPrefWidth(stage.getWidth());
		loading.setPrefWidth(stage.getWidth());
		if(download != null) {
			loading.setProgress(download.getProgress());
			loadingLabel.setText(Long.toString(Math.round((float)download.getBytesLoaded()/download.getTotalBytes()*100))+"%   "+Long.toString(download.getBytesLoaded())+"/"+Long.toString(download.getTotalBytes())+" bytes");
			try {
				if(download.isDone() && download.getState() == State.SUCCEEDED) {
					Files.move(downloadFile.toPath(), jarFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
					ProcessBuilder pb = new ProcessBuilder(getJavaHome()+"/bin/java", "-jar", jarFile.getAbsolutePath());
					pb.start();
					System.exit(0);
				} else if(download.getState() == State.FAILED) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.initModality(Modality.APPLICATION_MODAL);
					alert.initOwner(stage);
					alert.setContentText("Please check your internet connection and try again.");
					alert.setHeaderText("Download failed.");
					alert.showAndWait();
					cancelButton.fire();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private Thread runTask(Task<?> task) {
		Thread t = new Thread(task);
		t.setName(task.toString());
		t.start();
		return t;
	}
	
	private String getJavaHome() {
		String home = System.getProperty("java.home");
		return home;
	}
	
	public class C57runService extends ScheduledService<Void> {

		@Override
		protected Task<Void> createTask() {
			return new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					Platform.runLater( new Runnable() {
						@Override
						public void run() {
							UpdateMain.this.c57run();
						}
					});
					return null;
				}
			};
		}
		
	}

}
