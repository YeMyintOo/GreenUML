package Libraries;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Icon extends ImageView {
	Image image;

	public Icon(String path) {
		super();
		setFitHeight(25);
		setFitWidth(25);
		
		try {
			image = new Image(new FileInputStream(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		setImage(image);
	}
}
