package survey;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class SurveyText {
    public static String[] getSurveyText(String gameName) {
	String[] result = null;
	try {
	    File fXmlFile = new File("tutorial/surveyData.xml");
	    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    Document doc = (Document) dBuilder.parse(fXmlFile);

	    String temp = doc.getElementsByTagName(gameName).item(0).getTextContent();
	    result = temp.split(",");
	    for(int i=0; i<result.length; i++) {
		result[i] = result[i].trim();
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return result;
    }
}
