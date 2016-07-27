package Components;

import Libraries.General;
import javafx.scene.shape.Rectangle;

public class Sample1 extends General {
	// Rectangle
	public Sample1(double x, double y) {
		super(x, y);
		setWidth(300);
		setHeight(200);
	}

	public Rectangle gettlR() {
		return tlR;
	}
	public Rectangle gettrR(){
		return trR;
	}
}
