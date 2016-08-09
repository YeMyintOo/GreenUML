package Libraries;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class MenusLib {

	public DocumentBuilderFactory dbFactory;
	public DocumentBuilder dBuilder;
	public Document doc;
	public final KeyCombination pasteKey;

	public MenuItem nProject; // New Project
	public MenuItem oProject; // Open Project
	public MenuItem export; //
	public MenuItem nFile; // New File
	public MenuItem oFile;// Open File
	public MenuItem save; // Save
	public MenuItem exit; // Exit;

	public MenuItem copy; // Copy
	public MenuItem paste;// Paste
	public MenuItem cut; // Cut
	public MenuItem select; // Select
	public MenuItem selectAll; // Select All
	public MenuItem delete; // Delete

	public MenuBar bar;
	public Menu file;
	public Menu edit;

	public HBox sbar;
	public Button saveB;
	public Button printB;
	public Button pointB;
	public Button cpointB;
	public ColorPicker cpikcer;
	public Button deleteB;
	public Button selectB;

	public Button gHLineB; // Horizontal
	public Button gVLineB; // Vertical
	public Button rSelectB;// Region Select;

	public Button gBLineB; // Background
	public boolean isgBLine;
	public BorderPane gridPane;

	// System
	public Fonts Fonts;

	// UseCase
	
	public boolean isUCRelation;
	public boolean isUCGeneral;
	public boolean isUCInclude;
	public boolean isUCExtend;

	public MenusLib() {
		pasteKey = new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_ANY);

		bar = new MenuBar();
		file = new Menu("File");
		edit = new Menu("Edit");

		nProject = new MenuItem("New Project");
		nFile = new MenuItem("New Diagram");
		oProject = new MenuItem("Open..");
		export = new MenuItem("Export..");
		save = new MenuItem("Save");
		exit = new MenuItem("Exit");
		file.getItems().addAll(nProject, nFile, oProject, export, save, exit);

		copy = new MenuItem("Copy");
		paste = new MenuItem("Paste");
		// cut = new MenuItem("Cut");
		// select = new MenuItem("Select");
		// selectAll = new MenuItem("Select All");
		delete = new MenuItem("Delete");
		edit.getItems().addAll(copy, paste, delete);

		bar.getMenus().addAll(file, edit);

		// Tool
		sbar = new HBox();
		sbar.setAlignment(Pos.BASELINE_RIGHT);
		sbar.setSpacing(10);
		pointB = new Button("Pointer");
		cpointB = new Button("Color Pointer");
		cpikcer = new ColorPicker();
		deleteB = new Button("Delete");
		selectB = new Button("Select");
		saveB = new Button("Save");
		printB = new Button("Print");
		gHLineB = new Button("GuideLine (Horizontal)");
		gVLineB = new Button("GuideLine (Vertical)");
		gBLineB = new Button("GridLine");
		rSelectB = new Button("Region Select");
		sbar.getChildren().addAll(pointB, cpointB, cpikcer, selectB, deleteB, saveB, printB, gHLineB, gVLineB, gBLineB,
				rSelectB);

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
