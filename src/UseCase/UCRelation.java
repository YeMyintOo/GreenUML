package UseCase;

import Libraries.CNode;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class UCRelation extends Line {

	public CNode snode;// Start Node
	public CNode enode;// End Node
	public CNode mnode;// Middle Node

	public UCRelation(double startx, double starty, double endx, double endy) {
		super(startx, starty, endx, endy);
		setStroke(Color.BLACK);
		snode = new CNode();
		snode.centerXProperty().bind(startXProperty());
		snode.centerYProperty().bind(startYProperty());

		enode = new CNode();
		enode.centerXProperty().bind(endXProperty());
		enode.centerYProperty().bind(endYProperty());

		mnode = new CNode();
		mnode.setFill(Color.RED);
		mnode.centerXProperty().bind((startXProperty().add(endXProperty())).divide(2));
		mnode.centerYProperty().bind((startYProperty().add(endYProperty())).divide(2));

		snode.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				snode.centerXProperty().unbind();
				snode.centerYProperty().unbind();
				snode.setCenterX(e.getX());
				snode.setCenterY(e.getY());
				startXProperty().bind(snode.centerXProperty());
				startYProperty().bind(snode.centerYProperty());
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
			}
		});

		mnode.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				mnode.centerXProperty().unbind();
				mnode.centerYProperty().unbind();
				
				if(e.getX()>mnode.getCenterX()){
					mnode.setCenterX(e.getX());
					endXProperty().bind(mnode.centerXProperty());
					startXProperty().bind(mnode.centerXProperty());
				}
				
				
				
				//mnode.setCenterY(e.getY());

				

			}
		});

	}

}
