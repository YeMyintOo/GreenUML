package GUI;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Home extends BorderPane {

	BorderPane root;
	Text uml;
	Text green;
	Text sample;
	HBox flow;

	public Home() {
		root = new BorderPane();

		green = new Text("Green");
		green.setFont(Font.font("Purisa", FontWeight.BOLD, 110));
		green.setFill(Color.DARKGREEN);

		uml = new Text("UML");
		uml.setFont(Font.font("FreeSerif", FontWeight.SEMI_BOLD, 130));
		uml.setFill(Color.BROWN);

		flow = new HBox();
		flow.setAlignment(Pos.CENTER);
		flow.getChildren().addAll(green,uml);
		
		HBox data=new HBox();
		sample=new Text("[Simple & Easy]");
		sample.setFont(Font.font("Arial", FontWeight.NORMAL, 50));
		sample.setFill(Color.LIGHTSALMON);
		data.getChildren().add(sample);
		data.setAlignment(Pos.TOP_CENTER);
		

		root.setCenter(flow);
		root.setBottom(data);
		root.setStyle("-fx-padding:150 0 300 0;-fx-background-color:white;");

		setCenter(root);
	}
}
