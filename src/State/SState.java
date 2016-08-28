package State;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class SState extends Rectangle {

	public Text label;
	private StringProperty data;
	private TextField field;

	public SState(double x, double y, Color color) {
		super(x, y, 100, 40);
		setArcWidth(10);
		setArcHeight(10);
		data = new SimpleStringProperty("State");
		setFill(color);
		setStroke(Color.BLACK);

		label = new Text();
		label.setFont(Font.font("Arial", FontWeight.NORMAL, 13));
		label.textProperty().bind(data);
		label.xProperty().bind(xProperty().add(widthProperty().getValue() / 2)
				.subtract(label.layoutBoundsProperty().getValue().getWidth() / 2));
		label.yProperty().bind(yProperty().add(23));

		field = new TextField();
		//field.layoutXProperty().bind(xProperty());
		//field.layoutYProperty().bind(yProperty());
		//field.textProperty().bindBidirectional(data);

		addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				setX(e.getX());
				setY(e.getY());
			}
		});

		label.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				System.out.println("Click on Label");
				field.setVisible(true);
			}
		});

		DoubleProperty width = new SimpleDoubleProperty();
		field.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				width.set(label.layoutBoundsProperty().getValue().getWidth());
				label.xProperty().bind(xProperty().add(widthProperty().getValue() / 2)
						.subtract(label.layoutBoundsProperty().getValue().getWidth() / 2));
				widthProperty().bind(width.add(20));
				if (e.getCode() == KeyCode.ENTER) {
					field.setVisible(false);
				}
			}
		});
	}

	public TextField getText(boolean isShow) {
		if (isShow) {
			field.setVisible(true);
		} else {
			field.setVisible(false);
		}
		return field;
	}

}
