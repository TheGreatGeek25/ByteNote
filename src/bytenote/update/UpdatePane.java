package bytenote.update;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

import bytenote.ByteNoteMain;
import bytenote.JFXMain;
import bytenote.update.UpdateHandler.UpdateType;
import javafx.concurrent.Task;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.WindowEvent;

public class UpdatePane extends BorderPane {
	
	private boolean setOnClose = false;
	
	public Button installButton;
	public Button cancelButton;
	public Button exitButton;
	public URL updateSite;
	public WebView wv;
	public ProgressBar loading;
	public VBox loadingVBox;
	public Label loadingLabel;
	public Label loadingTimeLabel;
	public DownloadTask download;
	
	private File downloadFile;
	private String time = "  Calculating time remaining.";
	
	public UpdatePane(URL updateSite) throws IOException {
		this.updateSite = updateSite;
		
		wv = new WebView();
		WebEngine we = wv.getEngine();
		we.load(updateSite.toString()+"/releaseNotes.html");
		setCenter(wv);
		
		exitButton = new Button("Exit");
		exitButton.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				UpdatePane.this.getScene().getWindow().hide();
			}
		});
		setTop(exitButton);
		
		loading = new ProgressBar(0);
		loading.setPrefHeight(30);
		loadingLabel = new Label();
		loadingLabel.setPrefHeight(20);
		loadingLabel.setAlignment(Pos.CENTER);
		loadingTimeLabel = new Label();
		loadingTimeLabel.setPrefHeight(20);
		loadingTimeLabel.setAlignment(Pos.CENTER);
		loadingVBox = new VBox(loading, loadingLabel, loadingTimeLabel);
		loadingVBox.setVisible(false);
		
		installButton = new Button("Install");
		installButton.setPrefSize(this.getWidth(), 50);
		installButton.setTextAlignment(TextAlignment.CENTER);
		installButton.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(JFXMain.confirmExit("continue")) {
					try {
						if(UpdateHandler.getUpdateType() == UpdateType.JAR) {
							File dest = Files.createTempFile("ByteNoteUpdateJar", ".jar").toFile();
							downloadFile = dest;
							download = new DownloadTask(ClassLoader.getSystemResource("ByteNoteJARUpdateModule.jar"), dest);
							runTask(download);
						} else if(UpdateHandler.getUpdateType() == UpdateType.WIN32BIT) {
							File dest = Files.createTempFile("ByteNote32bitUpdate", ".exe").toFile();
							downloadFile = dest;
							download = new DownloadTask(new URL(updateSite.toString()+"/windows32bit.exe"), dest);
							runTask(download);
						}
					} catch (URISyntaxException | IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		setBottom(installButton);
		
		cancelButton = new Button("Cancel");
		cancelButton.setPrefSize(this.getWidth(), 50);
		cancelButton.setTextAlignment(TextAlignment.CENTER);
		cancelButton.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(download != null) {
					download.cancel();
					download = null;
				}
				loadingVBox.setVisible(false);
				UpdatePane.this.setTop(exitButton);
				UpdatePane.this.setBottom(installButton);
			}
		});
		cancelButton.setCancelButton(true);
		
		UpdateHandler.up = this;
	}

	public void _run() {
		if(!setOnClose) {
			this.getScene().getWindow().setOnCloseRequest( new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					cancelButton.fire();
				}
			});
			setOnClose = true;
		}
		exitButton.setPrefWidth(this.getWidth());
		installButton.setPrefWidth(this.getWidth());
		cancelButton.setPrefWidth(this.getWidth());
		loadingLabel.setPrefWidth(this.getWidth());
		loadingTimeLabel.setPrefWidth(this.getWidth());
		try {
			if(UpdateHandler.getUpdateType() == UpdateType.JAR) {
				if(UpdateChecker.isJRECompatible(updateSite)) {
					installButton.setDisable(false);
					installButton.setText("Install");
				} else {
					installButton.setDisable(true);
					installButton.setText("You must have java "+UpdateChecker.getRequiredJREVersion(updateSite)+" or later to install this update.\n"
							+ "Already have java "+UpdateChecker.getRequiredJREVersion(updateSite)+"? Try running \"java -version\" to check your running java version.");
				}
			} else if(UpdateHandler.getUpdateType() == UpdateType.WIN32BIT) {
				installButton.setDisable(false);
			}
		if(download != null) {
			if(getChildrenUnmodifiable().contains(loadingVBox)) {
				setTop(loadingVBox);
				loadingVBox.setVisible(true);
			}
			loading.setProgress(download.getProgress());
			loading.setPrefWidth(this.getWidth());
			if(download.getBytesLoaded() != 0) {
				long secondsLoading = (long) Duration.between(download.getStartTime(), Instant.now()).getSeconds();
				long secondsLeft = (long) ((download.getTotalBytes()-download.getBytesLoaded())/((float)download.getBytesLoaded()/secondsLoading));
				time = String.format("  %d hours %d minutes %d seconds",
						TimeUnit.SECONDS.toHours(secondsLeft), 
						TimeUnit.SECONDS.toMinutes(secondsLeft)-TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(secondsLeft)),
						secondsLeft-TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(secondsLeft)-TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(secondsLeft)))
				);
			}
			loadingLabel.setText(Long.toString(Math.round((float)download.getBytesLoaded()/download.getTotalBytes()*100))+"%   "+Long.toString(download.getBytesLoaded())+"/"+Long.toString(download.getTotalBytes())+" bytes");
			loadingTimeLabel.setText("Download Speed: "+download.getDownloadSpeedString()+"  Time remaining: "+time);
			if(download.isDone() && download.getState() == State.SUCCEEDED) {
				if(UpdateHandler.getUpdateType() == UpdateType.JAR) {
					ProcessBuilder pb = new ProcessBuilder(UpdateChecker.getJavaHome()+"/bin/java", "-jar", downloadFile.getAbsolutePath(), new File(ByteNoteMain.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath(), ByteNoteMain.updateSite.toString());
					pb.start();
					System.exit(0);
				} else if(UpdateHandler.getUpdateType() == UpdateType.WIN32BIT) {
					ProcessBuilder pb = new ProcessBuilder(downloadFile.getAbsolutePath(), "/SILENT", "/CLOSEAPPLICATIONS");
					pb.start();
					System.exit(0);
				}
			} else if(download.getState() == State.FAILED) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.initModality(Modality.APPLICATION_MODAL);
				alert.initOwner(JFXMain.mainStage);
				alert.setContentText("Please check your internet connection and try again.");
				alert.setHeaderText("Download failed.");
				alert.show();
				cancelButton.fire();
			}
		}
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}
		if (download != null && download.isRunning() && getChildren().contains(installButton)) {
			setTop(loadingVBox);
			setBottom(cancelButton);
		} else if ((download == null || download.isCancelled()) && !getChildren().contains(installButton)) {
			setTop(exitButton);
			setBottom(installButton);
		}
	}
	
	private Thread runTask(Task<?> task) {
		Thread t = new Thread(task);
		t.setName(task.toString());
		t.start();
		return t;
	}
	
}
