package Boxs;

import java.io.File;
import java.util.ArrayList;

import Libraries.FileCheckBox;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BOpen extends Stage {

	BorderPane root;
	public String condition;
	private String[] projects;

	BorderPane info;

	BorderPane project;
	Label porjL;
	ListView<String> list;

	VBox tool;
	Button addB;
	Button removeB;

	BorderPane file;
	Label fileL;
	ListView<CheckBox> flist;
	public ArrayList<String> files; // Real
	public String projectName;

	private Button okB;
	private Button closeB;
	private Button resetB;

	public BOpen(Stage owner) {
		super();
		initModality(Modality.WINDOW_MODAL); // Prevent click parent stage
		initOwner(owner);
		setResizable(false);
		setTitle("Open...");

		root = new BorderPane();
		condition = "default";
		files = new ArrayList<String>();

		info = new BorderPane();

		project = new BorderPane();
		porjL = new Label("Project");
		porjL.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		porjL.setStyle("-fx-padding: 0 0 10 0;");

		list = new ListView<String>();
		loadProjects();
		project.setTop(porjL);
		project.setCenter(list);
		project.setStyle("-fx-padding: 20 20 20 20;");

		tool = new VBox();
		tool.setSpacing(15);
		tool.setStyle("-fx-padding: 100 0 0 0;");
		addB = new Button(">");
		removeB = new Button("<");
		tool.getChildren().addAll(addB, removeB);

		file = new BorderPane();
		fileL = new Label("Files");
		fileL.setStyle("-fx-padding: 0 0 10 0;");
		fileL.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		flist = new ListView<CheckBox>();

		file.setTop(fileL);
		file.setCenter(flist);
		file.setStyle("-fx-padding: 20 20 20 20;");

		info.setLeft(project);
		info.setCenter(tool);
		info.setRight(file);

		// Button Panel
		HBox btn = new HBox();
		okB = new Button("Open");
		closeB = new Button("Close");
		resetB = new Button("Reset");
		btn.getChildren().addAll(resetB, closeB, okB);
		btn.setSpacing(4);
		btn.setStyle("-fx-padding:10 10 10 10;" + "-fx-background-color:rgb(220,220,220);" + "-fx-cursor: hand;");
		btn.setAlignment(Pos.BASELINE_RIGHT);

		// pane.setCenter(infoP);
		root.setCenter(info);
		root.setBottom(btn);

		Scene scene = new Scene(root, 600, 400, Color.WHITE);
		setScene(scene);

		addB.setOnAction(e -> {
			projectName = list.getSelectionModel().getSelectedItem().toString();
			loadFiles(list.getSelectionModel().getSelectedItem().toString());
		});

		okB.setOnAction(e -> {
			
			condition = "open";
			close();
		});

		closeB.setOnAction(e -> {
			close();
		});

	}

	public void loadProjects() {
		File path = new File("Diagrams");
		File[] folders = path.listFiles(File::isDirectory);
		projects = new String[folders.length];
		for (int i = 0; i < folders.length; i++) {
			projects[i] = folders[i].getName();
		}
		list.getItems().addAll(projects);
	}

	public void loadFiles(String project) {
		flist.getItems().clear();
		File path = new File("Diagrams/" + project);
		File[] dfiles = path.listFiles();
		for (int i = 0; i < dfiles.length; i++) {
			String name = dfiles[i].getName().substring(0, dfiles[i].getName().indexOf("."));
			FileCheckBox cb = new FileCheckBox(name);
			int index = i;
			cb.setOnAction(e -> {
				if (cb.isSelected()) {
					files.add(dfiles[index].getName());
				} else {
					files.remove(dfiles[index].getName());
				}

			});
			flist.getItems().add(cb);
		}

	}
}
