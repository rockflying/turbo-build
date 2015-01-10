package com.turbo.build.opt;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import com.turbo.build.graph.Graph;
import com.turbo.build.util.ClasspathEntry;
import com.turbo.build.util.Console;
import com.turbo.build.util.Jar;
import com.turbo.build.util.JarUtil;

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
							String proj = path.substring(1, path.indexOf("/", 1));
							String projPath = getProjectLocation(proj);
							String jarPath = projPath + path.substring(path.indexOf("/", 1));
							File file = new File(jarPath);

							if (file.exists()) {
								entry.path = jarPath;
//								jars.add(jarPath);
							}
						} else {
							if (new File(path).isAbsolute()) {
								entry.path = path;
							} else {
								File file = new File(classpath);
								entry.path = file.getParent() + File.separator + path;
							}
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
			Console.println(ex.toString());
			ex.printStackTrace();
		}
	}

	
	private void getExtraJars(String path, Element node) {
		// TODO using the eclipse plug-in to get the workspace
		// get the workspace directory
		String proj = path.substring(1);
		
		String workspace = getProjectLocation(proj) + File.separator;

		String classPath = workspace + ".classpath";
		File file = new File(classPath);

		if (file.exists()) {
			parseClassPath(classPath, node, true);
		}
	}

	private String getProjectLocation(String proj) {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		return root.getProject(proj).getLocation().toString();
	}

	public static void main(String[] args) {

		ClassPathParser parser = new ClassPathParser("test-cases\\case2\\App\\.classpath");
//		ClassPathParser parser = new ClassPathParser("F:\\workspace\\soot\\.classpath");

		parser.extractJars();

//		for (Iterator<ClasspathEntry> iter = parser.getEntries().iterator(); iter.hasNext();) {
//			ClasspathEntry key = iter.next();
//			System.out.println(key.path);
//		}
		
		JarUtil util = new JarUtil(parser.getEntries());
//		System.out.println(util);
		
		util.resolveConflict();
		
		Map<String, List<Jar>> jarMap = util.getConflictJars();
		
		Set<String> keys = jarMap.keySet();
		
		System.out.println(keys.size());
		
		for (Iterator<String> iter = keys.iterator(); iter.hasNext();) {
			String name = iter.next();
			System.out.println(">>> jar: " + name + " <<<");
			List<Jar> list = jarMap.get(name);
			System.out.println(list);
		}
		
		Graph g = new Graph(util.findConflictJars());
		System.out.println(g.getVertexSet().size());
		System.out.println(g);
		
		List<ClasspathEntry> entries = parser.getEntries();
		for(ClasspathEntry entry : entries) {
			System.out.println(entry.element.getAttributeValue("path"));
		}
	}
}