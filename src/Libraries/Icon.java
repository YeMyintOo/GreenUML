package Libraries;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.event.EventHandler;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Icon extends ImageView {
	Image image;
	public DropShadow shadow;

	public Icon(String path) {
		super();
		setFitHeight(25);
		setFitWidth(25);
		shadow = new DropShadow();
		try {
			image = new Image(new FileInputStream(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		setImage(image);

		addEventFilter(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				setEffect(shadow);
			}
		});
		
		addEventFilter(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				setEffect(null);
			}
		});
	}
}
