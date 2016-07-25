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
	public String ctool; // Current Selected Tool Item
	Screen screen;
	Pane area; // Draw
	VBox tool; // Tool Bar

	public Draw(Scene scene) {
		area = new Pane();
		tool = new VBox();
		screen = new Screen();

		setCenter(area);
		setRight(tool);

		setPrefHeight(screen.getHeight() - 140);
		setMaxHeight(screen.getHeight() - 140);
		setPrefWidth(400);
		setMaxWidth(400);
		setStyle("-fx-background-color:white;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: blue;");

		area.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				scene.setCursor(Cursor.DEFAULT);
				System.out.println("Selected Tool is : "+ ctool);
			}
		});

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
}
