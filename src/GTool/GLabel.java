package GTool;

import java.awt.GraphicsEnvironment;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class GLabel extends Text {

	public TextField text;
	private StringProperty data;

	public HBox tool;
	private ColorPicker colorP;
	private TextField size;
	private int fsize;
	private String[] fonts;

	public GLabel(double x, double y, String[] fonts) {
		super();
		setX(x);
		setY(y);
		this.fonts = fonts;

		text = new TextField();
		data = new SimpleStringProperty("Label...");
		tool = new HBox();
		tool.setSpacing(20);
		tool.setPrefWidth(160);

		colorP = new ColorPicker();
		colorP.setValue(Color.BLACK);
		colorP.setPrefWidth(100);
		size = new TextField();
		size.setMaxWidth(50);
		

		tool.getChildren().addAll(colorP, size);
		tool.layoutXProperty().bind(text.layoutXProperty());
		tool.layoutYProperty().bind(yProperty().add(layoutBoundsProperty().get().getHeight()));

		this.textProperty().bindBidirectional(data);
		setFont(Font.font("Arial", FontWeight.NORMAL, 15));

		text.layoutXProperty().bind(xProperty());
		text.layoutYProperty().bind(yProperty().subtract(layoutBoundsProperty().get().getHeight()));

		size.setText("" + (int) getFont().getSize());

		text.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {

				if (e.getCode() == KeyCode.ENTER) {
					data.set(text.getText());
					text.setVisible(false);
					tool.setVisible(false);
				}
			}
		});

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
				if (key.getClickCount() == 2) {
					text.setMinWidth(layoutBoundsProperty().get().getWidth());

					text.setVisible(true);
					tool.setVisible(true);
				}
			}
		});
		colorP.setOnAction(e -> {
			setFill(colorP.getValue());
		});

		size.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				if (!size.getText().equals("")) {
					fsize = Integer.parseInt(size.getText());
					System.out.println(" Font Size :" + fsize);
					setFont(Font.font("Arial", FontWeight.NORMAL, fsize));
				} else {
					System.out.println("Empyt Size");
					setFont(Font.font("Arial", FontWeight.NORMAL, 15));
				}
				if (e.getCode() == KeyCode.ENTER) {
					data.set(text.getText());
					text.setVisible(false);
					tool.setVisible(false);
				}

			}
		});
	}

	public TextField getText(boolean isShow) {
		if (isShow) {
			text.setVisible(true);
		} else {
			text.setVisible(false);
		}
		return text;
	}

	public HBox getTool(boolean isShow) {
		if (isShow) {
			tool.setVisible(true);
		} else {
			tool.setVisible(false);
		}
		return tool;
	}

}
