package Activity;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class AEndNode extends Circle{

	public Circle outer;

	public AEndNode(double x, double y, Color color) {
		super(x, y,10);
		setFill(color);
		setStroke(Color.BLACK);

		outer = new Circle(getCenterX(), getCenterY(), 15);
		outer.setFill(Color.WHITE);
		outer.setStroke(Color.BLACK);

		outer.centerXProperty().bindBidirectional(centerXProperty());
		outer.centerYProperty().bindBidirectional(centerYProperty());
		
		addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				setCenterX(e.getX());
				setCenterY(e.getY());
			}
		});
	}

}
