package GUI;

import Hardware.Screen;
import javafx.scene.layout.Pane;

public class Draw extends Pane {
	Screen screen;

	public Draw() {
		screen = new Screen();

		setPrefHeight(screen.getHeight()-140);
		setMaxHeight(screen.getHeight()-140);
		setPrefWidth(400);
		setMaxWidth(400);
		setStyle("-fx-background-color:white;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: blue;");
	}
}
