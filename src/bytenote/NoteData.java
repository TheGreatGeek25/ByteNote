package bytenote;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import bytenote.note.Note;
import bytenote.note.types.NoteTypes;
import javafx.scene.paint.Color;

public abstract class NoteData implements Serializable {
	private static final long serialVersionUID = 287788144153840368L;

	public static NoteData getCurrentData() {
		return new V1_0(JFXMain.root.todoPanel.notes, JFXMain.root.doingPanel.notes, JFXMain.root.donePanel.notes, NoteTypes.typeMap);
	}
	
	public static NoteData getBlankNoteData() {
		return new V1_0();
	}
	
	public abstract void load();
	
	public static class V1_0  extends NoteData {
		private static final long serialVersionUID = 2831267637881320671L;
		protected ArrayList<Note> todoNotes = new ArrayList<>();
		protected ArrayList<Note> doingNotes = new ArrayList<>();
		protected ArrayList<Note> doneNotes = new ArrayList<>();
		protected HashMap<String, int[]> noteTypes = new HashMap<>();
		
		V1_0() {
			
		}
		
		V1_0(ArrayList<Note> todoNotes, ArrayList<Note> doingNotes, ArrayList<Note> doneNotes, HashMap<String, Color> noteTypes) {
			this.todoNotes = todoNotes;
			this.doingNotes = doingNotes;
			this.doneNotes = doneNotes;
			for(String s:noteTypes.keySet()) {
				this.noteTypes.put(s, new int[] {(int) noteTypes.get(s).getRed(), (int) noteTypes.get(s).getGreen(), (int) noteTypes.get(s).getBlue()});
			}
		}
		
		@Override
		public void load() {
			JFXMain.root.todoPanel.notes.clear();
			JFXMain.root.todoPanel.addNotes(todoNotes);
			JFXMain.root.doingPanel.notes.clear();
			JFXMain.root.doingPanel.addNotes(doingNotes);
			JFXMain.root.donePanel.notes.clear();
			JFXMain.root.donePanel.addNotes(doneNotes);
			NoteTypes.typeMap.clear();
			for(String s:noteTypes.keySet()) {
				NoteTypes.addToMap(s, Color.color(noteTypes.get(s)[0], noteTypes.get(s)[1], noteTypes.get(s)[2]));
			}
		}
		
		
	}
}
