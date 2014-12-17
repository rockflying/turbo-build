package com.turbo.build.opt;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import com.turbo.build.util.ClasspathEntry;

/**
 * Parse the classpath and get jar list
 *
 */
public class ClassPathParser {
	
	private String buildPath = ".classpath";
	private static final String ENTRY_KIND     = "kind";
	private static final String ENTRY_PATH     = "path";
	private static final String ENTRY_EXPORTED = "exported";
	
	private Document document;
	
//	private List<String> jars  = new ArrayList<String>();
	private List<ClasspathEntry> entries = new ArrayList<ClasspathEntry>();

	public ClassPathParser(String buildPath) {
		this.buildPath = buildPath;
	}
	
	public ClassPathParser() {
	}

	public List<ClasspathEntry> getEntries() {
		return entries;
	}
	
	public void extractJars() {
		parseClassPath(buildPath, null, false);
	}
	
	public Document getDocument() {
		return document;
	}

	private void parseClassPath(String classpath, Element node, boolean exported) {
		
		SAXBuilder builder = new SAXBuilder();
		
		try {
			Document doc = builder.build(classpath);
			
			if(!exported) {
				this.document = doc;
			}
			
			List<Element> list = doc.getRootElement().getChildren();

//			int index = 0;
			for (Iterator<Element> iter = list.iterator(); iter.hasNext();) {
				Element curNode = iter.next();
				String kind = curNode.getAttributeValue(ENTRY_KIND);
				String path = curNode.getAttributeValue(ENTRY_PATH);
				String expt = curNode.getAttributeValue(ENTRY_EXPORTED);

				ClasspathEntry entry = new ClasspathEntry();
				if (kind.equals("lib")) {
					if (exported) { // only consider those exported jars from
									// other class path
						if (expt != null && expt.equals("true")) {
							
							if (new File(path).isAbsolute()) {
								entry.path = path;
//								jars.add(path);
							} else {
								File file = new File(classpath);
								entry.path = file.getParent() + File.separator + path;
//								jars.add(key);
							}
						}
						
					} else { //add jars
						if(path.startsWith("/")) { //depends on jars in other projects
							String workspace = System.getProperty("user.dir") + File.separator + "..";
							String jarPath = workspace + path;
							File file = new File(jarPath);

							if (file.exists()) {
								entry.path = jarPath;
//								jars.add(jarPath);
							}
						} else {
							entry.path = path;
						}
					}

					if (node != null) {
						entry.element = node;
					} else {
						entry.element = curNode;
					}
//					entry.index = index;
					
					if(entry.path != null) {
						entries.add(entry);
					}

				} else if(kind.equals("src") && path.startsWith("/")) {//depends on other projects
					if (node != null) {
						getExtraJars(path, node);
					} else {
						getExtraJars(path, curNode);
					}					
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	
	private void getExtraJars(String proj, Element node) {
		// TODO using the eclipse plug-in to get the workspace
		// get the workspace directory
		String workspace = System.getProperty("user.dir") + File.separator + "..";

		String classPath = workspace + proj + File.separator + ".classpath";
		File file = new File(classPath);

		if (file.exists()) {
			parseClassPath(classPath, node, true);
		}
	}

	public static void main(String[] args) {

		ClassPathParser parser = new ClassPathParser(".classpath");

		parser.extractJars();

		for (Iterator<ClasspathEntry> iter = parser.getEntries().iterator(); iter.hasNext();) {
			ClasspathEntry key = iter.next();
			System.out.println(key.path);
		}
	}
}