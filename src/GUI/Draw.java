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
	Screen screen;
	Pane area; // Draw
	VBox tool; // Tool Bar

	public Draw(Scene scene) {
		area = new Pane();
		tool = new VBox();
		tool.getChildren().add(new Button("EEE"));
		
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
			}
		});
	}
}
