package UseCase;

import Libraries.CNode;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class UCBoundary extends Rectangle {
	private StringProperty data;
	private Text label;
	private TextField text;
	private DropShadow shape;
	public CNode botRNode;

	public UCBoundary(double x, double y, Color color) {
		super(x, y, 300, 400);
		setX(x);
		setY(y);
		setFill(color);
		//setOpacity(0.3);
		setStroke(Color.BLACK);
		setArcWidth(10);
		setArcHeight(10);
		data = new SimpleStringProperty("Label");

		botRNode = new CNode();
		botRNode.setRadius(40);
		botRNode.centerXProperty().bind(xProperty().add(widthProperty()));
		botRNode.centerYProperty().bind(yProperty().add(heightProperty()));

		botRNode.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				botRNode.centerXProperty().unbind();
				botRNode.centerYProperty().unbind();
				botRNode.setCenterX(e.getX());
				botRNode.setCenterY(e.getY());
			}
		});

		label = new Text();
		label.textProperty().bindBidirectional(labelProperty());
		label.setFont(Font.font("Arial", FontWeight.BLACK, 16));
		label.layoutXProperty().bind(xProperty());
		label.layoutYProperty().bind(yProperty().subtract(15));

		text = new TextField(data.get());
		text.layoutXProperty().bind(xProperty());
		text.layoutYProperty().bind(yProperty().subtract(10));

		labelProperty().bindBidirectional(getTextData().textProperty());

		addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent key) {
				setX(key.getX() - 100);
				setY(key.getY() - 100);
			}
		});

		label.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent key) {
				text.setVisible(true);
			}
		});

		DoubleProperty w = new SimpleDoubleProperty();
		text.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				w.set(label.layoutBoundsProperty().getValue().getWidth());
				if (e.getCode() == KeyCode.ENTER) {
					text.setVisible(false);
				}
			}
		});

		addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent key) {
				if (shape == null) {
					shape = new DropShadow();
				}
				setEffect(shape);
			}
		});

		addEventFilter(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent key) {
				setEffect(null);
			}
		});
	}

	public final StringProperty labelProperty() {
		return data;
	}

	public Text getLabel() {
		return label;
	}

	public TextField getTextData() {
		return text;
	}

	public TextField getText(boolean isShow) {
		text.setText(labelProperty().get());
		if (isShow) {
			text.setVisible(isShow);
		} else {
			text.setVisible(false);
		}
		return text;
	}

	public void setTextInVisible() {
		text.setVisible(false);
	}

}
