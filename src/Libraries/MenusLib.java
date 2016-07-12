package Libraries;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;

public class MenusLib {
	public MenuItem nProject; // New Project
	public MenuItem oProject; // Open Project
	public MenuItem cWorkSpace; // WorkSpace
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
	public Button handB;
	public Button gHLineB; // Horizontal
	public Button gVLineB; // Vertical
	public Button gBLineB; // Background;
	public Button rSelectB;// Region Select;

	public MenusLib() {
		bar = new MenuBar();
		file = new Menu("File");
		edit = new Menu("Edit");

		nProject = new MenuItem("New Project");
		oProject = new MenuItem("Open Project");
		cWorkSpace = new MenuItem("WorkSpace");
		nFile = new MenuItem("New Diagram");
		oFile = new MenuItem("Open Diagram");
		save = new MenuItem("Save");
		exit = new MenuItem("Exit");
		file.getItems().addAll(nProject, oProject, cWorkSpace, nFile, oFile, save, exit);

		copy = new MenuItem("Copy");
		paste = new MenuItem("Paste");
		cut = new MenuItem("Cut");
		select = new MenuItem("Select");
		selectAll = new MenuItem("Select All");
		delete = new MenuItem("Delete");
		edit.getItems().addAll(copy, paste, cut, select, selectAll, delete);

		bar.getMenus().addAll(file, edit);

		// Tool
		sbar = new HBox();
		sbar.setAlignment(Pos.BASELINE_RIGHT);
		sbar.setSpacing(10);
		pointB = new Button("Pointer");
		cpointB = new Button("Color Pointer");
		handB = new Button("Hand");
		saveB = new Button("Save");
		printB = new Button("Print");
		gHLineB = new Button("GuideLine (Horizontal)");
		gVLineB = new Button("GuideLine (Vertical)");
		gBLineB = new Button("GridLine");
		rSelectB = new Button("Region Select");
		sbar.getChildren().addAll(pointB, handB, saveB, printB, gHLineB, gVLineB, gBLineB, rSelectB);
	}
}
