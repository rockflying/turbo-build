package com.buildpath.opt;

import java.util.ArrayList;
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
	
	private List<String> jars = new ArrayList<String>();

	public void extractJars(String classpath) {
		
		SAXBuilder builder = new SAXBuilder();
		
		List<String> projs = new ArrayList<String>();

		try {
			Document doc = builder.build(classpath);
			List<Element> list = doc.getRootElement().getChildren();

			for (Iterator<Element> iter = list.iterator(); iter.hasNext();) {
				Element node = iter.next();
				String kind = node.getAttributeValue(ENTRY_KIND);
				String path = node.getAttributeValue(ENTRY_PATH);
//				String expt = node.getAttributeValue(ENTRY_EXPORTED);
				if(kind.equals("output")) {
					System.out.println("Skip output");
					continue;
				} else if (kind.equals("src") && path.equals("src")) {
					System.out.println("Skip src in current project");
					continue;
				} else if(kind.equals("con")) {
					System.out.println("Skip con");
					continue;
				} else if (path.startsWith("/")) {
					projs.add(path);
				} else {
					jars.add(path);
				}
			}

		} catch (Exception ex) {

			ex.printStackTrace();
		}
		
		getExtraJars(projs);
	}

	public List<String> getJars() {
		return jars;
	}

	private void getExtraJars(List<String> projs){
		for(Iterator<String> iter = projs.iterator(); iter.hasNext();) {
			String proj = iter.next();
			System.out.println(proj);
		}
	}
	public static void main(String[] args) {

		ClassPathParser parser = new ClassPathParser();
		parser.extractJars("../StaticAnalysis/.classpath");
		System.out.println(parser.getJars());
//		String workspace = System.getProperty("user.dir")+"/../";

	}
}
