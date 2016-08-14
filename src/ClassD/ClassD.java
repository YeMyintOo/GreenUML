package ClassD;

import java.util.ArrayList;

import Libraries.CNode;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ClassD extends Rectangle {
	private DropShadow shape;

	public Rectangle dataBox; // Data area box
	public Rectangle funBox; // Function area box

	private StringProperty name; // Class name
	private ArrayList<StringProperty> datas;
	private ArrayList<StringProperty> functions;

	public Text label;
	public TextField field;

	public CNode resizeB;

	public ClassD(double x, double y, Color color) {
		super(x, y, 140, 40);
		setFill(color);
		setStroke(Color.BLACK);
		shape = new DropShadow();
		name = new SimpleStringProperty("Class");

		dataBox = new Rectangle(x, y + getHeight(), 100, 30);
		dataBox.setFill(Color.WHITE);
		dataBox.setStroke(Color.BLACK);
		dataBox.xProperty().bind(this.xProperty());
		dataBox.yProperty().bind(this.yProperty().add(getHeight()));

		funBox = new Rectangle(dataBox.getX(), dataBox.getY() + dataBox.getHeight(), 100, 30);
		funBox.setFill(Color.WHITE);
		funBox.setStroke(Color.BLACK);
		funBox.xProperty().bind(dataBox.xProperty());
		funBox.yProperty().bind(dataBox.yProperty().add(dataBox.getHeight()));

		label = new Text(name.get());
		label.setFont(Font.font("Arial", FontWeight.BLACK, 15));
		label.textProperty().bind(name);
		label.xProperty().bind(xProperty().add(widthProperty().getValue() / 2)
				.subtract(label.layoutBoundsProperty().getValue().getWidth() / 2));
		label.yProperty().bind(yProperty().add(23));

		field = new TextField();
		field.layoutXProperty().bind(xProperty().subtract(10));
		field.layoutYProperty().bind(yProperty().add(5));
		field.textProperty().bindBidirectional(name);

		resizeB = new CNode();
		resizeB.centerXProperty().bind(xProperty().add(widthProperty()));
		resizeB.centerYProperty().bind(yProperty().add(20));

		resizeB.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent key) {
				if (key.getX() > resizeB.getCenterX())
					setWidth(getWidth() + 2);
				if (key.getX() < resizeB.getCenterX())
					setWidth(getWidth() - 2);
			}
		});

		dataBox.heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				funBox.yProperty().bind(dataBox.heightProperty().add(dataBox.yProperty()));
			}
		});

		widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				label.xProperty().bind(xProperty().add(widthProperty().getValue() / 2)
						.subtract(label.layoutBoundsProperty().getValue().getWidth() / 2));

			}
		});

		funBox.widthProperty().bindBidirectional(widthProperty());
		dataBox.widthProperty().bindBidirectional(widthProperty());

		datas = new ArrayList<StringProperty>();
		functions = new ArrayList<StringProperty>();

		addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent key) {
				setX(key.getX());
				setY(key.getY());
			}
		});

		addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent key) {
				setEffect(shape);
			}
		});
		addEventFilter(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent key) {
				setEffect(null);
			}
		});

		label.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent key) {
				if (key.getClickCount() == 2) {
					field.setPrefWidth(widthProperty().get() + 20);
					field.setVisible(true);
				}
			}
		});

		field.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				if (label.layoutBoundsProperty().getValue().getWidth() >= getWidth()) {
					setWidth(label.layoutBoundsProperty().getValue().getWidth() + 60);
				}

				if (e.getCode() == KeyCode.ENTER) {
					field.setVisible(false);
					label.xProperty().bind(xProperty().add(widthProperty().getValue() / 2)
							.subtract(label.layoutBoundsProperty().getValue().getWidth() / 2));
					checkLabel();
				}
			}
		});

	}

	public TextField getText(boolean isShow) {
		if (isShow) {
			field.setVisible(isShow);
		} else {
			field.setVisible(false);
		}
		return field;
	}

	public void addData(String data) {
		StringProperty d = new SimpleStringProperty(data);
		datas.add(d);
	}

	public void addFunction(String data) {
		StringProperty d = new SimpleStringProperty(data);
		functions.add(d);
	}

	public ArrayList<StringProperty> getDatas() {
		return datas;
	}

	public ArrayList<StringProperty> getFunctions() {
		return functions;
	}

	public void checkLabel() {
		String var = name.get();
		char v2 = var.charAt(0);
		if (Character.isUpperCase(v2)) {
			label.setFill(Color.BLACK);
			label.setUnderline(false);
		} else {
			label.setFill(Color.RED);
			label.setUnderline(true);
		}

	}

}
