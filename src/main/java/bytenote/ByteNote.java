package bytenote;

public class ByteNote {

    private boolean isSaved = true;

    private NoteData savedData;

    private String filePath;

    public ByteNote() {
        savedData = NoteData.getBlankNoteData();
        filePath = null;
    }

    public NoteData getSavedData() {
        return savedData;
    }

    public ByteNote setSavedData(NoteData savedData) {
        this.savedData = savedData;
        return this;
    }

    public String getFilePath() {
        return filePath;
    }

    public ByteNote setFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public ByteNote setSaved(boolean saved) {
        isSaved = saved;
        return this;
    }
}
