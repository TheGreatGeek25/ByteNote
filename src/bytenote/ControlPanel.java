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
	public MenuItem save, saveAs, newFile, open;
	public Menu notes;
	public MenuItem newNote, deleteNote, manageTypes;
	public Menu selection;
	public MenuItem deselect;
	public Menu settings;
	public MenuItem checkUpdates, exit, releaseNotes, config;
	
	public NoteTypeManagerPanel typeManager;

	

	
	
	public ControlPanel() {
		super();
		setPrefSize(JFXMain.mainStage.getWidth(), InfoPanel.defaultHeight);
		setBackground( new Background( new BackgroundFill(Color.web("#00ffff"), null, null) ) );
		
		menuBar = new MenuBar();
		
		setAlignment(Pos.CENTER);
		
		file = new Menu("File");
		menuBar.getMenus().add(file);
		
		newFile = new MenuItem("New...");
		newFile.setOnAction(new CAction("newFileAction") );
		newFile.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
		file.getItems().add(newFile);
		
		open = new MenuItem("Open...");
		open.setOnAction( new CAction("openAction") );
		file.getItems().add(open);
		
		save = new MenuItem("Save");
		save.setOnAction( new CAction("saveAction") );
		save.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
		file.getItems().add(save);
		
		saveAs = new MenuItem("Save As...");
		saveAs.setOnAction( new CAction("saveAsAction") );
		saveAs.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+S"));
		file.getItems().add(saveAs);
		
		
		
		notes = new Menu("Notes");
		menuBar.getMenus().add(notes);
		
		newNote = new MenuItem("New note...");
		newNote.setOnAction( new CAction("newNoteAction") );
		notes.getItems().add(newNote);
		
		deleteNote = new MenuItem("Delete note");
		deleteNote.setOnAction( new CAction("deleteNoteAction") );
		deleteNote.setAccelerator(KeyCombination.keyCombination("DELETE"));
		deleteNote.setDisable(true);
		notes.getItems().add(deleteNote);
				
		notes.getItems().add( new SeparatorMenuItem() );
	
		manageTypes = new MenuItem("Manage types...");
		manageTypes.setOnAction( new CAction("manageTypeAction") );
		notes.getItems().add(manageTypes);
		
		selection = new Menu("Selection");
		menuBar.getMenus().add(selection);
		
		deselect = new MenuItem("Deselect");
		deselect.setOnAction( new CAction("deselectAction") );
		deselect.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+A"));
		selection.getItems().add(deselect);
		
		settings = new Menu("Settings");
		menuBar.getMenus().add(settings);
		
		checkUpdates = new MenuItem("Check for Updates...");
		checkUpdates.setOnAction( new CAction("checkForUpdatesAction") );
		settings.getItems().add(checkUpdates);
		
		releaseNotes = new MenuItem("Show Release Notes...");
		releaseNotes.setOnAction( new CAction("showReleaseNotes") );
		settings.getItems().add(releaseNotes);
		
		config = new MenuItem("Edit settings...");
		config.setOnAction( new CAction("editConfig") );
		settings.getItems().add(config);
		
		exit = new MenuItem("Exit");
		exit.setOnAction( new CAction("exit") );
		settings.getItems().add(exit);
		
		
		
		getChildren().add(menuBar);
		
		setVisible(true);
		
	}
	
	public void _run() {
		if(typeManager != null) {
			typeManager._run();
			
		}
		if(JFXMain.root.infoPanel.note != null) {
			deleteNote.setDisable(false);
		} else {
			deleteNote.setDisable(true);
		}
		
	}
	
}
