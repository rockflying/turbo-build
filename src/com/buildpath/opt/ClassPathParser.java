package com.buildpath.opt;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

/**
 * Parse the classpath and get jar list
 *
 */
public class ClassPathParser {
	
	private String buildPath = ".classpath";
	private static final String ENTRY_KIND     = "kind";
	private static final String ENTRY_PATH     = "path";
	private static final String ENTRY_EXPORTED = "exported";
	
	private List<String> jars  = new ArrayList<String>();
	
	public ClassPathParser(String buildPath) {
		this.buildPath = buildPath;
	}
	
	public ClassPathParser() {
	}

	public List<String> getJars() {
		return jars;
	}

	public void extractJars() {
		parseClassPath(buildPath, false);
	}
	
	private void parseClassPath(String classpath, boolean exported) {
		
		SAXBuilder builder = new SAXBuilder();
		
		try {
			Document doc = builder.build(classpath);
			List<Element> list = doc.getRootElement().getChildren();

			for (Iterator<Element> iter = list.iterator(); iter.hasNext();) {
				Element node = iter.next();
				String kind = node.getAttributeValue(ENTRY_KIND);
				String path = node.getAttributeValue(ENTRY_PATH);
				String expt = node.getAttributeValue(ENTRY_EXPORTED);

				if (kind.equals("lib")) {
					if (exported) { // only consider those exported jars from
									// other class path
						if (expt != null && expt.equals("true")) {
							File file = new File(classpath);
							jars.add(file.getParent()+"/"+path);
						}
					} else { //add jars
						jars.add(path);
					}
				} else if(kind.equals("src") && path.startsWith("/")) {//depends on other projects
					getExtraJars(path);
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	
	private void getExtraJars(String proj) {
		// TODO using the eclipse plug-in to get the workspace
		// get the workspace directory
		String workspace = System.getProperty("user.dir") + "/..";

		File file = new File(workspace + proj + "/.classpath");

		if (file.exists()) {
			parseClassPath(workspace + proj + "/.classpath", true);
		}

	}

/*	public static void main(String[] args) {

		ClassPathParser parser = new ClassPathParser("classpath");

		parser.extractJars();

		for (Iterator<String> iter = parser.getJars().iterator(); iter
				.hasNext();) {
			System.out.println(iter.next());
		}
	}*/
}
