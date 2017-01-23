package c57note64.cnote.types;

import c57note64.C57note64Main;
import c57note64.JFXMain;
import c57note64.cnote.types.typeeditor.CTypeEditorPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CTypeControlPanel extends VBox {
	
	public Button deleteButton;
	public Button addButton;
	public Button editButton;
	
	public CTypeControlPanel() {
		super();
		
		setAlignment(Pos.CENTER);
		setSpacing(20);
		
//		add(Box.createVerticalGlue());
//		add(Box.createVerticalGlue());
		
		addButton = new Button("Add...");
		addButton.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				C57note64Main.isSaved = false;
				( (CTypeManagerPanel) getParent()).typeEditor = new CTypeEditorPanel();
				JFXMain.showView((Stage) CTypeControlPanel.this.getScene().getWindow(), ( (CTypeManagerPanel) getParent()).typeEditor, "Edit type", 300, 300);
			}
		});
		getChildren().add(addButton);
//		add(Box.createVerticalGlue());
		
		editButton = new Button("Edit...");
		editButton.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				C57note64Main.isSaved = false;
				String type = (String) ( (CTypeManagerPanel) getParent()).mainTable.getSelectionModel().getSelectedItem().getTypeName();
				( (CTypeManagerPanel) getParent()).typeEditor = new CTypeEditorPanel(type);
				JFXMain.showView((Stage) CTypeControlPanel.this.getScene().getWindow(), ( (CTypeManagerPanel) getParent()).typeEditor, "Edit type", 300, 300);
			}
		});
		editButton.setDisable(true);
		getChildren().add(editButton);
//		add(Box.createVerticalGlue());
		
		/*deleteButton = new JButton(  new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				C57note64Main.isSaved = false;
				( (CTypeManagerPanel) getParent()).deleteType(( (CTypeManagerPanel) getParent()).mainTable.getSelectedRow());
				( (CTypeManagerPanel) getParent()).mainTable.setModel( new CTypeTableModel(( (CTypeManagerPanel) getParent()).data, ( (CTypeManagerPanel) getParent()).columnNames) );
			}
		} );*/
		deleteButton = new Button("Delete");
		deleteButton.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				C57note64Main.isSaved = false;
				( (CTypeManagerPanel) getParent()).deleteType(( (CTypeManagerPanel) getParent()).mainTable.getSelectionModel().getSelectedItem());
//				( (CTypeManagerPanel) getParent()).mainTable.setModel( new CTypeTableModel(( (CTypeManagerPanel) getParent()).data, ( (CTypeManagerPanel) getParent()).columnNames) );
			}
		});
		deleteButton.setDisable(true);
		getChildren().add(deleteButton);
//		add(Box.createVerticalGlue());
//		add(Box.createVerticalGlue());
		
	}
	
	public void c57run() {
//		System.out.println(( (CTypeManagerPanel) getParent()).mainTable.getSelectedRow());
		if(!( (CTypeManagerPanel) getParent()).mainTable.getSelectionModel().isEmpty() && !( (CTypeManagerPanel) getParent()).mainTable.getSelectionModel().getSelectedItem().getTypeName().equals("(default)")) {
			deleteButton.setDisable(false);
			editButton.setDisable(false);
		} else {
			deleteButton.setDisable(true);
			editButton.setDisable(true);
		}

	}
}
