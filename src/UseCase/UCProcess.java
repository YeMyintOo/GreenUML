package UseCase;

import java.io.IOException;

import XMLFactory.CopyXML;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UCProcess extends Ellipse {
	private CopyXML copy;
	public StringProperty data;
	private Text label;
	private TextField text;
	private DropShadow shape;

	private Button bL; // Left;
	private Button bR; // Right;

	final KeyCombination copyKey = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_ANY);

	public UCProcess(Stage owner, double centerX, double centerY, Color color) {
		super(centerX, centerY, 100, 40);
		setFill(color);
		setStroke(Color.GRAY);
		data = new SimpleStringProperty("Process");

		label = new Text();
		label.textProperty().bindBidirectional(labelProperty());
		label.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		label.layoutXProperty()
				.bind(centerXProperty().subtract(label.layoutBoundsProperty().getValue().getWidth() / 2));
		label.layoutYProperty().bind(centerYProperty());

		text = new TextField(data.get());
		text.layoutXProperty().bind(centerXProperty().subtract(70));
		text.layoutYProperty().bind(centerYProperty().subtract(15));

		labelProperty().bindBidirectional(getTextData().textProperty());

		addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent key) {
				setCenterX(key.getX());
				setCenterY(key.getY());
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

		DoubleProperty w = new SimpleDoubleProperty();

		text.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				w.set(label.layoutBoundsProperty().getValue().getWidth());
				label.layoutXProperty().bind(centerXProperty().subtract(label.getLayoutBounds().getWidth() / 2));
				if (w.get() > radiusXProperty().get()) {
					radiusXProperty().bind(w);
				}
				if (e.getCode() == KeyCode.ENTER) {
					text.setVisible(false);
				}
			}
		});
		text.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				text.requestFocus();
			}
		});

		label.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				text.setVisible(true);
			}
		});

		owner.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent key) {
				if (isHover()) {
					if (copyKey.match(key)) {
						System.out.println("Copy UseCase Process #Label-" + data.get());
						try {
							copy = new CopyXML();
						} catch (IOException e) {
							e.printStackTrace();
						}
						copy.copyUCProcess(getCenterX(), getCenterY(), getRadiusX(), getRadiusY(), data.get(),
								getFill().toString());
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
