package bytenote.note.types;

import bytenote.JFXMain;
import bytenote.note.types.table.CColor;
import bytenote.note.types.typeeditor.NoteTypeEditorPanel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class NoteTypeManagerPanel extends BorderPane {
	
	public ScrollPane mainScrollPane;
	public NoteTypeControlPanel controlPanel;
	
	public ObservableList<CColor> types;
	public TableView<CColor> mainTable = new TableView<>();
	public TableColumn<CColor, String> typeNameColumn;
	public TableColumn<CColor, Color> colorColumn;
	
	public NoteTypeEditorPanel typeEditor;
	private JFXMain jfxMain;

	public NoteTypeManagerPanel(JFXMain jfxMain) {
		super();
		this.jfxMain = jfxMain;

		types = loadData();
		mainTable.setEditable(false);
		mainTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		typeNameColumn = new TableColumn<>("Type Name");
		typeNameColumn.setCellValueFactory( new PropertyValueFactory<>("typeName"));
		mainTable.getColumns().add(typeNameColumn);
		
		colorColumn = new TableColumn<>("Color");
		colorColumn.setCellValueFactory( new PropertyValueFactory<>("typeColor"));
		colorColumn.setCellFactory( (TableColumn<CColor, Color> param) -> new TableCell<>() {
			@Override
			protected void updateItem(Color item, boolean empty) {
				super.updateItem(item, empty);
				setText(null);
				setBackground(new Background(new BackgroundFill(item, null, null)));
				if (item != null) {
					setTooltip(new Tooltip("RGB: " + (int) Math.floor(item.getRed() * 255) + ", " + (int) Math.floor(item.getGreen() * 255) + ", " + (int) Math.floor(item.getBlue() * 255) + "\n"
							+ "HSB: " + item.getHue() + ", " + item.getSaturation() + ", " + item.getBrightness() + "\n"
							+ "web: " + item.toString()));
				}
			}
		});
		mainTable.getColumns().add(colorColumn);
		
		mainTable.setItems(types);
		
		
		mainScrollPane = new ScrollPane(mainTable);
		mainScrollPane.setFitToWidth(true);
		setCenter(mainScrollPane);
		
		controlPanel = new NoteTypeControlPanel(jfxMain);
		controlPanel.setBackground( new Background( new BackgroundFill(Color.RED, null, null) ) );
		controlPanel.setPrefSize(100, 200); 
		setRight(controlPanel);

	}

	public void _run() {
		saveData();
		controlPanel._run();
	}
	
	
	public ObservableList<CColor> loadData() {
		ObservableList<CColor> list = FXCollections.observableArrayList();
		for(int i=0; i<NoteTypes.getTypeMap().keySet().size(); i++) {
			String[] keyArray = NoteTypes.getTypeMap().keySet().toArray( new String[0] );
			CColor newType = new CColor(keyArray[i], NoteTypes.getTypeMap().get(keyArray[i]));
			list.add(newType);
		}
		return list;
	}
	
	public void deleteType(CColor type) {
		for (int i = 0; i < jfxMain.getRoot().todoPanel.notes.size(); i++) {
			if(jfxMain.getRoot().todoPanel.notes.get(i).type.equals(type.getTypeName())) {
				jfxMain.getRoot().todoPanel.notes.get(i).type = "(default)";
			}
		}
		for (int i = 0; i < jfxMain.getRoot().doingPanel.notes.size(); i++) {
			if(jfxMain.getRoot().doingPanel.notes.get(i).type.equals(type.getTypeName())) {
				jfxMain.getRoot().doingPanel.notes.get(i).type = "(default)";
			}
		}
		for (int i = 0; i < jfxMain.getRoot().donePanel.notes.size(); i++) {
			if(jfxMain.getRoot().donePanel.notes.get(i).type.equals(type.getTypeName())) {
				jfxMain.getRoot().donePanel.notes.get(i).type = "(default)";
			}
		}
		types.remove(type);
		
	}
	
	public void saveData() {
		NoteTypes.getTypeMap().clear();
		for (CColor type : types) {
			NoteTypes.addToMap(type.getTypeName(), type.getTypeColor());

		}
	}

}
