package Hardware;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class Screen {

	protected JFrame box;
	protected Dimension d;

	public Screen() {
		box = new JFrame();
		Toolkit kit = box.getToolkit();
		d = kit.getScreenSize();
	}

	public Dimension getFScreenSize() {
		return d;
	}

	public double getHeight() {
		return d.height;
	}

	public double getWidth() {
		return d.width;
	}

}
