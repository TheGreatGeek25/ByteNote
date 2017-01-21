package c57note64.cnote.types;

import c57note64.JFXMain;
import c57note64.cnote.types.table.CColor;
import c57note64.cnote.types.typeeditor.CTypeEditorPanel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class CTypeManagerPanel extends BorderPane {
	
	public ScrollPane mainScrollPane;
	public CTypeControlPanel controlPanel;
	
	public ObservableList<CColor> types;
	public TableView<CColor> mainTable = new TableView<>();
	public TableColumn<CColor, String> typeNameColumn;
	public TableColumn<CColor, Color> colorColumn;
	
	public CTypeEditorPanel typeEditor;

	public CTypeManagerPanel() {
		super();
		
		types = loadData();
//		mainTable.setPreferredSize(new Dimension(cTypeManager.getWidth()-17, cTypeManager.getHeight()) );
		mainTable.setEditable(false);
		typeNameColumn = new TableColumn<>("Type Name");
		typeNameColumn.setCellValueFactory( new PropertyValueFactory<>("typeName"));
		mainTable.getColumns().add(typeNameColumn);
		
		colorColumn = new TableColumn<>("Color");
		colorColumn.setCellValueFactory( new PropertyValueFactory<>("typeColor"));
		colorColumn.setCellFactory( new Callback<TableColumn<CColor,Color>, TableCell<CColor,Color>>() {
			@Override
			public TableCell<CColor, Color> call(TableColumn<CColor, Color> param) {
				return new TableCell<CColor, Color>() {
					@Override
					protected void updateItem(Color item, boolean empty) {
						super.updateItem(item, empty);
						setText(null);
						setBackground( new Background( new BackgroundFill(item, null, null) ) );
					}
				};
			}
		});
		mainTable.getColumns().add(colorColumn);
		
		mainTable.setItems(types);
		
//		mainTable.setFillsViewportHeight(true);
//		mainTable.setDefaultRenderer(Color.class, new CColorRenderer() );
//		mainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		mainScrollPane = new ScrollPane(mainTable);
		mainScrollPane.setFitToWidth(true);
//		mainScrollPane.setPreferredSize(new Dimension(cTypeManager.getWidth()-17, cTypeManager.getHeight()) );
		setCenter(mainScrollPane);
		
		controlPanel = new CTypeControlPanel();
		controlPanel.setBackground( new Background( new BackgroundFill(Color.RED, null, null) ) );
		controlPanel.setPrefSize(100, 200); 
		setRight(controlPanel);

	}

	public void c57run() {
		saveData();
		controlPanel.c57run();
	}
	
	/*public void loadData() {
		data = new Object[CNoteTypes.typeMap.keySet().size()][columnNames.length];
		for (int i = 0; i < CNoteTypes.typeMap.keySet().toArray().length; i++) {
			data[i][0] = CNoteTypes.typeMap.keySet().toArray()[i];
			data[i][1] = CNoteTypes.typeMap.get(CNoteTypes.typeMap.keySet().toArray()[i]);
		}
		mainTable.setModel( new CTypeTableModel(data, columnNames) );
	}*/
	
	public ObservableList<CColor> loadData() {
		ObservableList<CColor> list = FXCollections.observableArrayList();
		for(int i=0; i<CNoteTypes.typeMap.keySet().size(); i++) {
			String[] keyArray = CNoteTypes.typeMap.keySet().toArray( new String[0] );
			CColor newType = new CColor(keyArray[i], CNoteTypes.typeMap.get(keyArray[i]));
			list.add(newType);
		}
		return list;
	}
	
	public void deleteType(CColor type) {
		for (int i = 0; i <JFXMain.root.todoPanel.notes.size(); i++) {
			if(JFXMain.root.todoPanel.notes.get(i).type.equals(type.getTypeName())) {
				JFXMain.root.todoPanel.notes.get(i).type = "(default)";
			}
		}
		for (int i = 0; i < JFXMain.root.doingPanel.notes.size(); i++) {
			if(JFXMain.root.doingPanel.notes.get(i).type.equals(type.getTypeName())) {
				JFXMain.root.doingPanel.notes.get(i).type = "(default)";
			}
		}
		for (int i = 0; i < JFXMain.root.donePanel.notes.size(); i++) {
			if(JFXMain.root.donePanel.notes.get(i).type.equals(type.getTypeName())) {
				JFXMain.root.donePanel.notes.get(i).type = "(default)";
			}
		}
		/*ArrayList<Object[]> asList = new ArrayList<Object[]>(Arrays.asList(data));
		asList.remove(row);
		Object[][] newData = new Object[asList.size()][data[0].length];
		asList.toArray(newData);
		data = newData.clone();*/
		types.remove(type);
		
	}
	
	public void saveData() {
		CNoteTypes.typeMap.clear();
		for (int i = 0; i < types.size(); i++) {
			CNoteTypes.addToMap(types.get(i).getTypeName(), types.get(i).getTypeColor());
			
		}
	}

}
