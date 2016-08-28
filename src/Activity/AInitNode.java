package Activity;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class AInitNode extends Circle {

	public AInitNode(double x, double y, Color color) {
		super(x, y, 15);
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
