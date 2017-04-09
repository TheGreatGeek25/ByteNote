package bytenote.update;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;

import bytenote.JFXMain;
import bytenote.update.UpdateHandler.UpdateType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class UpdatePane extends BorderPane {
	
	public Button installButton;
	public Button cancelButton;
	public URL updateSite;
	public WebView wv;
	public ProgressBar loading;
	public DownloadTask download;
	
	private File downloadFile;
	
	public UpdatePane(URL updateSite) throws IOException {
		this.updateSite = updateSite;
		
		
		wv = new WebView();
		WebEngine we = wv.getEngine();
		we.load(updateSite.toString()+"/releaseNotes.html");
		setCenter(wv);
		
		installButton = new Button("Install");
		installButton.setPrefSize(this.getWidth(), 50);
		installButton.setTextAlignment(TextAlignment.CENTER);
		installButton.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(JFXMain.confirmExit("continue")) {
					loading = new ProgressBar(0);
					try {
						if(UpdateHandler.getUpdateType() == UpdateType.JAR) {
							File dest = Files.createTempFile("ByteNoteUpdateJar", ".jar").toFile();
							downloadFile = dest;
							download = new DownloadTask(ClassLoader.getSystemResource("updateJar.jar"), dest);
						} else if(UpdateHandler.getUpdateType() == UpdateType.WIN32BIT) {
							File dest = Files.createTempFile("ByteNote32bitUpdate", ".exe").toFile();
							downloadFile = dest;
							download = new DownloadTask(new URL(updateSite.toString()+"/windows32bit.exe"), dest);
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
				}
			}
		});
		
		UpdateHandler.up = this;
	}

	public void c57run() {
		installButton.setPrefWidth(this.getWidth());
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
			setTop(loading);
			loading.setProgress(download.getProgress());
			if(download.isDone()) {
				if(UpdateHandler.getUpdateType() == UpdateType.JAR) {
					ProcessBuilder pb = new ProcessBuilder(UpdateChecker.getJavaHome()+"/bin/java", "-jar", downloadFile.getAbsolutePath());
					pb.start();
					System.exit(0);
				}// TODO
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
	
}
