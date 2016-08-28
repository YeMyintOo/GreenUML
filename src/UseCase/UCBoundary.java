package UseCase;

import java.io.IOException;

import Libraries.CNode;
import XMLFactory.CopyXML;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UCBoundary extends Rectangle {
	public StringProperty data;
	private Text label;
	private TextField text;
	private DropShadow shape;
	public CNode resizeHB;
	public CNode resizeVB;
	private CopyXML copy;
	final KeyCombination copyKey = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_ANY);

	public UCBoundary(Stage owner, double x, double y, Color color) {
		super(x, y, 300, 400);
		setX(x);
		setY(y);
		setFill(color);
		// setOpacity(0.3);
		setStroke(Color.BLACK);
		setArcWidth(10);
		setArcHeight(10);
		data = new SimpleStringProperty("Label");

		resizeHB = new CNode();
		resizeHB.centerXProperty().bind(xProperty().add(widthProperty()));
		resizeHB.centerYProperty().bind(yProperty().add(20));

		resizeHB.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent key) {
				if (key.getX() > resizeHB.getCenterX())
					setWidth(getWidth() + 2);
				if (key.getX() < resizeHB.getCenterX())
					setWidth(getWidth() - 2);
			}
		});

		resizeVB = new CNode();
		resizeVB.centerXProperty().bind(xProperty().add(20));
		resizeVB.centerYProperty().bind(yProperty().add(heightProperty()));

		resizeVB.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent key) {
				if (key.getY() > resizeVB.getCenterY())
					setHeight(getHeight() + 2);
				if (key.getY() < resizeVB.getCenterY())
					setHeight(getHeight() - 2);
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

		owner.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				if (isHover()) {
					if (copyKey.match(key)) {
						try {
							if (copy == null)
								copy = new CopyXML();
						} catch (IOException e) {
							e.printStackTrace();
						}
						copy.copyUCBoundary(getX(), getY(), getWidth(), getHeight(), data.get(), getFill().toString());
					}
				}
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
