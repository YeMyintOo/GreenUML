package Sequence;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class SERole extends Rectangle {
	private DropShadow shape;
	public StringProperty data;
	public DoubleProperty life;
	public Text label;
	public Line line;
	public TextField field;

	public SERole(double x, double y, Color bgcolor) {
		super(x, y, 110, 50);
		setFill(bgcolor);
		setStroke(Color.BLACK);
		data = new SimpleStringProperty(":Role");
		life = new SimpleDoubleProperty(100);

		label = new Text();
		label.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		label.textProperty().bind(data);
		label.xProperty().bind(xProperty().add(widthProperty().getValue() / 2)
				.subtract(label.layoutBoundsProperty().getValue().getWidth() / 2));
		label.yProperty().bind(yProperty().add(27));
		line = new Line(getX() + (getWidth() / 2), getY() + getHeight(), getX() + (getWidth() / 2),
				getY() + life.add(10).doubleValue());
		line.setStroke(Color.BLACK);
		line.getStrokeDashArray().addAll(10d, 10d);
		line.startXProperty().bind(xProperty().add(getWidth() / 2));
		line.startYProperty().bind(yProperty().add(getHeight()));
		line.endXProperty().bind(xProperty().add(getWidth() / 2));
		line.endYProperty().bind(life.add(10).add(yProperty()));

		field = new TextField(data.get());
		field.layoutXProperty().bind(xProperty().subtract(25));
		field.layoutYProperty().bind(yProperty().add(10));
		field.textProperty().bindBidirectional(data);

		widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				line.startXProperty().bind(xProperty().add(getWidth() / 2));
				line.startYProperty().bind(yProperty().add(getHeight()));
				line.endXProperty().bind(xProperty().add(getWidth() / 2));
				line.endYProperty().bind(life.add(10).add(yProperty()));
			}
		});

		addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent key) {
				setX(key.getX()-20);
				setY(key.getY()-20);
			}
		});

		setOnScroll(new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent s) {
				if (life.get() > 20) {
					if (s.getDeltaY() == 40) {
						life.set(life.getValue() + 10);
					} else {
						life.set(life.getValue() - 10);
					}
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

		DoubleProperty w = new SimpleDoubleProperty();
		field.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				w.set(label.layoutBoundsProperty().getValue().getWidth());
				label.xProperty().bind(xProperty().add(widthProperty().getValue() / 2)
						.subtract(label.layoutBoundsProperty().getValue().getWidth() / 2));
				if (w.get() > widthProperty().get()) {
					widthProperty().bind(w.add(30));
				}
				if (e.getCode() == KeyCode.ENTER) {
					field.setVisible(false);
					checkLabel();
				}
			}
		});

		label.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				field.setVisible(true);
			}
		});

	}

	public TextField getText(boolean isShow) {
		field.setText(data.get());
		if (isShow) {
			field.setVisible(isShow);
		} else {
			field.setVisible(false);
		}
		return field;
	}

	public void checkLabel(){
		String var=data.get();
		if(var.startsWith(":") || var.startsWith("/") || var.contains(":")){
			System.out.println("Sting is Validate");
			label.setFill(Color.BLACK);
			label.setUnderline(false);
		}else{
			System.out.println("Sting is not Validate");
			label.setFill(Color.RED);
			label.setUnderline(true);
		}
	}
}
