package Libraries;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class General extends Rectangle {
	public Bounds bounds;
	public Rectangle tlR; // Top Left Rectangle
	public Rectangle trR; // Top Right Rectangle
	public Rectangle blR; // Bottom Left Rectangle
	public Rectangle brR;// Bottom Right Rectangle

	public General(double x, double y) {
		super();
		setX(x);
		setY(y);
		setFill(Color.RED);

		tlR = new MRectangle();
		trR = new MRectangle();
		blR = new MRectangle();
		brR = new MRectangle();
		
		bounds=this.getLayoutBounds();
		
		tlR.xProperty().bind(this.xProperty().subtract(10));
		tlR.yProperty().bind(this.yProperty().subtract(10));
		
		trR.xProperty().bind(this.xProperty().subtract(10));
		trR.yProperty().bind(this.yProperty());
	
	}
}
