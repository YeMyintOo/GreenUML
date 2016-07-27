package GUI;

import Boxs.BNewDiagram;
import Boxs.BNewProject;
import Components.Sample1;
import Hardware.Screen;
import Libraries.MenusLib;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
	Scene scene;
	Screen screen;
	BorderPane container;
	VBox head;
	MenusLib menu;
	TabPane tabPane;
	Draw draw;

	@Override
	public void start(Stage stage) throws Exception {
		initState();

		scene = new Scene(container, screen.getWidth(), screen.getHeight());
		stage.setScene(scene);
		// stage.setFullScreen(true);
		stage.setTitle("GreenUML");
		stage.centerOnScreen();
		stage.show();

		menu.cpointB.setOnAction(e -> {
			System.out.println("Selected Color " + menu.cpikcer.getValue().toString());
			scene.setCursor(Cursor.HAND);
		});
		menu.gHLineB.setOnAction(e -> {
			System.out.println("Tabs " + tabPane.getTabs());
			tabPane.getSelectionModel().getSelectedItem().getContent().setStyle("-fx-background-color:blue;");

		});
		tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
			@Override
			public void changed(ObservableValue<? extends Tab> arg0, Tab arg1, Tab arg2) {
				System.out.println("Selected Tabs Index : " + tabPane.getSelectionModel().getSelectedIndex());
				Draw draw = (Draw) tabPane.getSelectionModel().getSelectedItem().getContent();

				draw.getArea().addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						Object obj = e.getTarget();
						if (obj instanceof Sample1 && scene.getCursor() == Cursor.HAND) {
							Sample1 sample = (Sample1) obj;
							Color color = Color.web(menu.cpikcer.getValue().toString());
							sample.setFill(color);

						} else if (obj instanceof Sample1) {

						} else if (draw.getCTool().equals("1")) {
							Sample1 sample = new Sample1(e.getX(), e.getY());
							draw.getArea().getChildren().addAll(sample);
						}
						scene.setCursor(Cursor.DEFAULT);
					}
				});

				draw.getArea().addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						Object obj = e.getTarget();
						if (obj instanceof Sample1) {
							Sample1 sample = (Sample1) obj;
							sample.setX(e.getX());
							sample.setY(e.getY());
						}
					}
				});
			}
		});

		// Menus Function////////////
		menu.nFile.setOnAction(e -> {
			BNewDiagram box = new BNewDiagram(stage);
			box.sizeToScene();
			container.setDisable(true);
			box.setAlwaysOnTop(true);
			box.showAndWait();
			if (box.getValue().equals("finish")) {
				container.setCenter(tabPane);
				addNewTab(box.getFileName(), box.getType(), box.getPath());
			}
			container.setDisable(false);
		});
		menu.nProject.setOnAction(e -> {
			BNewProject box = new BNewProject(stage);
			box.sizeToScene();
			container.setDisable(true);
			box.setAlwaysOnTop(true);
			box.showAndWait();
			if (box.getValue().equals("finish")) {
				//container.setCenter(tabPane);
				//addNewTab(box.getFileName(), box.getType(), box.getPath());
			}
			container.setDisable(false);
		});

		/////////////////////////////.
		
	}

	public void initState() {
		container = new BorderPane();
		screen = new Screen();
		head = new VBox();
		menu = new MenusLib();
		head.getChildren().addAll(menu.bar, menu.sbar);
		tabPane = new TabPane();
		tabPane.setMinWidth(screen.getWidth());
		container.setTop(head);
		ScrollPane sp = new ScrollPane();
		sp.setPrefWidth(screen.getWidth() - 20);
		sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		sp.setContent(tabPane);
		container.setCenter(sp);
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	public void addNewTab(String name, int diagram, String path) {
		Tab tab = new Tab();
		draw = new Draw(scene,diagram);
		tab.setContent(draw);
		tab.setText(name);
		tabPane.getTabs().add(tab);
		tabPane.setMaxWidth(screen.getWidth() - 500);
	}

}
