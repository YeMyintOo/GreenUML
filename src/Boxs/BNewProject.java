package Boxs;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BNewProject extends Stage {

	// New Project Box
	private Label nameL;
	public TextField nameF;

	private Button okB;
	private Button closeB;
	private Button resetB;

	// Return Value
	private String value;

	public BNewProject(Stage owner) {
		super();
		initModality(Modality.WINDOW_MODAL); // Prevent click parent stage
		initOwner(owner);
		setResizable(false);
		value = "default";
		setTitle("New Project");
		BorderPane pane = new BorderPane();

		BorderPane infoP = new BorderPane();

		// Name
		GridPane nameP = new GridPane();
		nameP.setStyle("-fx-padding: 40 10 0 10;");
		nameL = new Label("Name");
		nameL.setStyle("-fx-padding: 5 20 0 0;");
		nameF = new TextField();
		nameP.addRow(0, nameL, nameF);

		infoP.setCenter(nameP);

		// Button Panel
		HBox btn = new HBox();
		okB = new Button("Finish");
		closeB = new Button("Cancel");
		resetB = new Button("Reset");
		btn.getChildren().addAll(resetB, okB, closeB);
		btn.setSpacing(4);
		btn.setStyle("-fx-padding:10 10 10 10;" + "-fx-background-color:rgb(220,220,220);" + "-fx-cursor: hand;");
		btn.setAlignment(Pos.BASELINE_RIGHT);

		pane.setCenter(infoP);
		pane.setBottom(btn);

		Scene scene = new Scene(pane, 400, 150, Color.WHITE);
		setScene(scene);

		resetB.setOnAction(e -> {
			nameF.setText("");
		});
		closeB.setOnAction(e -> {
			setValue("close");
			close();
		});
		okB.setOnAction(e -> {
			if (!nameF.getText().equals("")) {
				try {
					setValue("finish");
					close();
				} catch (Exception ee) {
					ee.printStackTrace();
				}
			} else {
				nameF.setStyle("-fx-text-box-border:red");
			}
		});
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
