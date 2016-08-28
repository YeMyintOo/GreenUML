package GUI;

import java.util.ArrayList;

import Hardware.Screen;
import Libraries.Tool;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Draw extends BorderPane {
	public String projectName;
	public int diagram;
	public Tool ctool; // Current Selected Tool Item
	public ArrayList<Object> objects;
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

	// Object ////////

	//////////////////

	// Sequence //////
	public Button serole; // Role
	public Button seactivation;
	public Button sedactivation;
	public Button senactivation;
	public Button sesactivation;
	//////////////////

	// Class////////////
	public Button classB;
	public Button interfaceB;
	public Button abstractB;
	public Button caggregationB;
	public Button cassicationB;
	public Button ccompositinB;
	public Button cdependencyB;
	public Button cinheritanceB;
	///////////////////

	// State Chart//////////
	public Button stateStartB;
	public Button stateFinalB;
	public Button stateHistoryB;
	public Button stateB;
	public Button subStateB;
	public Button stateTranB;

	////////////////////////

	// Activity//////////////
	public Button aactionB;
	public Button aedgeB;
	public Button aendnodeB;
	public Button ainitnodeB;
	public Button aregionB;
	public Button atimeB;
	////////////////////////

	// Component ////////////
	public Button coartifactB;
	public Button cocompoentB;
	public Button codependB;
	public Button colibraryB;
	public Button copackageB;
	public Button coscomponentB;
	////////////////////////

	/// Development ///////////
	public Button dcomponentB;
	public Button ddatabaseB;
	public Button ddeviceB;
	public Button dfileB;
	public Button dprotocalB;
	public Button dsoftwareB;
	public Button dsystemB;
	//////////////////////////

	public Draw(Scene scene, int diagram, String projectName) {
		ctool = Tool.POINTER;
		objects = new ArrayList<Object>();
		area = new Pane();
		tool = new VBox();
		screen = new Screen();
		this.projectName = projectName;
		this.diagram = diagram;

		System.out.println(" Project Name :" + projectName);
		System.out.println(" Diagram :" + diagram);

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
		case 3:
			initLoadSE();
		case 4:
			break;
		case 5:
			initLoadC();
			break;
		case 6:
			initLoadS();
			break;
		case 7:
			initLoadA();
			break;
		case 8:
			initLoadCO();
			break;
		case 9:
			initLoadD();
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

	public void initLoadSE() {
		serole = new Button("R");
		seactivation = new Button("A");
		sedactivation = new Button("D");
		senactivation = new Button("N");
		sesactivation = new Button("S");

		tool.getChildren().addAll(serole, seactivation, sedactivation, senactivation, sesactivation);

		serole.setOnAction(e -> {
			ctool = Tool.SEROLE;
		});
		seactivation.setOnAction(e -> {
			ctool = Tool.SEACTIVATION;
		});
		sedactivation.setOnAction(e -> {
			ctool = Tool.SEDESTROYACTIVATION;
		});
		senactivation.setOnAction(e -> {
			ctool = Tool.SENEWACTIVATION;
		});
		sesactivation.setOnAction(e -> {
			ctool = Tool.SESELFACTIVATION;
		});
	}

	public void initLoadC() {
		classB = new Button("C");
		interfaceB = new Button("I");
		abstractB = new Button("A");
		caggregationB = new Button("A");
		cassicationB = new Button("A");
		ccompositinB = new Button("C");
		cdependencyB = new Button("d");
		cinheritanceB = new Button("I");

		tool.getChildren().addAll(classB, interfaceB, abstractB, caggregationB, cassicationB, ccompositinB,
				cdependencyB, cinheritanceB);

		classB.setOnAction(e -> {
			ctool = Tool.CLASS;
		});
		interfaceB.setOnAction(e -> {
			ctool = Tool.CINTERFACE;
		});
		abstractB.setOnAction(e -> {
			ctool = Tool.CABSTRACT;
		});
		caggregationB.setOnAction(e -> {
			ctool = Tool.CAGGREGATION;
		});
		cassicationB.setOnAction(e -> {
			ctool = Tool.CASSOCIATION;
		});
		ccompositinB.setOnAction(e -> {
			ctool = Tool.CCOMPOSITION;
		});
		cdependencyB.setOnAction(e -> {
			ctool = Tool.CDEPENDENCY;
		});
		cinheritanceB.setOnAction(e -> {
			ctool = Tool.CINHERITANCE;
		});
	}

	public void initLoadS() {
		stateStartB = new Button("S");
		stateFinalB = new Button("F");
		stateHistoryB = new Button("H");
		stateB = new Button("SS");
		subStateB = new Button("sS");
		stateTranB = new Button("T");

		stateStartB.setOnAction(e -> {
			System.out.println(":State Start");
			ctool = Tool.STATESTART;
		});
		stateFinalB.setOnAction(e -> {
			System.out.println(":State Final");
			ctool = Tool.STATEFINAL;
		});
		stateHistoryB.setOnAction(e -> {
			ctool = Tool.STATEHISTORY;
		});
		stateB.setOnAction(e -> {
			ctool = Tool.STATE;
		});
		subStateB.setOnAction(e -> {
			ctool = Tool.SUBSTATE;
		});
		stateTranB.setOnAction(e -> {
			ctool = Tool.STATETRANSITION;
		});

		tool.getChildren().addAll(stateStartB, stateFinalB, stateHistoryB, stateB, subStateB, stateTranB);

	}

	public void initLoadA() {
		aactionB = new Button("A");
		aedgeB = new Button("E");
		aendnodeB = new Button("E");
		ainitnodeB = new Button("I");
		aregionB = new Button("R");
		atimeB = new Button("T");

		aactionB.setOnAction(e -> {
			ctool = Tool.AACTION;
		});
		aedgeB.setOnAction(e -> {
			ctool = Tool.AEDGE;
		});
		aendnodeB.setOnAction(e -> {
			ctool = Tool.AENDNODE;
		});
		ainitnodeB.setOnAction(e -> {
			ctool = Tool.AINITNODE;
		});
		aregionB.setOnAction(e -> {
			ctool = Tool.AREGION;
		});
		atimeB.setOnAction(e -> {
			ctool = Tool.ATIME;
		});

		tool.getChildren().addAll(aactionB, aedgeB, aendnodeB, ainitnodeB, aregionB, atimeB);
	}

	public void initLoadCO() {
		coartifactB = new Button("A");
		cocompoentB = new Button("C");
		codependB = new Button("D");
		colibraryB = new Button("L");
		copackageB = new Button("P");
		coscomponentB = new Button("sC");

		coartifactB.setOnAction(e -> {
			ctool = Tool.COARTEFACT;
		});
		cocompoentB.setOnAction(e -> {
			ctool = Tool.COCOMPONENT;
		});
		codependB.setOnAction(e -> {
			ctool = Tool.CODEPEND;
		});
		colibraryB.setOnAction(e -> {
			ctool = Tool.COLIBRARY;
		});
		copackageB.setOnAction(e -> {
			ctool = Tool.COPACKAGE;
		});
		coscomponentB.setOnAction(e -> {
			ctool = Tool.COSCOMPONENT;
		});

		tool.getChildren().addAll(coartifactB, cocompoentB, codependB, colibraryB, copackageB, coscomponentB);
	}

	public void initLoadD() {
		dcomponentB = new Button("C");
		ddatabaseB = new Button("D");
		ddeviceB = new Button("D");
		dfileB = new Button("F");
		dprotocalB = new Button("P");
		dsoftwareB = new Button("S");
		dsystemB = new Button("S");

		dcomponentB.setOnAction(e -> {
			ctool = Tool.DCOMPONENT;
		});
		ddatabaseB.setOnAction(e -> {
			ctool = Tool.DDATABASE;
		});
		ddeviceB.setOnAction(e -> {
			ctool = Tool.DDEVICE;
		});
		dfileB.setOnAction(e -> {
			ctool = Tool.DFILE;
		});
		dprotocalB.setOnAction(e -> {
			ctool = Tool.DPROTOCAL;
		});
		dsoftwareB.setOnAction(e -> {
			ctool = Tool.DSOFTWARE;
		});
		dsystemB.setOnAction(e -> {
			ctool = Tool.DSYSTEM;
		});

		tool.getChildren().addAll(dcomponentB, ddatabaseB,ddeviceB,dfileB, dprotocalB, dsoftwareB, dsystemB);
	}

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
