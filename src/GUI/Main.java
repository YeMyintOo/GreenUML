package GUI;

import com.sun.prism.paint.Color;

import Hardware.Screen;
import Libraries.MenusLib;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
	Scene scene;
	Screen screen;
	BorderPane container;
	VBox head;
	MenusLib menu;
	TabPane tabPane;

	@Override
	public void start(Stage stage) throws Exception {
		initState();

		scene = new Scene(container, screen.getWidth(), screen.getHeight());
		stage.setScene(scene);
		// stage.setFullScreen(true);
		stage.setTitle("GreenUML");
		stage.centerOnScreen();
		stage.show();

		menu.handB.setOnAction(e -> {
			addNewTab();
		});
		menu.cpointB.setOnAction(e -> {
			System.out.println("Selected Color " + menu.cpikcer.getValue().toString());
			scene.setCursor(Cursor.HAND);
		});
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
		sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		sp.setContent(tabPane);
		container.setCenter(sp);
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	public void addNewTab() {
		Tab tab = new Tab();
		BorderPane ws = new BorderPane();
		ws.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				scene.setCursor(Cursor.DEFAULT);
			}
		});
		
		ws.maxWidth(screen.getWidth());
		ws.setMinHeight(800);
		ws.setStyle("-fx-background-color:white;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: blue;");
		tab.setContent(ws);
		tabPane.getTabs().add(tab);
	}

}
