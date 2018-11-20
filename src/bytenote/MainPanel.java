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
	private ScrollPane todoScrollPane;
	private ScrollPane doingScrollPane;
	private ScrollPane doneScrollPane;
	
	
	public InfoPanel infoPanel;
	public ControlPanel controlPanel;

	private final JFXMain jfxMain;
	
	public MainPanel(JFXMain jfxMain) {
		super();

		this.jfxMain = jfxMain;

		setVisible(true);
		
		controlPanel = new ControlPanel(jfxMain, this);
		todoPanel = new NotePanel(jfxMain, this);
		doingPanel = new NotePanel(jfxMain, this);
		donePanel = new NotePanel(jfxMain, this);
		infoPanel = new InfoPanel(jfxMain, this);
		
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
			jfxMain.getMainStage().setTitle(ByteNoteMain.name+"   "+new File(ByteNoteMain.filePath).getAbsolutePath());
		} else {
			ByteNoteMain.isSaved = false;
			jfxMain.getMainStage().setTitle(ByteNoteMain.name+"   *"+new File(ByteNoteMain.filePath).getAbsolutePath()+"*");
		}
		
		UpdateHandler._run();
		
	}


	public class _runService extends ScheduledService<Void> {

		@Override
		protected Task<Void> createTask() {
			return new Task<Void>() {
				@Override
				protected Void call() {
					Platform.runLater(MainPanel.this::_run);
					return null;
				}
			};
		}
		
	}
	
}
