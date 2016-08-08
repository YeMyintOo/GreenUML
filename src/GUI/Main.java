package GUI;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import Boxs.BNewDiagram;
import Boxs.BNewProject;
import GTool.GLabel;
import Hardware.Screen;
import Libraries.MenusLib;
import Libraries.OS;
import Libraries.Tool;
import UseCase.UCActor;
import UseCase.UCBoundary;
import UseCase.UCExtend;
import UseCase.UCGeneral;
import UseCase.UCInclude;
import UseCase.UCProcess;
import UseCase.UCRelation;
import XMLFactory.CopyXML;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyEvent;
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
	CopyXML copyxml;

	String osType;
	File folder; // Temp Diagrams Folder

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
			scene.setCursor(Cursor.HAND);
		});
		menu.deleteB.setOnAction(e -> {
			scene.setCursor(Cursor.CROSSHAIR);
		});
		menu.selectB.setOnAction(e -> {
			scene.setCursor(Cursor.OPEN_HAND);
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

		stage.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				if (menu.pasteKey.match(key)) {
					menu.dbFactory = DocumentBuilderFactory.newInstance();
					try {
						menu.dBuilder = menu.dbFactory.newDocumentBuilder();
						menu.doc = menu.dBuilder.parse("Temp/Copy.xml");
						switch (menu.doc.getElementsByTagName("diagram").item(0).getTextContent()) {
						case "UCProcess":
							double x = Double
									.parseDouble(menu.doc.getElementsByTagName("centerx").item(0).getTextContent());
							double y = Double
									.parseDouble(menu.doc.getElementsByTagName("centery").item(0).getTextContent());
							double xr = Double
									.parseDouble(menu.doc.getElementsByTagName("xradius").item(0).getTextContent());
							double yr = Double
									.parseDouble(menu.doc.getElementsByTagName("yradius").item(0).getTextContent());
							String label = menu.doc.getElementsByTagName("label").item(0).getTextContent();
							String color = menu.doc.getElementsByTagName("color").item(0).getTextContent();

							UCProcess process = new UCProcess(stage, x + 10, y + 10, Color.web(color));
							process.data.set(label);
							draw.getArea().getChildren().addAll(process, process.getLabel(), process.getText(false));

							break;
						}
					} catch (Exception e) {

					}
				}
			}
		});

		tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
			@Override
			public void changed(ObservableValue<? extends Tab> arg0, Tab arg1, Tab arg2) {
				if (tabPane.getTabs().size() > 0) {

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
									draw.getArea().getChildren().addAll(label, label.getText(false),
											label.getTool(false));
								} else if (draw.getCTool() == Tool.UCPROCESS) {
									UCProcess process = new UCProcess(stage, e.getX(), e.getY(), color);
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

							// Color Case
							if (scene.getCursor() == Cursor.HAND) {
								if (obj instanceof UCProcess) {
									UCProcess ucprocess = (UCProcess) obj;
									ucprocess.setFill(color);
								} else if (obj instanceof UCActor) {
									UCActor actor = (UCActor) obj;
									actor.setFill(color);
								} else if (obj instanceof UCBoundary) {
									UCBoundary boundary = (UCBoundary) obj;
									boundary.setFill(color);
								}
							} else

							// Delete Case
							if (scene.getCursor() == Cursor.CROSSHAIR) {
								if (obj instanceof GLabel) {
									GLabel label = (GLabel) obj;
									draw.getArea().getChildren().removeAll(label, label.getText(false),
											label.getTool(false));
								} else if (obj instanceof UCProcess) {
									UCProcess ucprocess = (UCProcess) obj;
									draw.getArea().getChildren().removeAll(ucprocess, ucprocess.getLabel(),
											ucprocess.getText(false));
								} else if (obj instanceof UCActor) {
									UCActor actor = (UCActor) obj;
									draw.getArea().getChildren().removeAll(actor, actor.getBody(), actor.getLeg(),
											actor.getLeg2(), actor.getLeg3(), actor.getLeg4(), actor.getLabel(),
											actor.getText(false));
								} else if (obj instanceof UCBoundary) {
									UCBoundary boundary = (UCBoundary) obj;
									draw.getArea().getChildren().removeAll(boundary);
								}
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
								draw.getArea().getChildren().addAll(ucextend.getSnode(), ucextend.getEnode(),
										ucextend.top, ucextend.label);

								menu.isUCExtend = false;
								ucextend = null;
							}
						}
					});
				}
			}
		});

		// Menus Function////////////
		menu.nFile.setOnAction(e -> {
			BNewDiagram box = new BNewDiagram(stage);
			box.sizeToScene();
			container.setDisable(true);
			box.setAlwaysOnTop(true);
			box.showAndWait();
			if (box.condition.equals("finish")) {
				container.setCenter(tabPane);
				try {
					addNewTab(box.getFileName(), box.folder, box.diagram);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
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
				// Create Project Folder
				File file = null;
				switch (osType) {
				case "Unix":
					file = new File("Diagrams/" + box.nameF.getText().toString().trim());
					if (!file.exists()) {
						file.mkdir();
						System.out.println("Folder @" + box.nameF.getText().toString() + " is created!");
					} else {
						System.out.println("Folder already exists.");
					}
					break;
				case "Windows":
					// Not Support!
					break;
				}
			}
			container.setDisable(false);
		});

		///////////////////////////// .

	}

	public void initState() throws IOException {
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
		OS os = new OS();
		if (os.isWindows()) {
			osType = "Windows";
		} else if (os.isUnix()) {
			osType = "Unix";
		}
		System.out.println("OS Type :" + osType);

		folder = new File("Diagrams");
		if (!folder.exists()) {
			folder.mkdir();
			System.out.println("Diagram Folder is created");
		} else {
			System.out.println("Diagram Folder is already exists");
		}

		System.out.println("Copy.xml File Loading...");
		copyxml = new CopyXML();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	public void addNewTab(String name, String folder, int diagram) throws IOException {
		Tab tab = new Tab();
		// Select Folder
		File project = new File("Diagrams/" + folder);
		if (project.exists() && project.isDirectory()) {
			System.out.println("Folder @" + folder + " is exists and Directory");
			// Create File For Diagram
			File file = null;
			switch (osType) {
			case "Unix":
				file = new File(project.getPath() + "/" + name + ".xml");
				if (!file.exists()) {
					if (file.createNewFile()) {
						System.out.println("File @" + name + " is created!");
						draw = new Draw(scene, diagram);
						tab.setContent(draw);
						tab.setText(name);
						tabPane.getTabs().add(tab);

					}
				} else {
					System.out.println("File already exists.");
				}
				break;
			case "Windows":
				// Not Support!
				break;
			}
		} else {
			System.out.println("Folder @" + folder + " is missing.");
		}

	}
}
