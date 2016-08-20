package GUI;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilderFactory;

import Boxs.BExport;
import Boxs.BNewDiagram;
import Boxs.BNewProject;
import Boxs.BOpen;
import Boxs.BPrintPreview;
import ClassD.ClassAbstract;
import ClassD.ClassD;
import ClassD.ClassDataBox;
import ClassD.ClassDepen;
import ClassD.ClassFunBox;
import ClassD.ClassInterface;
import ClassD.EditClassDataBox;
import GTool.GLabel;
import Hardware.Screen;
import Libraries.MenusLib;
import Libraries.OS;
import Libraries.Pack;
import Libraries.Region;
import Libraries.Tool;
import Sequence.SEActivation;
import Sequence.SEDActivation;
import Sequence.SENActivation;
import Sequence.SERole;
import UseCase.UCActor;
import UseCase.UCBoundary;
import UseCase.UCExtend;
import UseCase.UCGeneral;
import UseCase.UCInclude;
import UseCase.UCProcess;
import UseCase.UCRelation;
import XMLFactory.CopyXML;
import XMLFactory.UCXml;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
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
	File iFile;

	String osType;
	File folder; // Temp Diagrams Folder
	File tempPIC;
	Region region;
	// UseCase

	UCRelation ucrelation;
	UCGeneral ucgeneral;
	UCInclude ucinclude;
	UCExtend ucextend;

	SEActivation seactivation;
	SENActivation senactivation;
	SEDActivation sedactivation;

	ClassDepen cdepen;
	Stage stage;

	@Override
	public void start(Stage stage) throws Exception {
		initState();
		this.stage = stage;
		scene = new Scene(container, screen.getWidth(), screen.getHeight());
		stage.setScene(scene);
		// stage.setFullScreen(true);
		stage.setTitle("GreenUML");
		stage.centerOnScreen();
		stage.show();

		menu.rSelectB.setOnAction(e -> {
			scene.setCursor(Cursor.SE_RESIZE);
		});
		menu.clean.setOnAction(e -> {
			clean();
		});
		menu.gridLine.setOnAction(e -> {
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

		menu.cpointB.setOnAction(e -> {
			scene.setCursor(Cursor.HAND);
		});
		menu.deleteB.setOnAction(e -> {
			scene.setCursor(Cursor.CROSSHAIR);
		});
		menu.selectB.setOnAction(e -> {
			scene.setCursor(Cursor.OPEN_HAND);
		});
		menu.gBLineB.setOnAction(e -> {
			menu.gridLine.fire();
		});

		stage.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				if (menu.pasteKey.match(key)) {
					System.out.println("#Paste");
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
							String text = menu.doc.getElementsByTagName("label").item(0).getTextContent();
							String color = menu.doc.getElementsByTagName("color").item(0).getTextContent();

							UCProcess process = new UCProcess(stage, x + 10, y + 10, Color.web(color));
							process.setRadiusX(xr);
							process.setRadiusY(yr);
							process.data.set(text);
							process.label.layoutXProperty().bind(process.centerXProperty()
									.subtract(process.label.layoutBoundsProperty().getValue().getWidth() / 2));
							Draw draw = (Draw) tabPane.getSelectionModel().getSelectedItem().getContent();
							draw.getArea().getChildren().addAll(process, process.getLabel(), process.getText(false));
							draw.objects.add(process);
							break;
						}
					} catch (Exception e) {

					}
				} else if (menu.saveKey.match(key)) {
					menu.save.fire();
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
							if (obj instanceof Pane && scene.getCursor() == Cursor.SE_RESIZE) {
								System.out.println("Region Draw");
								region = new Region();
								region.setX(e.getX());
								region.setY(e.getY());
								draw.getArea().getChildren().addAll(region);
								menu.isRegionDraw = true;
							} else

							if (obj instanceof Pane || (obj instanceof UCBoundary && draw.getCTool() != Tool.POINTER
									&& draw.getCTool() != Tool.UCBOUNDARY)) {
								switch (draw.getCTool()) {
								case GLabel:
									GLabel label = new GLabel(e.getX(), e.getY(), menu.Fonts.fonts);
									draw.getArea().getChildren().addAll(label, label.getText(false),
											label.getTool(false));
									break;
								case UCPROCESS:
									UCProcess process = new UCProcess(stage, e.getX(), e.getY(), color);
									draw.getArea().getChildren().addAll(process, process.getLabel(),
											process.getText(false));
									draw.objects.add(process);
									break;
								case UCACTOR:
									UCActor actor = new UCActor(e.getX(), e.getY(), color);
									draw.getArea().getChildren().addAll(actor, actor.getBody(), actor.getLeg(),
											actor.getLeg2(), actor.getLeg3(), actor.getLeg4(), actor.getLabel(),
											actor.getText(false));
									break;
								case UCREALATION:
									ucrelation = new UCRelation(e.getX(), e.getY(), e.getX(), e.getY());
									draw.getArea().getChildren().addAll(ucrelation);
									menu.isUCRelation = true;
									break;
								case UCGENERAL:
									ucgeneral = new UCGeneral(e.getX(), e.getY(), e.getX(), e.getY(), color);
									draw.getArea().getChildren().addAll(ucgeneral);
									menu.isUCGeneral = true;
									break;
								case UCBOUNDARY:
									UCBoundary ucboundary = new UCBoundary(e.getX(), e.getY(), color);
									draw.getArea().getChildren().addAll(ucboundary);
									ucboundary.toBack();
									break;
								case UCINCLUDE:
									ucinclude = new UCInclude(e.getX(), e.getY(), e.getX(), e.getY(), color);
									draw.getArea().getChildren().addAll(ucinclude);
									menu.isUCInclude = true;
									break;
								case UCEXTEND:
									ucextend = new UCExtend(e.getX(), e.getY(), e.getX(), e.getY(), color);
									draw.getArea().getChildren().addAll(ucextend);
									menu.isUCExtend = true;
									break;
								case SEROLE:
									SERole role = new SERole(e.getX(), e.getY(), color);
									draw.getArea().getChildren().addAll(role, role.label, role.line,
											role.getText(false));
									role.line.toBack();
									break;
								case SEACTIVATION:
									seactivation = new SEActivation(e.getX(), e.getY(), e.getX(), e.getY(), color);
									draw.getArea().getChildren().addAll(seactivation);
									menu.isActivation = true;
									break;
								case SENEWACTIVATION:
									senactivation = new SENActivation(e.getX(), e.getY(), e.getX(), e.getY(), color);
									draw.getArea().getChildren().addAll(senactivation);
									menu.isNActivation = true;
									break;
								case SEDESTROYACTIVATION:
									sedactivation = new SEDActivation(e.getX(), e.getY(), e.getX(), e.getY());
									draw.getArea().getChildren().addAll(sedactivation);
									menu.isDActivation = true;
									break;
								case CLASS:
									ClassD classd = new ClassD(e.getX(), e.getY(), color);
									draw.getArea().getChildren().addAll(classd, classd.dataBox, classd.funBox,
											classd.label, classd.getText(false), classd.resizeB);
									// classd.resizeB.toBack();
									classd.dataBox.addEventFilter(MouseEvent.MOUSE_CLICKED,
											new EventHandler<MouseEvent>() {
												@Override
												public void handle(MouseEvent e) {
													ClassDataBox box = new ClassDataBox(stage);
													box.sizeToScene();
													container.setDisable(true);
													box.setAlwaysOnTop(true);
													box.showAndWait();
													if (box.condition.equals("create")) {
														classd.addData(box.label);
														Text data = new Text();
														if (box.isStatic) {
															data.setUnderline(true);
														}
														// TextFlow
														// flow=coloredClassDataLabel(box.label);

														int size = classd.getDatas().size();
														data.textProperty()
																.bindBidirectional(classd.getDatas().get(--size));
														data.setLayoutX(classd.dataBox.getX() + 10);
														data.setLayoutY(
																classd.dataBox.getY() + classd.dataBox.getHeight());

														data.layoutXProperty().bind(classd.dataBox.xProperty().add(10));
														data.layoutYProperty().bind(classd.dataBox.yProperty()
																.add(classd.dataBox.getHeight()));
														classd.dataBox.setHeight(classd.dataBox.getHeight() + 20);

														if ((data.layoutBoundsProperty().getValue().getWidth()
																+ 10) >= classd.dataBox.getWidth()) {
															classd.dataBox.setWidth(
																	data.layoutBoundsProperty().getValue().getWidth()
																			+ 30);
														}
														draw.getArea().getChildren().add(data);

														data.addEventFilter(MouseEvent.MOUSE_PRESSED,
																new EventHandler<MouseEvent>() {
																	@Override
																	public void handle(MouseEvent e) {
																		EditClassDataBox box = new EditClassDataBox(
																				stage, data.getText().trim());
																		box.sizeToScene();
																		container.setDisable(true);
																		box.setAlwaysOnTop(true);
																		box.showAndWait();
																		if (box.condition.equals("Delete")) {
																			draw.getArea().getChildren().remove(data);
																			// classd.dataBox.setHeight(
																			// classd.dataBox.getHeight()
																			// -
																			// 20);
																		} else if (box.condition.equals("Update")) {
																			data.setText("Changed");
																		}
																		container.setDisable(false);
																	}
																});

													}
													container.setDisable(false);
												}
											});
									classd.funBox.addEventFilter(MouseEvent.MOUSE_CLICKED,
											new EventHandler<MouseEvent>() {
												@Override
												public void handle(MouseEvent e) {
													ClassFunBox box = new ClassFunBox(stage);
													box.sizeToScene();
													container.setDisable(true);
													box.setAlwaysOnTop(true);
													box.showAndWait();
													if (box.condition.equals("create")) {
														classd.addFunction(box.label);
														Text data = new Text();
														int size = classd.getFunctions().size();
														data.textProperty()
																.bindBidirectional(classd.getFunctions().get(--size));
														data.setLayoutX(classd.funBox.getX() + 10);
														data.setLayoutY(
																classd.funBox.getY() + classd.funBox.getHeight());

														data.layoutXProperty().bind(classd.funBox.xProperty().add(10));
														data.layoutYProperty().bind(classd.funBox.yProperty()
																.add(classd.funBox.getHeight()));

														classd.funBox.setHeight(classd.funBox.getHeight() + 20);

														if ((data.layoutBoundsProperty().getValue().getWidth()
																+ 10) >= classd.funBox.getWidth()) {
															classd.funBox.setWidth(
																	data.layoutBoundsProperty().getValue().getWidth()
																			+ 30);
														}
														draw.getArea().getChildren().add(data);
													}
													container.setDisable(false);
												}
											});

									break;
								case CABSTRACT:
									ClassAbstract cabstract = new ClassAbstract(e.getX(), e.getY(), color);
									draw.getArea().getChildren().addAll(cabstract, cabstract.funBox, cabstract.label,
											cabstract.getText(false), cabstract.resizeB);
									cabstract.funBox.addEventFilter(MouseEvent.MOUSE_CLICKED,
											new EventHandler<MouseEvent>() {
												@Override
												public void handle(MouseEvent e) {
													ClassFunBox box = new ClassFunBox(stage);
													box.sizeToScene();
													container.setDisable(true);
													box.setAlwaysOnTop(true);
													box.showAndWait();
													if (box.condition.equals("create")) {
														cabstract.addFunction(box.label);
														Text data = new Text();
														data.setFont(Font.font("Arial", FontWeight.NORMAL,
																FontPosture.ITALIC, 13));
														int size = cabstract.getFunctions().size();
														data.textProperty().bindBidirectional(
																cabstract.getFunctions().get(--size));
														data.setLayoutX(cabstract.funBox.getX() + 10);
														data.setLayoutY(
																cabstract.funBox.getY() + cabstract.funBox.getHeight());

														data.layoutXProperty()
																.bind(cabstract.funBox.xProperty().add(10));
														data.layoutYProperty().bind(cabstract.funBox.yProperty()
																.add(cabstract.funBox.getHeight()));

														cabstract.funBox.setHeight(cabstract.funBox.getHeight() + 20);

														if ((data.layoutBoundsProperty().getValue().getWidth()
																+ 10) >= cabstract.funBox.getWidth()) {
															cabstract.funBox.setWidth(
																	data.layoutBoundsProperty().getValue().getWidth()
																			+ 30);
														}
														draw.getArea().getChildren().add(data);
													}
													container.setDisable(false);
												}
											});
									break;
								case CINTERFACE:
									ClassInterface inter = new ClassInterface(e.getX(), e.getY(), color);
									draw.getArea().getChildren().addAll(inter, inter.head, inter.dataBox, inter.funBox,
											inter.label, inter.getText(false), inter.resizeB);
									inter.dataBox.addEventFilter(MouseEvent.MOUSE_CLICKED,
											new EventHandler<MouseEvent>() {
												@Override
												public void handle(MouseEvent e) {
													ClassDataBox box = new ClassDataBox(stage);
													box.sizeToScene();
													container.setDisable(true);
													box.setAlwaysOnTop(true);
													box.showAndWait();
													if (box.condition.equals("create")) {
														inter.addData(box.label);
														Text data = new Text();
														if (box.isStatic) {
															data.setUnderline(true);
														}
														// TextFlow
														// flow=coloredClassDataLabel(box.label);

														int size = inter.getDatas().size();
														data.textProperty()
																.bindBidirectional(inter.getDatas().get(--size));
														data.setLayoutX(inter.dataBox.getX() + 10);
														data.setLayoutY(
																inter.dataBox.getY() + inter.dataBox.getHeight());

														data.layoutXProperty().bind(inter.dataBox.xProperty().add(10));
														data.layoutYProperty().bind(inter.dataBox.yProperty()
																.add(inter.dataBox.getHeight()));

														inter.dataBox.setHeight(inter.dataBox.getHeight() + 20);

														if ((data.layoutBoundsProperty().getValue().getWidth()
																+ 10) >= inter.dataBox.getWidth()) {
															inter.dataBox.setWidth(
																	data.layoutBoundsProperty().getValue().getWidth()
																			+ 30);
														}
														draw.getArea().getChildren().add(data);
													}
													container.setDisable(false);
												}
											});
									inter.funBox.addEventFilter(MouseEvent.MOUSE_CLICKED,
											new EventHandler<MouseEvent>() {
												@Override
												public void handle(MouseEvent e) {
													ClassFunBox box = new ClassFunBox(stage);
													box.sizeToScene();
													container.setDisable(true);
													box.setAlwaysOnTop(true);
													box.showAndWait();
													if (box.condition.equals("create")) {
														inter.addFunction(box.label);
														Text data = new Text();
														int size = inter.getFunctions().size();
														data.textProperty()
																.bindBidirectional(inter.getFunctions().get(--size));
														data.setLayoutX(inter.funBox.getX() + 10);
														data.setLayoutY(inter.funBox.getY() + inter.funBox.getHeight());

														data.layoutXProperty().bind(inter.funBox.xProperty().add(10));
														data.layoutYProperty().bind(
																inter.funBox.yProperty().add(inter.funBox.getHeight()));

														inter.funBox.setHeight(inter.funBox.getHeight() + 20);

														if ((data.layoutBoundsProperty().getValue().getWidth()
																+ 10) >= inter.funBox.getWidth()) {
															inter.funBox.setWidth(
																	data.layoutBoundsProperty().getValue().getWidth()
																			+ 30);
														}
														draw.getArea().getChildren().add(data);
													}
													container.setDisable(false);
												}
											});
									break;
								case CDEPENDENCY:
									cdepen = new ClassDepen(e.getX(), e.getY(), e.getX(), e.getY(), color);
									draw.getChildren().add(cdepen);
									menu.isCDepend = true;
									break;
								default:
									break;

								}
								draw.setCTool(Tool.POINTER);

							} else if (scene.getCursor() == Cursor.HAND) {

								if (obj instanceof UCProcess) {
									UCProcess ucprocess = (UCProcess) obj;
									ucprocess.setFill(color);
								} else if (obj instanceof UCActor) {
									UCActor actor = (UCActor) obj;
									actor.setFill(color);
								} else if (obj instanceof UCBoundary) {
									UCBoundary boundary = (UCBoundary) obj;
									boundary.setFill(color);
								} else if (obj instanceof SERole) {
									SERole role = (SERole) obj;
									role.setFill(color);
								}

							} else if (scene.getCursor() == Cursor.CROSSHAIR) {
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
							} else if (menu.isActivation) {
								seactivation.setEndX(e.getX());
								seactivation.setEndY(e.getY());
							} else if (menu.isNActivation) {
								senactivation.setEndX(e.getX());
								senactivation.setEndY(e.getY());
							} else if (menu.isDActivation) {
								sedactivation.setEndX(e.getX());
								sedactivation.setEndY(e.getY());
							} else if (menu.isCDepend) {
								cdepen.setEndX(e.getX());
								cdepen.setEndY(e.getY());
							} else if (menu.isRegionDraw) {
								if (e.getX() > region.getX() + region.getWidth())
									region.setWidth(region.getWidth() + 4);
								else if (e.getX() < region.getX() + region.getWidth())
									region.setWidth(region.getWidth() - 4);

								if (e.getY() > region.getY() + region.getHeight())
									region.setHeight(region.getHeight() + 4);
								else if (e.getY() < region.getY() + region.getHeight())
									region.setHeight(region.getHeight() - 4);

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
								draw.getArea().getChildren().addAll(ucrelation.snode, ucrelation.enode,
										ucrelation.mnode);
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
							} else if (menu.isActivation) {
								if (seactivation.getStartX() < seactivation.getEndX()) {
									draw.getArea().getChildren().addAll(seactivation.top, seactivation.bot,
											seactivation.activate, seactivation.rLine, seactivation.rtop,
											seactivation.rbot, seactivation.msg, seactivation.snode,
											seactivation.getText(false));
									seactivation.snode.toBack();
								}

								menu.isActivation = false;
								seactivation = null;
							} else if (menu.isNActivation) {
								if (senactivation.getStartX() < senactivation.getEndX()) {
									draw.getArea().getChildren().addAll(senactivation.top, senactivation.bot,
											senactivation.newOb, senactivation.nLine, senactivation.label,
											senactivation.lifeB, senactivation.rLine, senactivation.rtop,
											senactivation.rbot, senactivation.getText(false));
									menu.isNActivation = false;
									senactivation = null;
								}
							} else if (menu.isDActivation) {
								if (sedactivation.getStartX() < sedactivation.getEndX()) {
									draw.getArea().getChildren().addAll(sedactivation.top, sedactivation.bot,
											sedactivation.c1, sedactivation.c2, sedactivation.enode);
									sedactivation.enode.toBack();
									menu.isDActivation = false;
									sedactivation = null;
								}
							} else if (menu.isCDepend) {
								if (cdepen.filterLine()) {
									draw.getArea().getChildren().addAll(cdepen.l1, cdepen.l2, cdepen.l3, cdepen.node1,
											cdepen.node2, cdepen.startNode, cdepen.endNode, cdepen.top, cdepen.bot);
								}
								cdepen.l1.toFront();
								cdepen.l2.toFront();
								cdepen.l3.toFront();
								cdepen = null;
								menu.isCDepend = false;
							} else if (menu.isRegionDraw) {
								// region.accessibleHelpProperty()

								VBox priceBox = new VBox();
								Button printB = new Button("P");
								priceBox.getChildren().addAll(printB);
								priceBox.layoutXProperty().bind(region.xProperty().add(region.getWidth()));
								priceBox.layoutYProperty()
										.bind(region.yProperty().add(region.getHeight()).subtract(50));
								draw.getArea().getChildren().addAll(priceBox);

								container.setDisable(false);
								menu.isRegionDraw = false;

								printB.setOnAction(e2 -> {
									// draw.getArea().getChildren().removeAll(priceBox);
									String picName = null;
									try {
										SnapshotParameters parameters = new SnapshotParameters();
										Rectangle2D toPaint = new Rectangle2D(region.getX(), region.getY(),
												region.getWidth(), region.getHeight());
										parameters.setViewport(toPaint);
										picName = "" + System.currentTimeMillis();
										WritableImage snapshot = draw.getArea().snapshot(parameters, null);
										File output = new File("ImgTemp/" + picName + ".png");
										ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", output);
									} catch (IOException ex) {

									}

									regionPrint();

									// Show Preview Print
									BPrintPreview preview = new BPrintPreview(stage, picName);
									preview.sizeToScene();
									container.setDisable(true);
									preview.setAlwaysOnTop(true);
									preview.showAndWait();
									draw.getArea().getChildren().removeAll(priceBox, region);
									region = null;
								});

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

		menu.oProject.setOnAction(e -> {
			BOpen box = new BOpen(stage);
			box.sizeToScene();
			container.setDisable(true);
			box.setAlwaysOnTop(true);
			box.showAndWait();
			if (box.condition.equals("open")) {
				container.setCenter(tabPane);
				addOldTab(box.files, box.projectName);
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
				File file = null;
				switch (osType) {
				case "Unix":
					file = new File("Diagrams/" + box.nameF.getText().toString().trim());
					if (!file.exists()) {
						file.mkdir();
						System.out.println("Folder @" + box.nameF.getText().toString() + " is created!");
					} else {
						System.out.println("Folder already exists.");
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Alert!");
						alert.setHeaderText("Project Folder is Alreay exists.");
						alert.setContentText("Ooops, that is error");
						alert.showAndWait();
						openNewProjectBox();
					}
					break;
				case "Windows":
					// Not Support!
					break;
				}
			}
			container.setDisable(false);
		});

		menu.export.setOnAction(e -> {
			BExport box = new BExport(stage);
			box.sizeToScene();
			container.setDisable(true);
			box.setAlwaysOnTop(true);
			box.showAndWait();
			if (box.condition.equals("finish")) {

			}
			container.setDisable(false);
		});

		menu.importD.setOnAction(e -> {
			iFile = menu.fileChoose.showOpenDialog(stage);
			String format = iFile.getName().substring(iFile.getName().indexOf(".") + 1, iFile.getName().length());
			String name = iFile.getName().substring(0, iFile.getName().indexOf("."));
			System.out.println(" Name :" + name);
			if (format.equals("uml")) {
				if (!isPacked(name)) {
					Pack pack = new Pack();
					pack.doUnPack(iFile.getPath(), name);
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Project Package Already Exists");
					alert.setContentText("Rename project package .");
					alert.show();
				}

			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Fomart Error!");
				alert.setHeaderText("UML Diagram");
				alert.setContentText("Open supported UML Package");
				alert.show();
			}
		});

		menu.save.setOnAction(e -> {
			// menu.saveB.fire();
		});

		menu.saveB.setOnAction(e -> {
			if (!tabPane.getSelectionModel().isEmpty()) {
				Draw draw = (Draw) tabPane.getSelectionModel().getSelectedItem().getContent();
				String file = tabPane.getSelectionModel().getSelectedItem().getText();
				switch (draw.diagram) {
				case 1:
					try {
						UCXml xml = new UCXml("Diagrams/" + draw.projectName + "/" + file + ".xml");
						xml.add(draw.objects);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					break;
				}
			}
		});
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

		tempPIC = new File("ImgTemp");
		if (!tempPIC.exists()) {
			tempPIC.mkdir();
			System.out.println("Temp Photo Folder is created");
		} else {
			System.out.println("Temp Photo Folder is already exists");
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
						draw = new Draw(scene, diagram, folder);
						tab.setContent(draw);
						tab.setText(name);
						tabPane.getTabs().add(tab);
					}
				} else {
					System.out.println("File already exists.");
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Alert!");
					alert.setHeaderText("File Alreay exists.");
					alert.setContentText("Ooops, there was an error!");
					alert.showAndWait();
					menu.nFile.fire();
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

	public void addOldTab(ArrayList<String> files, String folder) {
		for (int i = 0; i < files.size(); i++) {
			String name = files.get(i).substring(0, files.get(i).indexOf("."));
			File file = new File("Diagrams/" + folder + "/" + name + ".xml");
			Tab tab = new Tab();
			switch (osType) {
			case "Unix":
				if (file.exists()) {
					System.out.println("File @" + name + " is loading...!");
					draw = new Draw(scene, 1, folder);
					tab.setContent(draw);
					tab.setText(name);
					tabPane.getTabs().add(tab);
				}
				break;
			case "Windows":
				// Not Support!
				break;
			}
		}

	}

	public void regionPrint() {
		for (Object obj : draw.objects) {
			UCProcess var = (UCProcess) obj;
			System.out.println(" Intersects " + region.intersects(var.getBoundsInLocal()));
		}
	}

	// Clean Temp Photo
	public void clean() {
		for (File file : tempPIC.listFiles()) {
			file.delete();
		}
	}

	public void openNewProjectBox() {
		menu.nProject.fire();
	}

	public boolean isPacked(String name) {
		boolean ispacked = false;
		File file = new File("Diagrams/" + name);
		if (file.exists()) {
			ispacked = true;
		}
		return ispacked;
	}
}
