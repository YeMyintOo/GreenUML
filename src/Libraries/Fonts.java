package Libraries;

import java.awt.GraphicsEnvironment;

public class Fonts {
	public String[] fonts;

	public Fonts() {
		fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
	}
}
