package bytenote;

import java.util.ArrayList;
import java.util.HashMap;

import bytenote.note.Note;
import bytenote.note.types.NoteTypes;
import javafx.scene.paint.Color;

public abstract class NoteData {
	
	public static NoteData getCurrentData() {
		return new V1_0(JFXMain.root.todoPanel.notes, JFXMain.root.doingPanel.notes, JFXMain.root.donePanel.notes, NoteTypes.typeMap);
	}
	
	public abstract void load();
	
	public static class V1_0  extends NoteData {

		protected ArrayList<Note> todoNotes = new ArrayList<>();
		protected ArrayList<Note> doingNotes = new ArrayList<>();
		protected ArrayList<Note> doneNotes = new ArrayList<>();
		protected HashMap<String, Color> noteTypes = new HashMap<>();
		
		V1_0(ArrayList<Note> todoNotes, ArrayList<Note> doingNotes, ArrayList<Note> doneNotes, HashMap<String, Color> noteTypes) {
			this.todoNotes = todoNotes;
			this.doingNotes = doingNotes;
			this.doneNotes = doneNotes;
			this.noteTypes = noteTypes;
		}
		
		@Override
		public void load() {
			JFXMain.root.todoPanel.notes.clear();
			JFXMain.root.todoPanel.addNotes(todoNotes);
			JFXMain.root.doingPanel.notes.clear();
			JFXMain.root.doingPanel.addNotes(doingNotes);
			JFXMain.root.donePanel.notes.clear();
			JFXMain.root.donePanel.addNotes(doneNotes);
			NoteTypes.setMap(noteTypes);
		}
		
		
	}
}
