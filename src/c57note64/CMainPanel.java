package c57note64;

import java.io.File;

import c57note64.cnote.CNotePanel;
import c57note64.cnote.types.CNoteTypes;
import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class CMainPanel extends BorderPane {
	
	public C57runService c57run;
	
	public CNotePanel todoPanel;
	public CNotePanel doingPanel;
	public CNotePanel donePanel;
	public ScrollPane todoScrollPane;
	public ScrollPane doingScrollPane;
	public ScrollPane doneScrollPane;
	
	
	public CInfoPanel infoPanel;
	public CControlPanel controlPanel;
	
	public CMainPanel() {
		super();
//		setWidth(JFXMain.mainStage.getWidth()-50);
//		setHeight(JFXMain.mainStage.getHeight()-50);
		
		setVisible(true);
		
		controlPanel = new CControlPanel();
		todoPanel = new CNotePanel();
		doingPanel = new CNotePanel();
		donePanel = new CNotePanel();
		infoPanel = new CInfoPanel();
		
		todoScrollPane = new ScrollPane(todoPanel);
		todoScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		todoScrollPane.setFitToWidth(true);
		todoScrollPane.setPrefWidth(this.getWidth()*CNotePanel.paneToWin);
		
		doingScrollPane = new ScrollPane(doingPanel);
		doingScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		doingScrollPane.setPrefSize(this.getWidth()*CNotePanel.paneToWin, this.getHeight());
		doingScrollPane.setFitToWidth(true);
		
		doneScrollPane = new ScrollPane(donePanel);
		doneScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		doneScrollPane.setPrefSize(this.getWidth()*CNotePanel.paneToWin, this.getHeight());
		doneScrollPane.setFitToWidth(true); 
		
		setTop(controlPanel);
		setLeft(todoScrollPane);
		setCenter(doingScrollPane);
		setRight(doneScrollPane);
		setBottom(infoPanel);
		
//		add(controlPanel, BorderLayout.PAGE_START);
//		add(infoPanel, BorderLayout.PAGE_END);
		
			
		
	}
	
	public void c57run() {
		CNoteTypes.addToMap("(default)", Color.rgb(255, 255, 150) );
//		revalidate();
//		repaint();
//		setWidth(JFXMain.mainStage.getWidth()-50);
//		setHeight(JFXMain.mainStage.getHeight()-32);
		
		todoScrollPane.setPrefSize(this.getWidth()*CNotePanel.paneToWin, this.getHeight());
		doingScrollPane.setPrefSize(this.getWidth()*CNotePanel.paneToWin, this.getHeight());
		doneScrollPane.setPrefSize(this.getWidth()*CNotePanel.paneToWin, this.getHeight());

		
		controlPanel.c57run();
		todoPanel.c57run();
		doingPanel.c57run();
		donePanel.c57run();
		infoPanel.c57run();
		
		if(C57note64Main.isSaved) {
			JFXMain.mainStage.setTitle(C57note64Main.name+"   "+new File(C57note64Main.filePath).getAbsolutePath());
		} else {
//			setTitle("C57note64 *UNSAVED*");
			JFXMain.mainStage.setTitle(C57note64Main.name+"   *"+new File(C57note64Main.filePath).getAbsolutePath()+"*");
		}
		
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
							CMainPanel.this.c57run();
						}
					});
					return null;
				}
			};
		}
		
	}
	
}
