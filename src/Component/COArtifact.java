package Component;

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

public class COArtifact extends Rectangle {

	public Text label;
	public Text head;
	private StringProperty data;
	public TextField field;

	public COArtifact(double x, double y, Color color) {
		super(x, y, 130, 50);
		setStroke(Color.BLACK);
		setFill(color);

		data = new SimpleStringProperty("Component");

		head = new Text("<<Source>>");
		head.xProperty().bind(xProperty().add(widthProperty().getValue() / 2)
				.subtract(head.layoutBoundsProperty().getValue().getWidth() / 2));
		head.yProperty().bind(yProperty().add(15));

		label = new Text();
		label.setFont(Font.font("Arial", FontWeight.BLACK, 14));
		label.textProperty().bind(data);
		label.xProperty().bind(xProperty().add(widthProperty().getValue() / 2)
				.subtract(label.layoutBoundsProperty().getValue().getWidth() / 2));
		label.yProperty().bind(yProperty().add(32));

		field = new TextField(data.get());
		field.setLayoutX(300);
		field.setLayoutY(400);
		//field.layoutXProperty().bind(xProperty().subtract(25));
		//field.layoutYProperty().bind(yProperty().add(25));
		

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
				field.setVisible(true);
			}
		});
		DoubleProperty width = new SimpleDoubleProperty();
		field.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				width.set(label.layoutBoundsProperty().getValue().getWidth());
				label.xProperty().bind(xProperty().add(widthProperty().getValue() / 2)
						.subtract(label.layoutBoundsProperty().getValue().getWidth() / 2).add(5));
				head.xProperty().bind(xProperty().add(widthProperty().getValue() / 2)
						.subtract(head.layoutBoundsProperty().getValue().getWidth() / 2));
				widthProperty().bind(width.add(40));
				if (e.getCode() == KeyCode.ENTER) {
					field.setVisible(false);
				}
			}
		});
	}

	public TextField getText(boolean isShow) {
		if (isShow) {
			field.setVisible(isShow);
		} else {
			field.setVisible(false);
		}
		return field;
	}

}
