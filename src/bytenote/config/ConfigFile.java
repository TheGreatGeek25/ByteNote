package bytenote.config;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import bytenote.note.types.NoteTypes.NoteType;
import javafx.scene.paint.Color;

public class ConfigFile extends File {

	private static final long serialVersionUID = -5858752517557176569L;
	
	public static final String CONFIG = "config.xml";
	
	public ConfigFile() {
		super(Config.getURIInConfigDir(CONFIG));
	}
	
	public Config readData() {
		try {
			Config config = new Config();
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder;
			documentBuilder = dbFactory.newDocumentBuilder();
			
			Document doc = documentBuilder.parse(this);
			
			Element configElement = doc.getDocumentElement();
			config.bytenoteVersion = configElement.getAttribute("bytenoteVersion");
			
			Element noteTypes = (Element) doc.getElementsByTagName("noteTypes").item(0);
			NodeList types = noteTypes.getElementsByTagName("type");
			for(int i=0; i<types.getLength(); i++) {
				Element typeNode = (Element) types.item(i);
				Color color = Color.web(typeNode.getAttribute("color"));
				config.noteTypes.add( new NoteType(typeNode.getAttribute("name"), color, false) );
			}
			
			return config;
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void writeData(Config data) throws ParserConfigurationException, TransformerException {
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();
		Document doc = documentBuilder.newDocument();
		
		Element configElement = doc.createElement("config");
		configElement.setAttribute("bytenoteVersion", data.bytenoteVersion);
		doc.appendChild(configElement);
		
		Element noteTypes = doc.createElement("noteTypes");
		for(NoteType type: data.noteTypes) {
			Element typeElement = doc.createElement("type");
			typeElement.setAttribute("name", type.getName());
			String red = Integer.toHexString((int) (type.getColor().getRed()*255));
			String green = Integer.toHexString((int) (type.getColor().getGreen()*255));
			String blue = Integer.toHexString((int) (type.getColor().getBlue()*255));
			typeElement.setAttribute("color", "#"+red+green+blue);
			noteTypes.getParentNode().appendChild(typeElement);
		}
		//TODO Finish this

		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		Result output = new StreamResult(this);
		Source input = new DOMSource(doc);

		transformer.transform(input, output);
	}
	
	

}
