package bytenote;

import java.io.File;

import bytenote.note.NotePanel;
import bytenote.note.types.NoteTypes;
import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class MainPanel extends BorderPane {
	
	public C57runService c57run;
	
	public NotePanel todoPanel;
	public NotePanel doingPanel;
	public NotePanel donePanel;
	public ScrollPane todoScrollPane;
	public ScrollPane doingScrollPane;
	public ScrollPane doneScrollPane;
	
	
	public InfoPanel infoPanel;
	public ControlPanel controlPanel;
	
	public MainPanel() {
		super();
//		setWidth(JFXMain.mainStage.getWidth()-50);
//		setHeight(JFXMain.mainStage.getHeight()-50);
		
		setVisible(true);
		
		controlPanel = new ControlPanel();
		todoPanel = new NotePanel();
		doingPanel = new NotePanel();
		donePanel = new NotePanel();
		infoPanel = new InfoPanel();
		
		todoScrollPane = new ScrollPane(todoPanel);
		todoScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		todoScrollPane.setFitToWidth(true);
		todoScrollPane.setPrefWidth(this.getWidth()*NotePanel.paneToWin);
		
		doingScrollPane = new ScrollPane(doingPanel);
		doingScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		doingScrollPane.setPrefSize(this.getWidth()*NotePanel.paneToWin, this.getHeight());
		doingScrollPane.setFitToWidth(true);
		
		doneScrollPane = new ScrollPane(donePanel);
		doneScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		doneScrollPane.setPrefSize(this.getWidth()*NotePanel.paneToWin, this.getHeight());
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
		NoteTypes.addToMap("(default)", Color.rgb(255, 255, 150) );
//		revalidate();
//		repaint();
//		setWidth(JFXMain.mainStage.getWidth()-50);
//		setHeight(JFXMain.mainStage.getHeight()-32);
		
		todoScrollPane.setPrefSize(this.getWidth()*NotePanel.paneToWin, this.getHeight());
		doingScrollPane.setPrefSize(this.getWidth()*NotePanel.paneToWin, this.getHeight());
		doneScrollPane.setPrefSize(this.getWidth()*NotePanel.paneToWin, this.getHeight());

		
		controlPanel.c57run();
		todoPanel.c57run();
		doingPanel.c57run();
		donePanel.c57run();
		infoPanel.c57run();
		
		if(ByteNoteMain.isSaved) {
			JFXMain.mainStage.setTitle(ByteNoteMain.name+"   "+new File(ByteNoteMain.filePath).getAbsolutePath());
		} else {
//			setTitle("C57note64 *UNSAVED*");
			JFXMain.mainStage.setTitle(ByteNoteMain.name+"   *"+new File(ByteNoteMain.filePath).getAbsolutePath()+"*");
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
							MainPanel.this.c57run();
						}
					});
					return null;
				}
			};
		}
		
	}
	
}
