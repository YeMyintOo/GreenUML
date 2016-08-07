package Boxs;

import java.io.File;

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
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BNewDiagram extends Stage {
	// Return Data
	public String fileName;
	public int diagram;
	public String folder; // Project Name

	public TextField nameF;// Name for Diagram
	private Label isnamed; // Already name?
	private ComboBox projectCB;// Project ComboBox
	private String[] projects;
	// private Button pathB;

	private RadioButton r1;
	private RadioButton r2;
	private RadioButton r3;
	private RadioButton r4;
	private RadioButton r5;
	private RadioButton r6;
	private RadioButton r7;
	private RadioButton r8;
	private RadioButton r9;

	private Button okB;
	private Button closeB;
	private Button resetB;

	// Return value
	public String condition;

	public BNewDiagram(Stage owner) {
		super();
		setResizable(false);
		condition = "default";
		initModality(Modality.WINDOW_MODAL); // Prevent click parent stage
		initOwner(owner);
		setTitle("Choose New Diagram");
		loadProjects();

		BorderPane pane = new BorderPane();

		// Information
		GridPane p1 = new GridPane();
		p1.setHgap(20);
		p1.setVgap(10);
		p1.setStyle("-fx-padding: 10;");
		Label nameL = new Label("Name");
		nameF = new TextField();
		nameF.setTooltip(new Tooltip("Give a name For your diagram."));
		isnamed = new Label();
		p1.addRow(0, nameL, nameF, isnamed); // Add Components in Row 0

		projectCB = new ComboBox<>();
		projectCB.setPrefWidth(200);
		projectCB.getItems().addAll(projects);
		Label projL = new Label("Project");
		p1.addRow(1, projL, projectCB);

		// Diagram Types
		GridPane p2 = new GridPane();
		p2.setStyle("-fx-padding: 10;");
		p2.setHgap(20);
		p2.setVgap(10);
		ToggleGroup group = new ToggleGroup();
		r1 = new RadioButton("Use Case Diagram");
		r2 = new RadioButton("Object Diagram");
		r3 = new RadioButton("Sequence Diagram");
		r4 = new RadioButton("Collaboration Diagram");
		r4.setDisable(true);
		r5 = new RadioButton("Class Diagram");
		r6 = new RadioButton("StateChart Diagram");
		r7 = new RadioButton("Activity Diagram");
		r8 = new RadioButton("Component Diagram");
		r9 = new RadioButton("Deployment Diagram");
		group.getToggles().addAll(r1, r2, r3, r4, r5, r6, r7, r8, r9);
		r1.setSelected(true);
		p2.addRow(0, r1);
		p2.addRow(1, r2);
		p2.addRow(2, r3);
		p2.addRow(3, r4);
		p2.addRow(4, r5);
		p2.addRow(5, r6);
		p2.addRow(6, r7);
		p2.addRow(7, r8);
		p2.addRow(8, r9);

		// Button bar
		HBox btn = new HBox();
		okB = new Button("Finish");
		closeB = new Button("Cancel");
		resetB = new Button("Reset");
		btn.getChildren().addAll(resetB, okB, closeB);
		btn.setSpacing(4);
		btn.setStyle("-fx-padding:10 10 10 10;" + "-fx-background-color:rgb(220,220,220);" + "-fx-cursor: hand;");
		btn.setAlignment(Pos.BASELINE_RIGHT);

		pane.setTop(p1);
		pane.setCenter(p2);
		pane.setBottom(btn);

		Scene scene = new Scene(pane, 400, 400, Color.WHITE);
		setScene(scene);

		// closeB.setCancelButton(true);
		closeB.setOnAction(e -> {
			condition = "close";
			close();
		});

		okB.setOnAction(e -> {
			if (!nameF.getText().equals("") && projectCB.getSelectionModel().getSelectedIndex()!=-1) {
				setFileName(nameF.getText());
				diagram = calculateType();
				condition = "finish";
				close();
			}
		});

		resetB.setOnAction(e -> {
			nameF.setText("");
			r1.setSelected(true);
		});

		projectCB.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			public void changed(ObservableValue<?> ov, Object t, Object t1) {
				folder = projectCB.getSelectionModel().getSelectedItem().toString();
			}
		});

	}

	private int calculateType() {
		int i = 0; // null
		if (r1.isSelected()) {
			i = 1;
		} else if (r2.isSelected()) {
			i = 2;
		} else if (r3.isSelected()) {
			i = 3;
		} else if (r4.isSelected()) {
			i = 4;
		} else if (r5.isSelected()) {
			i = 5;
		} else if (r6.isSelected()) {
			i = 6;
		} else if (r7.isSelected()) {
			i = 7;
		} else if (r8.isSelected()) {
			i = 8;
		} else {
			i = 9;
		}
		return i;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void loadProjects() {
		File path = new File("Diagrams");
		File[] folders = path.listFiles(File::isDirectory);
		projects = new String[folders.length];
		for (int i = 0; i < folders.length; i++) {
			projects[i] = folders[i].getName();
		}
	}
}
