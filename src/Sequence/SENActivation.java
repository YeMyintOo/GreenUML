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

public class SENActivation extends Line {

	private Color color;
	private DropShadow shape;
	private DoubleProperty life;

	private DoubleProperty lifeP;
	private StringProperty data;

	public Line top;
	public Line bot;
	public Line nLine;
	public Rectangle newOb;
	public Text label;
	public Rectangle lifeB;
	public Line rLine;
	public Line rtop;
	public Line rbot;
	public TextField field;

	public SENActivation(double startx, double starty, double endx, double endy, Color color) {
		super(startx, starty, endx, endy);
		setStroke(Color.BLACK);
		life = new SimpleDoubleProperty(100);
		lifeP = new SimpleDoubleProperty(150);
		data = new SimpleStringProperty(":Role");
		shape = new DropShadow();

		newOb = new Rectangle();
		newOb.setWidth(110);
		newOb.setFill(color);
		newOb.setStroke(Color.LIGHTGRAY);
		newOb.setHeight(50);
		newOb.xProperty().bindBidirectional(endXProperty());
		DoubleProperty y = new SimpleDoubleProperty();
		y.set(endYProperty().subtract(27).doubleValue());
		newOb.yProperty().bindBidirectional(y);
		newOb.yProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				endYProperty().bind(newOb.yProperty().add(20));
			}
		});

		nLine = new Line();
		nLine.getStrokeDashArray().addAll(10d, 10d);
		nLine.startXProperty().bind(newOb.xProperty().add(newOb.widthProperty().getValue() / 2));
		nLine.startYProperty().bind(newOb.yProperty().add(newOb.getHeight()));
		nLine.endXProperty().bind(newOb.xProperty().add(newOb.widthProperty().getValue() / 2));
		nLine.endYProperty().bind(newOb.yProperty().add(newOb.getHeight()).add(life));

		label = new Text(data.get());
		label.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		label.textProperty().bindBidirectional(data);
		label.xProperty().bind(newOb.xProperty().add(newOb.widthProperty().getValue() / 2)
				.subtract(label.layoutBoundsProperty().getValue().getWidth() / 2));
		label.yProperty().bind(newOb.yProperty().add(27));

		lifeB = new Rectangle();
		lifeB.setFill(Color.LIGHTGRAY);
		lifeB.setStroke(Color.BLACK);
		lifeB.setHeight(100);
		lifeB.setWidth(20);
		lifeB.xProperty().bind(newOb.xProperty().add(newOb.widthProperty().getValue() / 2).subtract(10));
		lifeB.yProperty().bind(newOb.yProperty().add(newOb.getHeight()));
		lifeB.heightProperty().bind(life);

		top = new Line();
		top.startXProperty().bind(endXProperty());
		top.startYProperty().bind(endYProperty());
		top.endXProperty().bind(endXProperty().subtract(10));
		top.endYProperty().bind(endYProperty().subtract(5));

		bot = new Line();
		bot.startXProperty().bind(endXProperty());
		bot.startYProperty().bind(endYProperty());
		bot.endXProperty().bind(endXProperty().subtract(10));
		bot.endYProperty().bind(endYProperty().add(5));

		rLine = new Line();
		rLine.getStrokeDashArray().addAll(10d, 5d);
		rLine.startXProperty().bind(lifeB.xProperty());
		rLine.startYProperty().bind(lifeB.yProperty().add(life));
		rLine.endYProperty().bind(lifeB.yProperty().add(life));
		rLine.endXProperty().bind(startXProperty());

		rtop = new Line();
		rtop.startXProperty().bind(rLine.endXProperty());
		rtop.startYProperty().bind(rLine.endYProperty());
		rtop.endXProperty().bind(rLine.endXProperty().add(10));
		rtop.endYProperty().bind(rLine.endYProperty().subtract(5));

		rbot = new Line();
		rbot.startXProperty().bind(rLine.endXProperty());
		rbot.startYProperty().bind(rLine.endYProperty());
		rbot.endXProperty().bind(rLine.endXProperty().add(10));
		rbot.endYProperty().bind(rLine.endYProperty().add(5));

		field = new TextField(data.get());
		field.layoutXProperty().bind(newOb.xProperty().subtract(25));
		field.layoutYProperty().bind(newOb.yProperty().add(10));
		field.textProperty().bindBidirectional(data);

		startYProperty().bindBidirectional(endYProperty());
		newOb.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				nLine.startXProperty().bind(newOb.xProperty().add(newOb.widthProperty().getValue() / 2));
				nLine.startYProperty().bind(newOb.yProperty().add(newOb.getHeight()));
				nLine.endXProperty().bind(newOb.xProperty().add(newOb.widthProperty().getValue() / 2));
				nLine.endYProperty().bind(newOb.yProperty().add(newOb.getHeight()).add(lifeP));
				lifeB.xProperty().bind(newOb.xProperty().add(newOb.widthProperty().getValue() / 2).subtract(10));
				lifeB.yProperty().bind(newOb.yProperty().add(newOb.getHeight()));
			}
		});

		lifeB.setOnScroll(new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent s) {
				if (s.getDeltaY() == 40) {
					life.set(life.get() + 10);
				} else {
					life.set(life.get() - 10);
				}
			}
		});

		lifeB.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent key) {
				lifeB.setEffect(shape);
			}
		});
		lifeB.addEventFilter(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent key) {
				lifeB.setEffect(null);
			}
		});

		newOb.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent key) {
				newOb.setEffect(shape);
			}
		});
		newOb.addEventFilter(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent key) {
				newOb.setEffect(null);
			}
		});

		newOb.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				newOb.setX(e.getX());
				newOb.setY(e.getY());
			}
		});

		newOb.setOnScroll(new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent s) {
				if (s.getDeltaY() == 40) {
					lifeP.set(lifeP.get() + 10);
				} else {
					lifeP.set(lifeP.get() - 10);
				}
			}
		});

		DoubleProperty w = new SimpleDoubleProperty();
		field.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				w.set(label.layoutBoundsProperty().getValue().getWidth());
				label.xProperty().bind(newOb.xProperty().add(newOb.widthProperty().getValue() / 2)
						.subtract(label.layoutBoundsProperty().getValue().getWidth() / 2));
				if (w.get() > newOb.widthProperty().get()) {
					newOb.widthProperty().bind(w.add(30));
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
				System.out.println("Click on Process Label");
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

	public void checkLabel() {
		String var = data.get();
		if (var.startsWith(":") || var.contains(":")) {
			System.out.println("Sting is Validate");
			label.setFill(Color.BLACK);
			label.setUnderline(false);
		} else {
			System.out.println("Sting is not Validate");
			label.setFill(Color.RED);
			label.setUnderline(true);
		}
	}

}
