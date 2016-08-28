package XMLFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import UseCase.UCProcess;
import javafx.scene.paint.Color;

public class CopyXML {
	protected DocumentBuilderFactory dbFactory;
	protected DocumentBuilder dBuilder;
	public Document document;
	private Element root;
	protected NodeList nList;

	public CopyXML() throws IOException {
		File xml = new File("Temp/Copy.xml");
		if (!xml.exists()) {
			if (xml.createNewFile()) {
				System.out.println("Build Copy.xml File");
			}
		} else {
			System.out.println("Copy.xml File already exist");
		}
		buildCopyXML();

	}

	public void buildCopyXML() {
		dbFactory = DocumentBuilderFactory.newInstance();
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			document = dBuilder.newDocument();
		} catch (ParserConfigurationException parserException) {
			parserException.printStackTrace();
		}
		root = document.createElement("Doc");
		document.appendChild(root);
		try {
			Source xmlSource = new DOMSource(document);
			Result result = new StreamResult(new FileOutputStream("Temp/Copy.xml"));
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.transform(xmlSource, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void copyUCProcess(double x, double y, double xr, double yr, String l, String c) {
		removeChilds(root);

		Element diagram = document.createElement("diagram");
		Element centerx = document.createElement("centerx");
		Element centery = document.createElement("centery");
		Element xradius = document.createElement("xradius");
		Element yradius = document.createElement("yradius");
		Element label = document.createElement("label");
		Element color = document.createElement("color");

		diagram.appendChild(document.createTextNode("UCProcess"));
		centerx.appendChild(document.createTextNode("" + x));
		centery.appendChild(document.createTextNode("" + y));
		xradius.appendChild(document.createTextNode("" + xr));
		yradius.appendChild(document.createTextNode("" + yr));
		label.appendChild(document.createTextNode("" + l));
		color.appendChild(document.createTextNode("" + c));

		root.appendChild(diagram);
		root.appendChild(centerx);
		root.appendChild(centery);
		root.appendChild(xradius);
		root.appendChild(yradius);
		root.appendChild(label);
		root.appendChild(color);

		save();
		System.out.println("#Success Copy");
	}

	public void copyUCActor(double x, double y, String l, String c) {
		removeChilds(root);

		Element diagram = document.createElement("diagram");
		Element centerx = document.createElement("centerx");
		Element centery = document.createElement("centery");
		Element label = document.createElement("label");
		Element color = document.createElement("color");

		diagram.appendChild(document.createTextNode("UCActor"));
		centerx.appendChild(document.createTextNode("" + x));
		centery.appendChild(document.createTextNode("" + y));
		label.appendChild(document.createTextNode("" + l));
		color.appendChild(document.createTextNode("" + c));

		root.appendChild(diagram);
		root.appendChild(centerx);
		root.appendChild(centery);
		root.appendChild(label);
		root.appendChild(color);
		save();
		System.out.println("#Success Copy");
	}

	public void copyUCBoundary(double x, double y, double w, double h, String l, String c) {
		removeChilds(root);

		Element diagram = document.createElement("diagram");
		Element centerx = document.createElement("x");
		Element centery = document.createElement("y");
		Element width = document.createElement("width");
		Element height = document.createElement("height");
		Element label = document.createElement("label");
		Element color = document.createElement("color");

		diagram.appendChild(document.createTextNode("UCBoundary"));
		centerx.appendChild(document.createTextNode("" + x));
		centery.appendChild(document.createTextNode("" + y));
		label.appendChild(document.createTextNode("" + l));
		color.appendChild(document.createTextNode("" + c));
		width.appendChild(document.createTextNode("" + w));
		height.appendChild(document.createTextNode("" + h));

		root.appendChild(diagram);
		root.appendChild(centerx);
		root.appendChild(centery);
		root.appendChild(label);
		root.appendChild(color);
		root.appendChild(width);
		root.appendChild(height);

		save();
		System.out.println("#Success Copy");
	}
	
	

	public void save() {
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult("Temp/Copy.xml");
			transformer.transform(domSource, streamResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void removeChilds(Node node) {
		while (node.hasChildNodes())
			node.removeChild(node.getFirstChild());
	}
}
