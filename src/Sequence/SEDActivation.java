package Sequence;

import Libraries.CNode;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class SEDActivation extends Line {

	public Line top;
	public Line bot;
	public Line c1;
	public Line c2;
	public CNode enode;

	public SEDActivation(double startx, double starty, double endx, double endy) {
		super(startx, starty, endx, endy);
		setStroke(Color.BLACK);

		top = new Line();
		top.setStroke(Color.BLACK);
		top.startXProperty().bind(endXProperty());
		top.startYProperty().bind(endYProperty());
		top.endXProperty().bind(endXProperty().subtract(10));
		top.endYProperty().bind(endYProperty().subtract(5));

		bot = new Line();
		bot.setStroke(Color.BLACK);
		bot.startXProperty().bind(endXProperty());
		bot.startYProperty().bind(endYProperty());
		bot.endXProperty().bind(endXProperty().subtract(10));
		bot.endYProperty().bind(endYProperty().add(5));

		c1 = new Line();
		c1.setStroke(Color.LIGHTGRAY);
		c1.startXProperty().bind(endXProperty().subtract(15));
		c1.startYProperty().bind(endYProperty().subtract(15));
		c1.endXProperty().bind(endXProperty().add(15));
		c1.endYProperty().bind(endYProperty().add(15));
		c1.setStrokeWidth(4);

		c2 = new Line(getEndX() - 15, getEndY() + 15, getEndX() + 15, getEndY() - 15);
		c2.setStroke(Color.LIGHTGRAY);
		c2.startXProperty().bind(endXProperty().subtract(15));
		c2.startYProperty().bind(endYProperty().add(15));
		c2.endXProperty().bind(endXProperty().add(15));
		c2.endYProperty().bind(endYProperty().subtract(15));
		c2.setStrokeWidth(4);

		enode = new CNode();
		enode.setRadius(15);
		enode.centerXProperty().bindBidirectional(endXProperty());
		enode.centerYProperty().bindBidirectional(endYProperty());

		enode.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				setEndX(e.getX());
			}
		});

		startYProperty().bindBidirectional(endYProperty());
	}

}
