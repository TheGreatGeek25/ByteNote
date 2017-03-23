package bytenote;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
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
	public abstract String toOldFormat();
	public abstract boolean isEqual(NoteData data);
	
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
			try {
				return isEqual(this, data);
			} catch (IllegalArgumentException | IllegalAccessException e) {
//				e.printStackTrace();
				return false;
			}
		}
		
		
	}
	
	public static <T> boolean isEqual(T a, T b) throws IllegalArgumentException, IllegalAccessException {
		if(!a.getClass().equals(b.getClass())) {
			return false;
		}
		if(a.equals(b)) {
			return true;
		}
		boolean out = true;
		ArrayList<Field> fields = getAllFields(a.getClass());
		for(Field field: fields) {
			field.setAccessible(true);
			if(field.getType().isPrimitive()) {
				switch(field.getType().getName()) {
					case "boolean":
						out = out && field.getBoolean(a) == field.getBoolean(b);
						break;
					case "byte":
						out = out && field.getByte(a) == field.getByte(b);
						break;
					case "short":
						out = out && field.getShort(a) == field.getShort(b);
						break;
					case "int":
						out = out && field.getInt(a) == field.getInt(b);
						break;
					case "long":
						out = out && field.getLong(a) == field.getLong(b);
						break;
					case "float":
						out = out && field.getFloat(a) == field.getFloat(b);
						break;
					case "double":
						out = out && field.getDouble(a) == field.getDouble(b);
						break;
					case "char":
						out = out && field.getChar(a) == field.getChar(b);
						break;
				}
			} else {
				out = out && isEqual(field.get(a), field.get(b));
			}
		}
		return out;
	}
	
	public static ArrayList<Field> getAllFields(Class<?> clazz) {
		ArrayList<Field> list = new ArrayList<>();
		list.addAll(Arrays.asList(clazz.getFields()));
		if(clazz.getSuperclass() != null) {
			list.addAll(getAllFields(clazz.getSuperclass()));
		}
		for(int i=0; i<clazz.getInterfaces().length; i++) {
			list.addAll(getAllFields(clazz.getInterfaces()[i]));
		}
		return list;
	}
	
}
