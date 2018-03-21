package com.company.demo.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.company.demo.core.BranchAdd;

public class Utility {
	final static Logger logger = Logger.getLogger(Utility.class);
	public static String loc = "src/main/resources/config/config.xml";

	private static String control_properties_file = "src/main/resources/config/controls.properties";
	private static String messages_properties_file = "src/main/resources/config/messages.properties";

	private static Properties properties = null;
	public static String app_url = configReader("Url");

	public void init() {
		loadProperties();
	}

	public Utility() {
		init();
	}

	private static void loadProperties() {
		properties = new Properties();
		try (FileInputStream fis = new FileInputStream(messages_properties_file)) {
			properties.load(fis);
		} catch (IOException e) {
			logger.error(e);
		}
		try (FileInputStream fis = new FileInputStream(control_properties_file)) {
			properties.load(fis);
		} catch (IOException e) {
			logger.error(e);
		}

	}

	// To read Locators from Property file
	public static String getControls(String key) throws IOException {
		if (null == properties) {
			loadProperties();
		}
		return properties.getProperty(key);
	}

	// To read messages from property file
	public static String getMessages(final String key) {
		if (null == properties) {
			System.out.println("loading properties for key = " + key);
			loadProperties();
		}
		return properties.getProperty(key);
	}

	// To get the test Data by entering tag name
	public static String getTestData(String tagName, String name, String xmlPath) {
		String value = null;
		File file = new File(xmlPath);
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();

			Element el = doc.getDocumentElement();

			NodeList nlist = el.getElementsByTagName(tagName);
			for (int i = 0; i < nlist.getLength(); i++) {

				Node node = nlist.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					el = (Element) node;
					value = el.getElementsByTagName(name).item(0).getTextContent();
				}
			}
			System.out.println("TestData value is " + value);

		} catch (Exception e) {
			System.out.println("No test data found" + e);
		}
		// System.out.println("TestData value is1 " + value);

		return value;
	}

	// To Add test data by entering tagname and Node
	public static List<String> getTestDataAdd(String tagName, String name, String xmlPath) {
		String value = null;
		List<String> listStrings = new LinkedList<String>();
		File file = new File(xmlPath);
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();

			Element el = doc.getDocumentElement();

			NodeList nodes = el.getElementsByTagName(tagName);
			for (int i = 0; i < nodes.getLength(); i++) {

				Node node = nodes.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					el = (Element) node;
					value = el.getElementsByTagName(name).item(0).getTextContent();
					listStrings.add(value);
				}

			}
		} catch (Exception e) {
			logger.error("No test data found" + e);
		}
		return listStrings;
	}

	// To read Url from Config file
	public static String configReader(String tagName) {

		File file = new File(loc);
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();

			Element el = doc.getDocumentElement();
			String value = el.getElementsByTagName(tagName).item(0).getTextContent();
			logger.info("value for tagName " + tagName + " is " + value + " ");
			return value;
		} catch (ParserConfigurationException | SAXException | IOException e) {
			logger.error(e);
		}
		return "";

	}

	public static Set<BranchAdd> getTestDataAdd(String tagName, String xmlPath) {
		Set<BranchAdd> listStrings = new HashSet<BranchAdd>();
		File file = new File(xmlPath);
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();

			Element el = doc.getDocumentElement();

			NodeList nodes = el.getElementsByTagName(tagName);
			for (int i = 0; i < nodes.getLength(); i++) {

				Node node = nodes.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					NodeList childNodes = node.getChildNodes();
					BranchAdd add = new BranchAdd();
					for (int j = 0; j < childNodes.getLength(); j++) {
						Node childNode = childNodes.item(j);
						if (childNode.getNodeType() == Node.ELEMENT_NODE) {
							if (childNode.getNodeName().equals("name")) {
								add.setName(childNode.getTextContent());
							} else if (childNode.getNodeName().equals("code")) {
								add.setCode(childNode.getTextContent());
							}
						}
					}
					listStrings.add(add);
				}
			}
		} catch (Exception e) {
			System.out.println("No test data found" + e);
		}
		return listStrings;
	}

}
