package Boxs;

import java.io.File;
import java.io.IOException;

import Libraries.Pack;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BExport extends Stage {

	BorderPane root;
	public String condition;

	BorderPane project;
	Label porjL;
	ListView<String> list;
	private String[] projects;

	private Button okB;
	private Button closeB;
	private Pack pack;

	public BExport(Stage owner) {
		super();
		initModality(Modality.WINDOW_MODAL); // Prevent click parent stage
		initOwner(owner);
		setResizable(false);
		setTitle("Export...");

		root = new BorderPane();
		condition = "default";

		project = new BorderPane();
		porjL = new Label("Project");
		porjL.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		porjL.setStyle("-fx-padding: 0 0 10 0;");

		list = new ListView<String>();
		loadProjects();
		project.setTop(porjL);
		project.setCenter(list);
		project.setStyle("-fx-padding: 20 20 20 20;");

		HBox btn = new HBox();
		okB = new Button("Export");
		closeB = new Button("Close");
		btn.getChildren().addAll(closeB, okB);
		btn.setSpacing(4);
		btn.setStyle("-fx-padding:10 20 10 10;" + "-fx-background-color:rgb(220,220,220);" + "-fx-cursor: hand;");
		btn.setAlignment(Pos.BASELINE_RIGHT);

		root.setCenter(project);
		root.setBottom(btn);

		Scene scene = new Scene(root, 300, 400, Color.WHITE);
		setScene(scene);

		Alert alert = new Alert(AlertType.INFORMATION);

		okB.setOnAction(e -> {
			DirectoryChooser choose = new DirectoryChooser();
			choose.setTitle("Export To...");
			File dir = choose.showDialog(this);
			String project = list.getSelectionModel().getSelectedItem().toString();
			pack = new Pack();
			try {
				pack.doPack(new File("Diagrams/" + project).toPath(),
						new File(dir.getPath() + "/" + project + ".uml").toPath());
				close();
				alert.setTitle("Success");
				alert.setHeaderText("Export Success");
				alert.show();				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
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
}
