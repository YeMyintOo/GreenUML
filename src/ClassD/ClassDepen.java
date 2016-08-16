package ClassD;

import Libraries.CNode;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class ClassDepen extends Line {

	private StringProperty data1; // Start Point Multiplicity
	private StringProperty data2; // End Point Multiplicity

	public Line l1;
	public Line l2;
	public Line l3;

	public CNode node1;
	public CNode node2;
	public CNode startNode;
	public CNode endNode;

	public Line top;
	public Line bot;

	private boolean isdrawable;

	public ClassDepen(double startx, double starty, double endx, double endy, Color color) {
		super(startx, starty, endx, endy);
		setStroke(color);

		data1 = new SimpleStringProperty("1");
		data2 = new SimpleStringProperty("1");

		node1 = new CNode();
		node2 = new CNode();
		startNode = new CNode();
		endNode = new CNode();

		l1 = new Line();
		l1.setStroke(Color.BLACK);

		l2 = new Line();
		l2.setStroke(Color.BLACK);

		l3 = new Line();
		l3.setStroke(Color.BLACK);

		top = new Line();
		top.setStroke(Color.BLACK);

		bot = new Line();
		bot.setStroke(Color.BLACK);

		node1.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				node1.setCenterX(e.getX());
				node1.setCenterY(e.getY());
				startNode.setCenterY(e.getY());
			}
		});

		node2.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				node2.setCenterX(e.getX());
				node2.setCenterY(e.getY());
				endNode.setCenterY(e.getY());
			}
		});
		startNode.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				startNode.setCenterX(e.getY());
			}
		});
		endNode.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				endNode.setCenterX(e.getX());
			}
		});
	}

	public boolean filterLine() {
		isdrawable = true;
		double d = Math.sqrt((Math.pow(getEndX() - getStartX(), 2)) + (Math.pow(getEndY() - getStartY(), 2)));
		double mid = d / 2;
		double slope = (getStartY() - getEndY()) / (getStartX() - getEndX());
		top.startXProperty().bind(endXProperty());
		top.startYProperty().bind(endYProperty());
		bot.startXProperty().bind(endXProperty());
		bot.startYProperty().bind(endYProperty());

		if (getStartX() < getEndX() && getStartY() < getEndY()) {
			if (slope < 2) {
				node1.setCenterX(getStartX() + mid);
				node1.setCenterY(getStartY());

				node2.setCenterX(getStartX() + mid);
				node2.setCenterY(getEndY());

				startNode.setCenterX(getStartX());
				startNode.setCenterY(getStartY());

				endNode.setCenterX(getEndX());
				endNode.setCenterY(getEndY());

				l1.setStartX(getStartX());
				l1.setStartY(getStartY());
				l1.setEndX(getStartX() + mid);
				l1.setEndY(getStartY());

				l2.setStartX(getStartX() + mid);
				l2.setStartY(getStartY());
				l2.setEndX(getStartX() + mid);
				l2.setEndY(getEndY());

				l3.setStartX(getStartX() + mid);
				l3.setStartY(getEndY());
				l3.setEndX(getEndX());
				l3.setEndY(getEndY());

				top.endXProperty().bind(endXProperty().subtract(10));
				top.endYProperty().bind(endYProperty().subtract(10));

				bot.endXProperty().bind(endXProperty().subtract(10));
				bot.endYProperty().bind(endYProperty().add(10));

			} else {
				isdrawable = false;
				System.out.println("You cann't daraw This Line");
			}
		}

		if (getStartX() > getEndX() && getStartY() > getEndY()) {
			if (slope < 2) {
				node1.setCenterX(getStartX() - mid);
				node1.setCenterY(getStartY());

				node2.setCenterX(getStartX() - mid);
				node2.setCenterY(getEndY());

				startNode.setCenterX(getStartX());
				startNode.setCenterY(getStartY());

				endNode.setCenterX(getEndX());
				endNode.setCenterY(getEndY());

				top.endXProperty().bind(endXProperty().add(10));
				top.endYProperty().bind(endYProperty().subtract(10));

				bot.endXProperty().bind(endXProperty().add(10));
				bot.endYProperty().bind(endYProperty().add(10));

				l1.setStartX(getStartX());
				l1.setStartY(getStartY());
				l1.setEndX(getStartX() - mid);
				l1.setEndY(getStartY());

				l2.setStartX(getStartX() - mid);
				l2.setStartY(getStartY());
				l2.setEndX(getStartX() - mid);
				l2.setEndY(getEndY());

				l3.setStartX(getStartX() - mid);
				l3.setStartY(getEndY());
				l3.setEndX(getEndX());
				l3.setEndY(getEndY());

			} else {
				isdrawable = false;
				System.out.println("You cann't daraw This Line");
			}
		}

		if (getStartX() > getEndX() && getStartY() < getEndY()) {
			if (slope > -1.5) {
				node1.setCenterX(getStartX() - mid);
				node1.setCenterY(getStartY());

				node2.setCenterX(getStartX() - mid);
				node2.setCenterY(getEndY());

				startNode.setCenterX(getStartX());
				startNode.setCenterY(getStartY());

				endNode.setCenterX(getEndX());
				endNode.setCenterY(getEndY());

				top.endXProperty().bind(endXProperty().add(10));
				top.endYProperty().bind(endYProperty().subtract(10));

				bot.endXProperty().bind(endXProperty().add(10));
				bot.endYProperty().bind(endYProperty().add(10));

				l1.setStartX(getStartX());
				l1.setStartY(getStartY());
				l1.setEndX(getStartX() - mid);
				l1.setEndY(getStartY());

				l2.setStartX(getStartX() - mid);
				l2.setStartY(getStartY());
				l2.setEndX(getStartX() - mid);
				l2.setEndY(getEndY());

				l3.setStartX(getStartX() - mid);
				l3.setStartY(getEndY());
				l3.setEndX(getEndX());
				l3.setEndY(getEndY());
			} else {
				isdrawable = false;
				System.out.println("You cann't daraw This Line");
			}
		}

		if (getStartX() < getEndX() && getStartY() > getEndY()) {
			if (slope > -1.5) {
				node1.setCenterX(getStartX() + mid);
				node1.setCenterY(getStartY());

				node2.setCenterX(getStartX() + mid);
				node2.setCenterY(getEndY());

				startNode.setCenterX(getStartX());
				startNode.setCenterY(getStartY());

				endNode.setCenterX(getEndX());
				endNode.setCenterY(getEndY());

				top.endXProperty().bind(endXProperty().subtract(10));
				top.endYProperty().bind(endYProperty().subtract(10));

				bot.endXProperty().bind(endXProperty().subtract(10));
				bot.endYProperty().bind(endYProperty().add(10));

				l1.setStartX(getStartX());
				l1.setStartY(getStartY());
				l1.setEndX(getStartX() + mid);
				l1.setEndY(getStartY());

				l2.setStartX(getStartX() + mid);
				l2.setStartY(getStartY());
				l2.setEndX(getStartX() + mid);
				l2.setEndY(getEndY());

				l3.setStartX(getStartX() + mid);
				l3.setStartY(getEndY());
				l3.setEndX(getEndX());
				l3.setEndY(getEndY());
			} else {
				isdrawable = false;
				System.out.println("You cann't daraw This Line");
			}
		}

		if (getStartX() < getEndX() && getStartY() == getEndY()) {
			if (slope == -0.0) {
				node1.setCenterX(getStartX() + mid);
				node1.setCenterY(getStartY());

				node2.setCenterX(getStartX() + mid);
				node2.setCenterY(getEndY());

				startNode.setCenterX(getStartX());
				startNode.setCenterY(getStartY());

				endNode.setCenterX(getEndX());
				endNode.setCenterY(getEndY());

				top.endXProperty().bind(endXProperty().subtract(10));
				top.endYProperty().bind(endYProperty().subtract(10));

				bot.endXProperty().bind(endXProperty().subtract(10));
				bot.endYProperty().bind(endYProperty().add(10));

				l1.setStartX(getStartX());
				l1.setStartY(getStartY());
				l1.setEndX(getStartX() + mid);
				l1.setEndY(getStartY());

				l2.setStartX(getStartX() + mid);
				l2.setStartY(getStartY());
				l2.setEndX(getStartX() + mid);
				l2.setEndY(getEndY());

				l3.setStartX(getStartX() + mid);
				l3.setStartY(getEndY());
				l3.setEndX(getEndX());
				l3.setEndY(getEndY());
			} else {
				isdrawable = false;
				System.out.println("You cann't daraw This Line");
			}
		}

		if (getStartX() > getEndX() && getStartY() == getEndY()) {
			if (slope == -0.0) {
				node1.setCenterX(getStartX());
				node1.setCenterY(getStartY() + mid);

				node2.setCenterX(getStartX());
				node2.setCenterY(getEndY() + mid);

				startNode.setCenterX(getStartX());
				startNode.setCenterY(getStartY());

				endNode.setCenterX(getEndX());
				endNode.setCenterY(getEndY());

				top.endXProperty().bind(endXProperty().add(10));
				top.endYProperty().bind(endYProperty().subtract(10));

				bot.endXProperty().bind(endXProperty().add(10));
				bot.endYProperty().bind(endYProperty().add(10));

				l1.setStartX(getStartX());
				l1.setStartY(getStartY());
				l1.setEndX(getStartX());
				l1.setEndY(getStartY() + mid);

				l2.setStartX(getStartX() - mid);
				l2.setStartY(getStartY());
				l2.setEndX(getStartX() - mid);
				l2.setEndY(getEndY());

				l3.setStartX(getStartX() - mid);
				l3.setStartY(getEndY());
				l3.setEndX(getEndX());
				l3.setEndY(getEndY());

			} else {
				isdrawable = false;
				System.out.println("You cann't daraw This Line");
			}
		}

		if (getStartX() == getEndX() && getStartY() < getEndY()) {
		}

		if (getStartX() == getEndX() && getStartY() > getEndY()) {
		}

		l1.startXProperty().bind(startNode.centerXProperty());
		l3.endXProperty().bind(endNode.centerXProperty());
		l1.endXProperty().bind(node1.centerXProperty());
		l1.startYProperty().bind(node1.centerYProperty());
		l1.endYProperty().bind(node1.centerYProperty());
		l2.startYProperty().bind(node1.centerYProperty());
		l2.startXProperty().bind(node1.centerXProperty());
		l2.endXProperty().bind(node2.centerXProperty());
		l2.endYProperty().bind(node2.centerYProperty());
		l3.startXProperty().bind(node2.centerXProperty());
		l3.startYProperty().bind(node2.centerYProperty());
		l3.endYProperty().bind(node2.centerYProperty());

		startXProperty().bind(l1.startXProperty());
		startYProperty().bind(l1.endYProperty());
		endXProperty().bind(l3.endXProperty());
		endYProperty().bind(l3.endYProperty());
		return isdrawable;
	}

	public StringProperty startMultiplicity() {
		return data1;
	}

	public StringProperty endMultiplicity() {
		return data2;
	}

}
