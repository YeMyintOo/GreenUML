package GUI;

import Hardware.Screen;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Draw extends BorderPane {
	private String ctool; // Current Selected Tool Item
	Screen screen;
	private Pane area; // Draw
	private VBox tool; // Tool Bar

	public Draw(Scene scene) {
		ctool="1"; //Default Value;
		area = new Pane();
		tool = new VBox();
		screen = new Screen();

		setCenter(area);
		setRight(tool);

		setPrefHeight(screen.getHeight() - 140);
		setMaxHeight(screen.getHeight() - 140);
		setPrefWidth(screen.getWidth());
		setMaxWidth(screen.getWidth());
		setStyle("-fx-background-color:white;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: blue;");

		

		Button b1 = new Button("1");
		Button b2 = new Button("2");
		Button b3 = new Button("3");
		tool.getChildren().addAll(b1, b2, b3);

		b1.setOnAction(e -> {
			ctool=b1.getText();
		});
		b2.setOnAction(e -> {
			ctool=b2.getText();
		});
		b3.setOnAction(e -> {
			ctool=b3.getText();
		});
	}
	public String getCTool(){
		return ctool;
	}
	public Pane getArea(){
		return area;
	}
}
