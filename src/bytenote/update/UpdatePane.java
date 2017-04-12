package bytenote.update;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;

import bytenote.JFXMain;
import bytenote.update.UpdateHandler.UpdateType;
import javafx.concurrent.Task;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.WindowEvent;

public class UpdatePane extends BorderPane {
	
	private boolean setOnClose = false;
	
	public Button installButton;
	public Button cancelButton;
	public URL updateSite;
	public WebView wv;
	public ProgressBar loading;
	public VBox loadingVBox;
	public Label loadingLabel;
	public DownloadTask download;
	
	private File downloadFile;
	
	public UpdatePane(URL updateSite) throws IOException {
		this.updateSite = updateSite;
		
		wv = new WebView();
		WebEngine we = wv.getEngine();
		we.load(updateSite.toString()+"/releaseNotes.html");
		setCenter(wv);
		
		loading = new ProgressBar(0);
		loading.setPrefHeight(30);
		loadingLabel = new Label();
		loadingLabel.setPrefHeight(20);
		loadingLabel.setAlignment(Pos.CENTER);
		loadingVBox = new VBox(loading, loadingLabel);
		loadingVBox.setVisible(false);
		setTop(loadingVBox);
		
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
							download = new DownloadTask(ClassLoader.getSystemResource("updateJar.jar"), dest);
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
				UpdatePane.this.setBottom(installButton);
			}
		});
		cancelButton.setCancelButton(true);
		
		UpdateHandler.up = this;
	}

	public void c57run() {
		if(!setOnClose) {
			this.getScene().getWindow().setOnCloseRequest( new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					cancelButton.fire();
				}
			});
			setOnClose = true;
		}
		
		installButton.setPrefWidth(this.getWidth());
		cancelButton.setPrefWidth(this.getWidth());
		loadingLabel.setPrefWidth(this.getWidth());
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
			loadingVBox.setVisible(true);
			loading.setProgress(download.getProgress());
			loading.setPrefWidth(this.getWidth());
			loadingLabel.setText(Long.toString(Math.round((float)download.getBytesLoaded()/download.getTotalBytes()*100))+"%   "+Long.toString(download.getBytesLoaded())+"/"+Long.toString(download.getTotalBytes())+" bytes");
			if(download.isDone() && download.getState() == State.SUCCEEDED) {
				if(UpdateHandler.getUpdateType() == UpdateType.JAR) {
					ProcessBuilder pb = new ProcessBuilder(UpdateChecker.getJavaHome()+"/bin/java", "-jar", downloadFile.getAbsolutePath());
					pb.start();
					System.exit(0);
				} else if(UpdateHandler.getUpdateType() == UpdateType.WIN32BIT) {
					ProcessBuilder pb = new ProcessBuilder("TIMEOUT 8 & \""+downloadFile.getAbsolutePath()+"\"");
					pb.start();
					System.exit(0);
				}
			}
		}
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}
		if (download != null && download.isRunning() && getChildren().contains(installButton)) {
			setBottom(cancelButton);
		} else if ((download == null || download.isCancelled()) && !getChildren().contains(installButton)) {
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
