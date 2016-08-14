package ClassD;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ClassFunBox extends Stage {

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

	private String[] types;
	public ComboBox<String> dataType;

	private Button okB;
	private Button closeB;

	public ClassFunBox(Stage owner) {
		super();
		initStyle(StageStyle.UNDECORATED);
		initModality(Modality.WINDOW_MODAL); // Prevent click parent stage
		initOwner(owner);
		setResizable(false);
		setTitle("DataBox");
		root = new BorderPane();
		condition = "default";

		types = new String[] { "String", "Integer", "Boolean", "Void" };
		dataType = new ComboBox<String>();
		dataType.getItems().addAll(types);

		dataF = new TextField();

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

		Scene scene = new Scene(root, 400, 200, Color.WHITE);
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
				label = label + " " + dataF.getText().trim() + "() : ";

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
					case "Void":
						label = label + "void";
						break;
					}
					close();
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
