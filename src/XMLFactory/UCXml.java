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

import GTool.GLabel;
import UseCase.UCActor;
import UseCase.UCBoundary;
import UseCase.UCExtend;
import UseCase.UCGeneral;
import UseCase.UCInclude;
import UseCase.UCProcess;
import UseCase.UCRelation;

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
			root.setAttribute("Diagram", "UseCase");
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
				diagram.setAttribute("Type", "UCProcess");
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

			if (objects.get(i) instanceof UCActor) {
				UCActor actor = (UCActor) objects.get(i);
				Element diagram = document.createElement("Diagram");
				diagram.setAttribute("Type", "UCActor");
				Element centerx = document.createElement("Centerx");
				Element centery = document.createElement("Centery");
				Element label = document.createElement("Label");
				Element color = document.createElement("Color");

				centerx.appendChild(document.createTextNode("" + actor.getCenterX()));
				centery.appendChild(document.createTextNode("" + actor.getCenterY()));
				label.appendChild(document.createTextNode("" + actor.data.get()));
				color.appendChild(document.createTextNode("" + actor.getFill().toString()));

				diagram.appendChild(centerx);
				diagram.appendChild(centery);
				diagram.appendChild(label);
				diagram.appendChild(color);
				root.appendChild(diagram);
			}

			if (objects.get(i) instanceof UCBoundary) {
				UCBoundary boundary = (UCBoundary) objects.get(i);
				Element diagram = document.createElement("Diagram");
				diagram.setAttribute("Type", "UCBoundary");
				Element x = document.createElement("x");
				Element y = document.createElement("y");
				Element w = document.createElement("width");
				Element h = document.createElement("height");
				Element label = document.createElement("Label");
				Element color = document.createElement("Color");

				x.appendChild(document.createTextNode("" + boundary.getX()));
				y.appendChild(document.createTextNode("" + boundary.getY()));
				w.appendChild(document.createTextNode("" + boundary.getWidth()));
				h.appendChild(document.createTextNode("" + boundary.getHeight()));
				label.appendChild(document.createTextNode("" + boundary.data.get()));
				color.appendChild(document.createTextNode("" + boundary.getFill().toString()));

				diagram.appendChild(x);
				diagram.appendChild(y);
				diagram.appendChild(w);
				diagram.appendChild(h);
				diagram.appendChild(label);
				diagram.appendChild(color);
				root.appendChild(diagram);
			}

			if (objects.get(i) instanceof UCExtend) {
				UCExtend extend = (UCExtend) objects.get(i);
				Element diagram = document.createElement("Diagram");
				diagram.setAttribute("Type", "UCExtend");

				Element startx = document.createElement("startX");
				Element starty = document.createElement("startY");
				Element endx = document.createElement("endx");
				Element endy = document.createElement("endy");
				Element labelx = document.createElement("labelX");
				Element labely = document.createElement("labelY");

				startx.appendChild(document.createTextNode("" + extend.getStartX()));
				starty.appendChild(document.createTextNode("" + extend.getStartY()));
				endx.appendChild(document.createTextNode("" + extend.getEndX()));
				endy.appendChild(document.createTextNode("" + extend.getEndY()));
				labelx.appendChild(document.createTextNode("" + extend.label.getX()));
				labely.appendChild(document.createTextNode("" + extend.label.getY()));

				diagram.appendChild(startx);
				diagram.appendChild(starty);
				diagram.appendChild(endx);
				diagram.appendChild(endy);
				diagram.appendChild(labelx);
				diagram.appendChild(labely);
				root.appendChild(diagram);
			}

			if (objects.get(i) instanceof UCInclude) {
				UCInclude extend = (UCInclude) objects.get(i);
				Element diagram = document.createElement("Diagram");
				diagram.setAttribute("Type", "UCInclude");

				Element startx = document.createElement("startX");
				Element starty = document.createElement("startY");
				Element endx = document.createElement("endx");
				Element endy = document.createElement("endy");
				Element labelx = document.createElement("labelX");
				Element labely = document.createElement("labelY");

				startx.appendChild(document.createTextNode("" + extend.getStartX()));
				starty.appendChild(document.createTextNode("" + extend.getStartY()));
				endx.appendChild(document.createTextNode("" + extend.getEndX()));
				endy.appendChild(document.createTextNode("" + extend.getEndY()));
				labelx.appendChild(document.createTextNode("" + extend.label.getX()));
				labely.appendChild(document.createTextNode("" + extend.label.getY()));

				diagram.appendChild(startx);
				diagram.appendChild(starty);
				diagram.appendChild(endx);
				diagram.appendChild(endy);
				diagram.appendChild(labelx);
				diagram.appendChild(labely);
				root.appendChild(diagram);
			}

			if (objects.get(i) instanceof UCGeneral) {
				UCGeneral general = (UCGeneral) objects.get(i);
				Element diagram = document.createElement("Diagram");
				diagram.setAttribute("Type", "UCGeneral");

				Element startx = document.createElement("startX");
				Element starty = document.createElement("startY");
				Element endx = document.createElement("endx");
				Element endy = document.createElement("endy");
				Element color = document.createElement("color");

				startx.appendChild(document.createTextNode("" + general.getStartX()));
				starty.appendChild(document.createTextNode("" + general.getStartY()));
				endx.appendChild(document.createTextNode("" + general.getEndX()));
				endy.appendChild(document.createTextNode("" + general.getEndY()));
				color.appendChild(document.createTextNode("" + general.tri.getFill()));

				diagram.appendChild(startx);
				diagram.appendChild(starty);
				diagram.appendChild(endx);
				diagram.appendChild(endy);
				diagram.appendChild(color);
				root.appendChild(diagram);
			}

			if (objects.get(i) instanceof UCRelation) {
				UCRelation extend = (UCRelation) objects.get(i);
				Element diagram = document.createElement("Diagram");
				diagram.setAttribute("Type", "UCRelation");

				Element startx = document.createElement("startX");
				Element starty = document.createElement("startY");
				Element endx = document.createElement("endx");
				Element endy = document.createElement("endy");
				startx.appendChild(document.createTextNode("" + extend.getStartX()));
				starty.appendChild(document.createTextNode("" + extend.getStartY()));
				endx.appendChild(document.createTextNode("" + extend.getEndX()));
				endy.appendChild(document.createTextNode("" + extend.getEndY()));
				diagram.appendChild(startx);
				diagram.appendChild(starty);
				diagram.appendChild(endx);
				diagram.appendChild(endy);
				root.appendChild(diagram);
			}
			
			if (objects.get(i) instanceof GLabel) {
				GLabel label = (GLabel) objects.get(i);
				Element diagram = document.createElement("Diagram");
				diagram.setAttribute("Type", "GLabel");

				Element x = document.createElement("x");
				Element y = document.createElement("y");
				Element size = document.createElement("size");
				Element color = document.createElement("color");
				x.appendChild(document.createTextNode("" + label.getX()));
				y.appendChild(document.createTextNode("" + label.getY()));
				size.appendChild(document.createTextNode("" + label.getFont().getSize()));
				color.appendChild(document.createTextNode("" + label.getFill()));
				diagram.appendChild(x);
				diagram.appendChild(y);
				diagram.appendChild(size);
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
			System.out.println("#UseCase Diagram is Successfully Saved");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void removeChilds(Node node) {
		while (node.hasChildNodes())
			node.removeChild(node.getFirstChild());
	}
}
