package Sequence;

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
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class SEActivation extends Line {
	private DropShadow shape;
	public Line top;
	public Line bot;
	public DoubleProperty life;
	public Rectangle activate;
	public Line rLine; // Return Line
	public Line rtop;
	public Line rbot;

	public StringProperty msgData;
	public Text msg;
	public TextField text;

	public CNode snode;

	public SEActivation(double startx, double starty, double endx, double endy, Color color) {
		super(startx, starty, endx, endy);
		setFill(color);

		life = new SimpleDoubleProperty(100);

		activate = new Rectangle();
		activate.xProperty().bindBidirectional(endXProperty());
		activate.yProperty().bindBidirectional(endYProperty());
		activate.setWidth(20);
		activate.setFill(color);
		activate.setStroke(Color.BLACK);
		activate.heightProperty().bind(life);

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
		rLine.startXProperty().bind(activate.xProperty());
		rLine.startYProperty().bind(activate.yProperty().add(life));
		rLine.endYProperty().bind(activate.yProperty().add(life));
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

		msgData = new SimpleStringProperty("function()");
		msg = new Text();
		msg.textProperty().bindBidirectional(msgData);
		msg.xProperty().bind((startXProperty().add(endXProperty()).divide(2)));
		msg.yProperty().bind(startYProperty().subtract(15));

		text = new TextField();
		text.textProperty().bindBidirectional(msgData);
		text.layoutXProperty().bind(msg.xProperty());
		text.layoutYProperty().bind(msg.yProperty().subtract(20));

		snode = new CNode();
		snode.centerXProperty().bindBidirectional(startXProperty());
		snode.centerYProperty().bindBidirectional(startYProperty());

		startYProperty().bindBidirectional(endYProperty());

		activate.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				activate.setX(e.getX());
				activate.setY(e.getY());
			}
		});

		activate.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent key) {
				if (shape == null) {
					shape = new DropShadow();
				}
				activate.setEffect(shape);
			}
		});
		activate.addEventFilter(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent key) {
				activate.setEffect(null);
			}
		});

		msg.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				msg.xProperty().unbind();
				msg.setX(e.getX());
			}
		});

		snode.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				snode.setCenterX(e.getX());
				snode.setCenterY(e.getY());
			}
		});

		activate.setOnScroll(new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent s) {
				if (s.getDeltaY() == 40) {
					life.set(life.get() + 10);
				} else {
					life.set(life.get() - 10);
				}
			}
		});

		text.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				if (e.getCode() == KeyCode.ENTER) {
					text.setVisible(false);
					checkLabel();
				}
			}
		});

		msg.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (e.getClickCount() == 2)
					text.setVisible(true);
			}
		});
	}

	public TextField getText(boolean isShow) {
		if (isShow) {
			text.setVisible(true);
		} else {
			text.setVisible(false);
		}
		return text;
	}

	public void checkLabel() {
		String var = msgData.get();
		if (var.endsWith("()")) {
			msg.setFill(Color.BLACK);
			msg.setUnderline(false);
		} else {
			msg.setFill(Color.RED);
			msg.setUnderline(true);
		}
	}

}
