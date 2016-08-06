package UseCase;

import Libraries.CNode;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class UCInclude extends Line {

	protected CNode snode;// Start Node
	protected CNode enode;// End Node

	public Text label;
	public Path top;
	public Path bot;

	public UCInclude(double startx, double starty, double endx, double endy, Color color) {
		super(startx, starty, endx, endy);
		setStroke(Color.BLACK);

		label = new Text("<<Include>>");
		label.setFont(Font.font("Arial", FontWeight.NORMAL, 14));

		top = new Path();
		bot = new Path();

		snode = new CNode();
		snode.centerXProperty().bind(startXProperty());
		snode.centerYProperty().bind(startYProperty());

		enode = new CNode();
		enode.centerXProperty().bind(endXProperty());
		enode.centerYProperty().bind(endYProperty());

		snode.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				snode.centerXProperty().unbind();
				snode.centerYProperty().unbind();
				snode.setCenterX(e.getX());
				snode.setCenterY(e.getY());
				startXProperty().bind(snode.centerXProperty());
				startYProperty().bind(snode.centerYProperty());
				update();
			}
		});

		enode.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				enode.centerXProperty().unbind();
				enode.centerYProperty().unbind();
				enode.setCenterX(e.getX());
				enode.setCenterY(e.getY());
				endXProperty().bind(enode.centerXProperty());
				endYProperty().bind(enode.centerYProperty());
				update();
			}
		});
		
		label.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				label.setX(e.getX());
				label.setY(e.getY());
			}
		});
	}

	public void update() {
		double startx = getStartX();
		double starty = getStartY();
		double endx = getEndX();
		double endy = getEndY();

		// Arrow Head
		double x, y, length;
		length = Math.sqrt((endx - startx) * (endx - startx) + (endy - starty) * (endy - starty));
		x = (endx - startx) / length;
		y = (endy - starty) / length;
		Point2D base = new Point2D(endx - x * 10, endy - y * 10);
		Point2D back_top = new Point2D(base.getX() - 10 * y, base.getY() + 10 * x);
		Point2D back_bottom = new Point2D(base.getX() + 10 * y, base.getY() - 10 * x);
		top.getElements().clear();

		top.getElements().add(new MoveTo(endx, endy));
		top.getElements().add(new LineTo(back_top.getX(), back_top.getY()));
		top.getElements().add(new MoveTo(endx, endy));
		top.getElements().add(new LineTo(back_bottom.getX(), back_bottom.getY()));
		
		double varx=(getStartX()+getEndX())/2;
		double vary=(getStartY()+getEndY())/2;
		label.relocate(varx, vary);
		
	}

	public CNode getSnode() {
		return snode;
	}

	public CNode getEnode() {
		return enode;
	}
}
