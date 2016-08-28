package Boxs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BPrintPreview extends Stage {
	private BorderPane root;

	private BorderPane printP;
	Label deviceL;
	ListView<String> list;
	Button closeB;
	Button pngB;
	Button printB;

	private ImageView view;
	private Image img;

	public BPrintPreview(Stage owner, String pName) {
		super();
		initModality(Modality.WINDOW_MODAL); // Prevent click parent stage
		initOwner(owner);
		setResizable(false);
		setTitle("Print Preview...");

		root = new BorderPane();

		printP = new BorderPane();
		printP.setPrefWidth(200);
		printP.setMaxWidth(200);
		printP.setPrefHeight(360);
		printP.setMaxHeight(360);
		printP.setStyle("-fx-padding: 20 10 50 20;");

		// deviceL = new Label("Devices");
		list = new ListView<String>();
		closeB = new Button("Close");
		pngB = new Button("PNG");
		printB = new Button("Print");
		HBox btnB = new HBox();
		btnB.setAlignment(Pos.BASELINE_CENTER);
		btnB.setStyle("-fx-padding: 15 0 0 0;");
		btnB.getChildren().addAll(closeB, pngB, printB);

		// printP.setTop(deviceL);
		printP.setCenter(list);
		printP.setBottom(btnB);

		BorderPane imgV = new BorderPane();
		imgV.setPrefWidth(600);
		imgV.setMaxWidth(600);
		imgV.setMaxHeight(390);
		imgV.setStyle("-fx-padding: 20 20 0 0;");
		view = new ImageView();
		try {
			img = new Image(new FileInputStream("ImgTemp/" + pName + ".png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		view.setImage(img);

		double scaleX = 600 / view.getLayoutBounds().getWidth();
		double scaleY = 400 / view.getLayoutBounds().getHeight();
		double min = Math.min(scaleX, scaleY);
		Scale scale = new Scale(min, min);

		view.getTransforms().add(scale);

		imgV.setCenter(view);

		root.setLeft(printP);
		root.setCenter(imgV);

		Scene scene = new Scene(root, 800, 400, Color.WHITE);
		setScene(scene);

		getPrinterList();

		closeB.setOnAction(e -> {
			close();
		});
	}

	public void getPrinterList() {
		PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
		String[] names = new String[printServices.length];

		for (int i = 0; i < printServices.length; i++) {
			names[i] = printServices[i].getName();
		}
		list.getItems().addAll(names);
	}

	public void printNode() {

	}

	public void printPNG() {

	}
}
