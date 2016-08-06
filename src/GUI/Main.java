package GUI;

import Boxs.BNewDiagram;
import Boxs.BNewProject;
import GTool.GLabel;
import Hardware.Screen;
import Libraries.MenusLib;
import Libraries.Tool;
import UseCase.UCActor;
import UseCase.UCBoundary;
import UseCase.UCExtend;
import UseCase.UCGeneral;
import UseCase.UCInclude;
import UseCase.UCProcess;
import UseCase.UCRelation;
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
import javafx.scene.layout.Pane;
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

	// UseCase
	UCRelation ucrelation;
	UCGeneral ucgeneral;
	UCInclude ucinclude;
	UCExtend ucextend;

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
			tabPane.getSelectionModel().getSelectedItem().getContent().setStyle("-fx-background-color:blue;");
		});
		menu.gBLineB.setOnAction(e -> {
			System.out.println(" Call GridLine ");
			Draw draw = (Draw) tabPane.getSelectionModel().getSelectedItem().getContent();
			if (menu.isgBLine) {
				draw.getArea().getChildren().add(menu.gridPane);
				menu.gridPane.toBack();
				menu.isgBLine = false;
			} else {
				menu.isgBLine = true;
				draw.getArea().getChildren().remove(menu.gridPane);
			}
		});

		tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
			@Override
			public void changed(ObservableValue<? extends Tab> arg0, Tab arg1, Tab arg2) {
				Draw draw = (Draw) tabPane.getSelectionModel().getSelectedItem().getContent();

				draw.getArea().addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						Object obj = e.getTarget();
						Color color = Color.web(menu.cpikcer.getValue().toString());
						// New Draw
						if (obj instanceof Pane || (obj instanceof UCBoundary && draw.getCTool() != Tool.POINTER
								&& draw.getCTool() != Tool.UCBOUNDARY)) {
							if (draw.getCTool() == Tool.GLabel) {
								GLabel label = new GLabel(e.getX(), e.getY(), menu.Fonts.fonts);
								draw.getArea().getChildren().addAll(label, label.getText(false), label.getTool(false));
							} else if (draw.getCTool() == Tool.UCPROCESS) {
								UCProcess process = new UCProcess(e.getX(), e.getY(), color);
								draw.getArea().getChildren().addAll(process, process.getLabel(),
										process.getText(false));
							} else if (draw.getCTool() == Tool.UCACTOR) {
								UCActor actor = new UCActor(e.getX(), e.getY(), color);
								draw.getArea().getChildren().addAll(actor, actor.getBody(), actor.getLeg(),
										actor.getLeg2(), actor.getLeg3(), actor.getLeg4(), actor.getLabel(),
										actor.getText(false));
							} else if (draw.getCTool() == Tool.UCREALATION) {
								ucrelation = new UCRelation(e.getX(), e.getY(), e.getX(), e.getY());
								draw.getArea().getChildren().addAll(ucrelation);
								menu.isUCRelation = true;
							} else if (draw.getCTool() == Tool.UCGENERAL) {
								ucgeneral = new UCGeneral(e.getX(), e.getY(), e.getX(), e.getY(), color);
								draw.getArea().getChildren().addAll(ucgeneral);
								menu.isUCGeneral = true;
							} else if (draw.getCTool() == Tool.UCBOUNDARY) {
								UCBoundary ucboundary = new UCBoundary(e.getX(), e.getY(), color);
								draw.getArea().getChildren().addAll(ucboundary);
								ucboundary.toBack();
							} else if (draw.getCTool() == Tool.UCINCLUDE) {
								ucinclude = new UCInclude(e.getX(), e.getY(), e.getX(), e.getY(), color);
								draw.getArea().getChildren().addAll(ucinclude);
								menu.isUCInclude = true;
							} else if (draw.getCTool() == Tool.UCEXTEND) {
								ucextend = new UCExtend(e.getX(), e.getY(), e.getX(), e.getY(), color);
								draw.getArea().getChildren().addAll(ucextend);
								menu.isUCExtend = true;
							}
							draw.setCTool(Tool.POINTER); // Null
						}

						// Use Case Color Case
						if (obj instanceof UCProcess && scene.getCursor() == Cursor.HAND) {
							UCProcess ucprocess = (UCProcess) obj;
							ucprocess.setFill(color);
						} else if (obj instanceof UCActor && scene.getCursor() == Cursor.HAND) {
							UCActor actor = (UCActor) obj;
							actor.setFill(color);
						} else if (obj instanceof UCBoundary && scene.getCursor() == Cursor.HAND) {
							UCBoundary boundary = (UCBoundary) obj;
							boundary.setFill(color);
						}

						scene.setCursor(Cursor.DEFAULT);
					}
				});

				draw.getArea().addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						scene.setCursor(Cursor.MOVE);
						if (menu.isUCRelation) {
							ucrelation.setEndX(e.getX());
							ucrelation.setEndY(e.getY());
						} else if (menu.isUCGeneral) {
							ucgeneral.setEndX(e.getX());
							ucgeneral.setEndY(e.getY());
						} else if (menu.isUCInclude) {
							ucinclude.setEndX(e.getX());
							ucinclude.setEndY(e.getY());
						} else if (menu.isUCExtend) {
							ucextend.setEndX(e.getX());
							ucextend.setEndY(e.getY());
						}

					}
				});

				draw.getArea().addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						scene.setCursor(Cursor.DEFAULT);
						if (menu.isUCRelation) {
							ucrelation.setEndX(e.getX());
							ucrelation.setEndY(e.getY());
							draw.getArea().getChildren().addAll(ucrelation.getSnode(), ucrelation.getEnode());
							menu.isUCRelation = false;
							ucrelation = null;
						} else if (menu.isUCGeneral) {
							ucgeneral.setEndX(e.getX());
							ucgeneral.setEndY(e.getY());
							ucgeneral.calculateTri();
							draw.getArea().getChildren().addAll(ucgeneral.getSnode(), ucgeneral.getEnode(),
									ucgeneral.getTri());
							menu.isUCGeneral = false;
							ucgeneral = null;
						} else if (menu.isUCInclude) {
							ucinclude.setEndX(e.getX());
							ucinclude.setEndY(e.getY());
							ucinclude.update();
							draw.getArea().getChildren().addAll(ucinclude.getSnode(), ucinclude.getEnode(),
									ucinclude.top, ucinclude.label);

							menu.isUCInclude = false;
							ucinclude = null;
						} else if (menu.isUCExtend) {
							ucextend.setEndX(e.getX());
							ucextend.setEndY(e.getY());
							ucextend.update();
							draw.getArea().getChildren().addAll(ucextend.getSnode(), ucextend.getEnode(), ucextend.top,
									ucextend.label);

							menu.isUCExtend = false;
							ucextend = null;
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
				// container.setCenter(tabPane);
				// addNewTab(box.getFileName(), box.getType(), box.getPath());
			}
			container.setDisable(false);
		});

		///////////////////////////// .

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
		draw = new Draw(scene, diagram);
		tab.setContent(draw);
		tab.setText(name);
		tabPane.getTabs().add(tab);
		tabPane.setMaxWidth(screen.getWidth() - 500);
	}

}
