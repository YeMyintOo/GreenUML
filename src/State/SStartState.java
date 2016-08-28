package State;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class SStartState extends Circle {

	public SStartState(double x, double y, Color color) {
		super(x, y, 10);
		setFill(color);
		setStroke(Color.BLACK);

		addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				setCenterX(e.getX());
				setCenterY(e.getY());
			}
		});
	}

}
