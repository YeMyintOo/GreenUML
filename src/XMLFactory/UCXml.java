package XMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import UseCase.UCProcess;

public class UCXml {
	protected DocumentBuilderFactory dbFactory;
	protected DocumentBuilder dBuilder;
	public Document document;
	private Element root;
	protected NodeList nList;
	protected File xml;

	public UCXml(String path) throws IOException {
		xml = new File(path);
		dbFactory = DocumentBuilderFactory.newInstance();
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			document = dBuilder.newDocument();
		} catch (ParserConfigurationException parserException) {
			parserException.printStackTrace();
		}
		if (xml.exists()) {
			root = document.createElement("Document");
			document.appendChild(root);
		} else {
			System.out.println("File Not Found!");
		}
	}

	public void add(ArrayList<Object> objects) {
		System.out.println("Number of Objects to Save :" + objects.size());
		removeChilds(root);
		for (int i = 0; i < objects.size(); i++) {
			if (objects.get(i) instanceof UCProcess) {
				UCProcess process = (UCProcess) objects.get(i);
				Element diagram = document.createElement("Diagram");
				diagram.setAttribute("Type","UCProcess");
				Element centerx = document.createElement("Centerx");
				Element centery = document.createElement("Centery");
				Element xradius = document.createElement("Xradius");
				Element yradius = document.createElement("Yradius");
				Element label = document.createElement("Label");
				Element color = document.createElement("Color");

				// agram.appendChild(document.createTextNode("UCProcess"));
				centerx.appendChild(document.createTextNode("" + process.getCenterX()));
				centery.appendChild(document.createTextNode("" + process.getCenterY()));
				xradius.appendChild(document.createTextNode("" + process.getRadiusX()));
				yradius.appendChild(document.createTextNode("" + process.getRadiusY()));
				label.appendChild(document.createTextNode("" + process.data.get()));
				color.appendChild(document.createTextNode("" + process.getFill().toString()));

				diagram.appendChild(centerx);
				diagram.appendChild(centery);
				diagram.appendChild(xradius);
				diagram.appendChild(yradius);
				diagram.appendChild(label);
				diagram.appendChild(color);

				root.appendChild(diagram);
			}
		}
		save();
	}

	public void save() {
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult(xml);
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
