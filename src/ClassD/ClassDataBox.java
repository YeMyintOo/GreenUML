package ClassD;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ClassDataBox extends Stage {

	BorderPane root;
	public String condition;
	public String label;

	private GridPane permit;
	public RadioButton publicCB;
	public RadioButton privateCB;
	public RadioButton protectCB;
	public RadioButton packageCB;

	private GridPane info;
	public TextField dataF;
	public TextField multiF;
	public CheckBox staticAtt;

	private String[] types;
	public ComboBox<String> dataType;

	private Button okB;
	private Button closeB;
	
	public boolean isStatic;

	public ClassDataBox(Stage owner) {
		super();
		initStyle(StageStyle.UNDECORATED);
		initModality(Modality.WINDOW_MODAL); // Prevent click parent stage
		initOwner(owner);
		setResizable(false);
		setTitle("DataBox");
		root = new BorderPane();
		condition = "default";

		types = new String[] { "String", "Integer", "Boolean" };
		dataType = new ComboBox<String>();
		dataType.getItems().addAll(types);
		dataType.setPrefWidth(170);
		dataType.setMaxWidth(170);

		dataF = new TextField();
		
		
		multiF = new TextField();
		multiF.setPrefWidth(70);
		multiF.setMaxWidth(70);
		
		staticAtt = new CheckBox("Static Attribute");
		staticAtt.setFont(Font.font("Arial", FontWeight.NORMAL, 10));

		ToggleGroup group = new ToggleGroup();
		publicCB = new RadioButton("Public");
		privateCB = new RadioButton("Private");
		protectCB = new RadioButton("Protected");
		packageCB = new RadioButton("Package");
		group.getToggles().addAll(publicCB, privateCB, protectCB, packageCB);

		BorderPane p = new BorderPane();
		permit = new GridPane();
		permit.setStyle("-fx-padding: 20;");
		permit.setHgap(20);
		permit.setVgap(10);
		permit.addRow(0, publicCB);
		permit.addRow(1, privateCB);
		permit.addRow(2, protectCB);
		permit.addRow(3, packageCB);
		publicCB.setSelected(true);

		info = new GridPane();
		info.setStyle("-fx-padding: 20;");
		info.setHgap(20);
		info.setVgap(10);
		info.addRow(0, new Label("Name"), dataF);
		info.addRow(1, new Label("Type"), dataType);
		info.addRow(2, new Label("Multiplicity"), multiF);
		info.addRow(3, new Label(""), staticAtt);

		p.setCenter(permit);
		p.setLeft(info);

		HBox btn = new HBox();
		okB = new Button("Create");
		closeB = new Button("Close");
		btn.getChildren().addAll(closeB, okB);
		btn.setSpacing(4);
		btn.setStyle("-fx-padding:10 20 10 10;" + "-fx-background-color:rgb(220,220,220);" + "-fx-cursor: hand;");
		btn.setAlignment(Pos.BASELINE_RIGHT);

		root.setCenter(p);
		root.setBottom(btn);

		Scene scene = new Scene(root, 440, 220, Color.WHITE);
		setScene(scene);

		closeB.setOnAction(e -> {
			close();
		});
		okB.setOnAction(e -> {
			if (!dataF.getText().equals("") && !dataF.getText().contains(" ")) {
				condition = "create";
				if (publicCB.isSelected()) {
					label = "+";
				} else if (privateCB.isSelected()) {
					label = "-";
				} else if (protectCB.isSelected()) {
					label = "#";
				} else if (packageCB.isSelected()) {
					label = "~";
				}
				label = label + " " + dataF.getText().trim() + " : ";

				if (dataType.getSelectionModel().getSelectedIndex() != -1) {
					switch (dataType.getSelectionModel().getSelectedItem().toString()) {
					case "String":
						label = label + "String";
						break;
					case "Integer":
						label = label + "int";
						break;
					case "Boolean":
						label = label + "boolean";
						break;
					}
					if(staticAtt.isSelected()){
						isStatic=true;
					}
					
					if (!multiF.getText().trim().equals("")
							&& (multiF.getText().startsWith("*") || multiF.getText().trim().contains(".."))) {
						label = label + " [" + multiF.getText().trim() + "]";
						close();
					} else if (multiF.getText().trim().equals("")) {
						close();
					} else {
						multiF.setStyle("-fx-text-box-border:red");
					}

				} else {
					dataType.setStyle("-fx-background-color:red");
				}
			} else {
				dataF.setStyle("-fx-text-box-border:red");
			}
		});

		dataType.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				dataType.setStyle("-fx-background-color:white");
			}
		});
	}
}
