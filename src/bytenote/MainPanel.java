package bytenote;

import java.io.File;

import bytenote.note.NotePanel;
import bytenote.note.types.NoteTypes;
import bytenote.update.UpdateHandler;
import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;

public class MainPanel extends BorderPane {
	
	public _runService _run;
	
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
	
	public void _run() {
		NoteTypes.addDefaultColor();
		
		todoScrollPane.setPrefSize(this.getWidth()*NotePanel.paneToWin, this.getHeight());
		doingScrollPane.setPrefSize(this.getWidth()*NotePanel.paneToWin, this.getHeight());
		doneScrollPane.setPrefSize(this.getWidth()*NotePanel.paneToWin, this.getHeight());

		
		controlPanel._run();
		todoPanel._run();
		doingPanel._run();
		donePanel._run();
		infoPanel._run();
		
		if(ByteNoteMain.savedData != null && ByteNoteMain.savedData.isEqual(NoteData.getCurrentData())) {
			ByteNoteMain.isSaved = true;
			JFXMain.mainStage.setTitle(ByteNoteMain.name+"   "+new File(ByteNoteMain.filePath).getAbsolutePath());
		} else {
			ByteNoteMain.isSaved = false;
			JFXMain.mainStage.setTitle(ByteNoteMain.name+"   *"+new File(ByteNoteMain.filePath).getAbsolutePath()+"*");
		}
		
		UpdateHandler._run();
		
	}
	
	
	public class _runService extends ScheduledService<Void> {

		@Override
		protected Task<Void> createTask() {
			return new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					Platform.runLater( () -> {
							MainPanel.this._run();
						}
					);
					return null;
				}
			};
		}
		
	}
	
}
