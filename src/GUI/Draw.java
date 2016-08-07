package GUI;

import Hardware.Screen;
import Libraries.Tool;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Draw extends BorderPane {
	public Tool ctool; // Current Selected Tool Item
	Screen screen;
	private Pane area; // Draw
	private VBox tool; // Tool Bar

	public Button glabel;// General Label;

	// Use Case////////
	public Button ucprocess; // Process
	public Button ucactor; // Actor
	public Button ucrealation; // Relation
	public Button ucgeneral; // General
	public Button ucboundary; // Boundary
	public Button ucinclude; // Include
	public Button ucextend; // Include
	//////////////////

	public Draw(Scene scene, int diagram) {
		ctool = Tool.POINTER;
		area = new Pane();
		tool = new VBox();
		screen = new Screen();

		setCenter(area);
		setRight(tool);

		tool.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
				+ "-fx-border-radius: 5;");
		setPrefWidth(screen.getWidth());
		setMaxWidth(screen.getWidth());
		setStyle("-fx-background-color:white;");

		glabel = new Button("G");

		switch (diagram) {
		case 1:
			initLoadUC();
			break;
		case 2:
			break;
		}

		tool.getChildren().addAll(glabel);

		glabel.setOnAction(e -> {
			ctool = Tool.GLabel;
		});
	}

	// Initial Tool Loading...
	public void initLoadUC() {
		ucprocess = new Button("P");
		Tooltip ucp = new Tooltip("Process");
		Tooltip.install(ucprocess, ucp);

		ucactor = new Button("A");
		ucrealation = new Button("R");
		ucgeneral = new Button("G");
		ucboundary = new Button("B");
		ucinclude = new Button("I");
		ucextend = new Button("E");

		tool.getChildren().addAll(ucprocess, ucactor, ucrealation, ucgeneral, ucboundary, ucinclude, ucextend);

		ucprocess.setOnAction(e -> {
			ctool = Tool.UCPROCESS;
		});
		ucactor.setOnAction(e -> {
			ctool = Tool.UCACTOR;
		});
		ucrealation.setOnAction(e -> {
			ctool = Tool.UCREALATION;
		});
		ucgeneral.setOnAction(e -> {
			ctool = Tool.UCGENERAL;
		});
		ucboundary.setOnAction(e -> {
			ctool = Tool.UCBOUNDARY;
		});
		ucinclude.setOnAction(e -> {
			ctool = Tool.UCINCLUDE;
		});
		ucextend.setOnAction(e -> {
			ctool = Tool.UCEXTEND;
		});
	}
	//

	public Tool getCTool() {
		return ctool;
	}

	public Pane getArea() {
		return area;
	}

	public void setCTool(Tool tool) {
		ctool = tool;
	}
}
