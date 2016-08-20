package Libraries;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import GUI.Home;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

public class MenusLib {

	public DocumentBuilderFactory dbFactory;
	public DocumentBuilder dBuilder;
	public Document doc;
	public final KeyCombination pasteKey;
	public final KeyCombination saveKey;

	public MenuItem nProject; // New Project
	public MenuItem oProject; // Open Project
	public MenuItem export; //
	public MenuItem importD;
	public MenuItem nFile; // New File
	public MenuItem oFile;// Open File
	public MenuItem save; // Save
	public MenuItem exit; // Exit;

	public MenuItem copy; // Copy
	public MenuItem paste;// Paste
	public MenuItem delete; // Delete

	public MenuItem clean;
	public MenuItem gridLine;

	public MenuBar bar;
	public Menu file;
	public Menu edit;
	public Menu project;

	public HBox sbar;
	public Icon saveB;
	public Icon printB;
	public Icon pointB;
	public Icon cpointB;
	public ColorPicker cpikcer;
	public Icon deleteB;
	public Icon selectB;

	public Button gHLineB; // Horizontal
	public Button gVLineB; // Vertical
	public Icon rSelectB;// Region Select;

	public Icon gBLineB; // Background
	public boolean isgBLine;
	public BorderPane gridPane;

	// System
	public Fonts Fonts;
	public boolean isRegionDraw;
	public FileChooser fileChoose;
	public BoxBlur bbox;
	public Home home;
	// UseCase

	public boolean isUCRelation;
	public boolean isUCGeneral;
	public boolean isUCInclude;
	public boolean isUCExtend;

	public boolean isActivation;
	public boolean isNActivation;
	public boolean isDActivation;

	public boolean isCDepend;

	public MenusLib() {
		pasteKey = new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_ANY);
		saveKey = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_ANY);

		bbox = new BoxBlur();
		home = new Home();

		fileChoose = new FileChooser();
		fileChoose.setTitle("Import UML Package");

		bar = new MenuBar();
		file = new Menu("File");
		edit = new Menu("Edit");
		project = new Menu("Project");

		nProject = new MenuItem("New Project");
		nFile = new MenuItem("New Diagram");
		oProject = new MenuItem("Open..");
		export = new MenuItem("Export..");
		importD = new MenuItem("Import..");
		save = new MenuItem("Save");
		exit = new MenuItem("Exit");
		file.getItems().addAll(nProject, nFile, oProject, export, importD, save, exit);

		copy = new MenuItem("Copy");
		paste = new MenuItem("Paste");
		delete = new MenuItem("Delete");
		edit.getItems().addAll(copy, paste, delete);

		clean = new MenuItem("Clean");
		gridLine = new MenuItem("GridLine");
		project.getItems().addAll(clean, gridLine);

		bar.getMenus().addAll(file, edit, project);

		// Tool
		sbar = new HBox();
		sbar.setAlignment(Pos.BASELINE_RIGHT);
		sbar.setSpacing(10);
		sbar.setStyle("-fx-padding:5 5 5 5;-fx-background-color:#F5F5F5;");
		pointB = new Icon("Images/Icons/cursor.png");
		cpointB = new Icon("Images/Icons/color.png");
		cpikcer = new ColorPicker();
		cpikcer.setPrefSize(60, 25);
		cpikcer.setStyle("-fx-padding:5 5 5 5");
		deleteB = new Icon("Images/Icons/delete.png");
		selectB = new Icon("Images/Icons/cursor.png");

		saveB = new Icon("Images/Icons/save.png");
		printB = new Icon("Images/Icons/print.png");
		// gHLineB = new Button("GuideLine (Horizontal)");
		// gVLineB = new Button("GuideLine (Vertical)");
		gBLineB = new Icon("Images/Icons/gridline.png");
		rSelectB = new Icon("Images/Icons/region.png");
		sbar.getChildren().addAll(pointB, rSelectB, deleteB, saveB, printB, gBLineB, cpointB, cpikcer);

		Fonts = new Fonts();

		isgBLine = true;
		gridPane = new BorderPane();
		setGridLine();
	}

	public void setGridLine() {
		int i = 10;
		while (i < 800) {
			GridLines l = new GridLines(10, i, 1340, i);
			gridPane.getChildren().add(l);
			i = i + 20;
		}
		int k = 10;
		while (k < 1340) {
			GridLines l = new GridLines(k, 10, k, 690);
			gridPane.getChildren().add(l);
			k = k + 20;
		}

	}
}
