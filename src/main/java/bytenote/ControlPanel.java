package bytenote;

import bytenote.note.types.NoteTypeManagerPanel;
import javafx.geometry.Pos;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class ControlPanel extends HBox {
	
	public MenuBar menuBar;
	
	public Menu file;
	private MenuItem save, saveAs, newFile, open;
	public Menu notes;
	private MenuItem newNote, deleteNote, manageTypes;
	private Menu selection;
	private MenuItem deselect;
	private Menu settings;
	private MenuItem checkUpdates, exit, releaseNotes;
	
	public NoteTypeManagerPanel typeManager;

	private final JFXMain jfxMain;
	private final MainPanel mainPanel;


	public ControlPanel(JFXMain jfxMain, MainPanel mainPanel) {
		super();
		this.jfxMain = jfxMain;
		this.mainPanel = mainPanel;
		setPrefSize(jfxMain.getMainStage().getWidth(), InfoPanel.defaultHeight);
		setBackground( new Background( new BackgroundFill(Color.web("#00ffff"), null, null) ) );
		
		menuBar = new MenuBar();
		
		setAlignment(Pos.CENTER);
		
		file = new Menu("File");
		menuBar.getMenus().add(file);
		
		newFile = new MenuItem("New...");
		newFile.setOnAction(jfxMain.getEventHandlers().NEW_FILE_EVENT);
		newFile.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
		file.getItems().add(newFile);
		
		open = new MenuItem("Open...");
		open.setOnAction(jfxMain.getEventHandlers().OPEN_EVENT);
		file.getItems().add(open);
		
		save = new MenuItem("Save");
		save.setOnAction(jfxMain.getEventHandlers().SAVE_EVENT);
		save.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
		file.getItems().add(save);
		
		saveAs = new MenuItem("Save As...");
		saveAs.setOnAction(jfxMain.getEventHandlers().SAVE_AS_EVENT);
		saveAs.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+S"));
		file.getItems().add(saveAs);
		
		
		
		notes = new Menu("Notes");
		menuBar.getMenus().add(notes);
		
		newNote = new MenuItem("New note...");
		newNote.setOnAction(jfxMain.getEventHandlers().NEW_NOTE_EVENT);
		notes.getItems().add(newNote);
		
		deleteNote = new MenuItem("Delete note");
		deleteNote.setOnAction(jfxMain.getEventHandlers().DELETE_NOTE_EVENT);
		deleteNote.setAccelerator(KeyCombination.keyCombination("DELETE"));
		deleteNote.setDisable(true);
		notes.getItems().add(deleteNote);
				
		notes.getItems().add( new SeparatorMenuItem() );
	
		manageTypes = new MenuItem("Manage types...");
		manageTypes.setOnAction(jfxMain.getEventHandlers().MANAGE_TYPE_EVENT);
		notes.getItems().add(manageTypes);
		
		selection = new Menu("Selection");
		menuBar.getMenus().add(selection);
		
		deselect = new MenuItem("Deselect");
		deselect.setOnAction(jfxMain.getEventHandlers().DESELECT_EVENT);
		deselect.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+A"));
		selection.getItems().add(deselect);
		
		settings = new Menu("Settings");
		menuBar.getMenus().add(settings);
		
		checkUpdates = new MenuItem("Check for Updates...");
		checkUpdates.setOnAction(jfxMain.getEventHandlers().CHECK_FOR_UPDATES_EVENT);
		settings.getItems().add(checkUpdates);
		
		releaseNotes = new MenuItem("Show Release Notes...");
		releaseNotes.setOnAction(jfxMain.getEventHandlers().SHOW_RELEASE_NOTES_EVENT);
		settings.getItems().add(releaseNotes);
		
		exit = new MenuItem("Exit");
		exit.setOnAction(jfxMain.getEventHandlers().EXIT_EVENT);
		settings.getItems().add(exit);
		
		
		getChildren().add(menuBar);
		
		setVisible(true);
		
	}
	
	public void _run() {
		if(typeManager != null) {
			typeManager._run();
			
		}
		if(mainPanel.infoPanel.note != null) {
			deleteNote.setDisable(false);
		} else {
			deleteNote.setDisable(true);
		}
		
	}
	
}
