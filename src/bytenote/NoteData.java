package bytenote;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import bytenote.note.Note;
import bytenote.note.types.NoteTypes;
import javafx.scene.paint.Color;

public abstract class NoteData implements Serializable {
	private static final long serialVersionUID = 287788144153840368L;
	
	public static NoteData getCurrentData() {
		HashMap<String, Color> types = new HashMap<>();
		for(String key: NoteTypes.getTypeMap().keySet()) {
			if(NoteTypes.getTypeMap().get(key).isLocal()) {
				types.put(key, NoteTypes.getTypeMap().get(key).getColor());
			}
		}
		return new V1_0(JFXMain.root.todoPanel.notes, JFXMain.root.doingPanel.notes, JFXMain.root.donePanel.notes, types);
	}
	
	public static NoteData getBlankNoteData() {
		return new V1_0();
	}
	
	public abstract void load();
	public abstract String toOldFormat();
	public abstract boolean isEqual(NoteData data);
	
	public final static class V1_0  extends NoteData {
		private static final long serialVersionUID = 2831267637881320671L;
		private ArrayList<Note> todoNotes = new ArrayList<>();
		private ArrayList<Note> doingNotes = new ArrayList<>();
		private ArrayList<Note> doneNotes = new ArrayList<>();
		private HashMap<String, int[]> noteTypes = new HashMap<>();
		
		V1_0() {
			
		}
		
		@SuppressWarnings("unchecked")
		V1_0(ArrayList<Note> todoNotes, ArrayList<Note> doingNotes, ArrayList<Note> doneNotes, HashMap<String, Color> noteTypes) {
			this.todoNotes = (ArrayList<Note>) todoNotes.clone();
			this.doingNotes = (ArrayList<Note>) doingNotes.clone();
			this.doneNotes = (ArrayList<Note>) doneNotes.clone();
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
			NoteTypes.getTypeMap().clear();
			for(String s:noteTypes.keySet()) {
				NoteTypes.addToMap(s, Color.color(noteTypes.get(s)[0], noteTypes.get(s)[1], noteTypes.get(s)[2]), true);
			}
		}

		@Override
		public String toOldFormat() {
			String writeString = ByteNoteMain.syntaxVersion+"\n"
					+ "notes\n"
					+ "todo\n";
			/**
			 * Save todo notes
			 */
			for (int i = 0; i < todoNotes.size(); i++) {
				writeString += "note\n"
						+ "noteText"+todoNotes.get(i).noteText+"\n"
						+ "priority"+todoNotes.get(i).priority+"\n"
						+ "type"+todoNotes.get(i).type+"\n";
				if(i != todoNotes.size() - 1) {
					writeString += "";
				}
			}
			
			/**
			 * Save doing notes
			 */
			writeString += "doing\n";
			for (int i = 0; i < doingNotes.size(); i++) {
				writeString += "note\n"
						+ "noteText"+doingNotes.get(i).noteText+"\n"
						+ "priority"+doingNotes.get(i).priority+"\n"
						+ "type"+doingNotes.get(i).type+"\n";
				if(i != doingNotes.size() - 1) {
					writeString += "";
				}
			}
			/**
			 * Save done notes
			 */
			writeString += "done\n";
			for (int i = 0; i < doneNotes.size(); i++) {
				writeString += "note\n"
						+ "noteText"+doneNotes.get(i).noteText+"\n"
						+ "priority"+doneNotes.get(i).priority+"\n"
						+ "type"+doneNotes.get(i).type+"\n";
				if(i != doneNotes.size() - 1) {
					writeString += "";
				}
			}
			/**
			 * Save settings
			 */
			writeString += "settings\n"
					+ "types\n";
			for (int i = 0; i < noteTypes.keySet().toArray().length; i++) {
				writeString += noteTypes.keySet().toArray()[i]+"";
				String color;
				//red
				color = ( (int) Math.floor(noteTypes.get(noteTypes.keySet().toArray()[i])[0]*255))+",";
				//green
				color += ( (int) Math.floor(noteTypes.get(noteTypes.keySet().toArray()[i])[1]*255))+",";
				//blue
				color += ( (int) Math.floor(noteTypes.get(noteTypes.keySet().toArray()[i])[2]*255));
				writeString += color+"\n";
			}
			return writeString;
		}

		@Override
		public boolean isEqual(NoteData data) {
			if(!this.getClass().equals(data.getClass())) {
				return false;
			}
			V1_0 dataV1_0 = (V1_0) data;
			if(!(this.getTodoNotes().equals(dataV1_0.getTodoNotes()) &&
					this.getDoingNotes().equals(dataV1_0.getDoingNotes()) &&
					this.getDoneNotes().equals(dataV1_0.getDoneNotes()) &&
					this.getNoteTypes().keySet().equals(dataV1_0.getNoteTypes().keySet()))) {
				return false;
			}
			Collection<int[]> thisTypes = this.getNoteTypes().values();
			Collection<int[]> dataTypes = dataV1_0.getNoteTypes().values();
			if(thisTypes.size() != dataTypes.size()) {
				return false;
			}
			Iterator<int[]> thisIt = thisTypes.iterator();
			Iterator<int[]> dataIt = dataTypes.iterator();
			while(thisIt.hasNext()) {
				int[] thisArray = thisIt.next();
				int[] dataArray = dataIt.next();
				if(thisArray.length == dataArray.length) {
					for (int i = 0; i < thisArray.length; i++) {
						if(thisArray[i] != dataArray[i]) {
							return false;
						}
					}
				}
			}
			return true;
		}
		
		public ArrayList<Note> getTodoNotes() {
			return todoNotes;
		}
		
		public ArrayList<Note> getDoingNotes() {
			return doingNotes;
		}
		
		public ArrayList<Note> getDoneNotes() {
			return doneNotes;
		}
		
		public HashMap<String, int[]> getNoteTypes() {
			return noteTypes;
		}
		
	}
	
	
}
