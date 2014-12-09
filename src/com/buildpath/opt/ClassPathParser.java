package com.buildpath.opt;

import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public class ClassPathParser {
	
	private String buildPath = ".classpath";
	private static final String ENTRY_KIND     = "kind";
	private static final String ENTRY_PATH     = "path";
	private static final String ENTRY_EXPORTED = "exported";
	
	private List<String> libs;

	public void parse() {
		SAXBuilder builder = new SAXBuilder();

		try {
			Document doc = builder.build(buildPath);
			List<Element> list = doc.getRootElement().getChildren();

			for (Iterator<Element> iter = list.iterator(); iter.hasNext();) {
				Element node = iter.next();
				System.out.println(node.getAttributeValue(ENTRY_KIND));
				System.out.println(node.getAttributeValue(ENTRY_PATH));
				System.out.println(node.getAttributeValue(ENTRY_EXPORTED));
			}

		} catch (Exception ex) {

			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {

		ClassPathParser parser = new ClassPathParser();
		parser.parse();
	}
}
