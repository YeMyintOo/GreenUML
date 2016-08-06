package Libraries;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class GridLines extends Line {
	public GridLines(double x1, double y1, double x2, double y2) {
		super(x1, y1, x2, y2);
		setStroke(Color.ALICEBLUE);
	}
}
