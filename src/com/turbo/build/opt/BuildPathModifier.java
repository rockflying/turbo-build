package com.turbo.build.opt;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.swt.graphics.Color;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.turbo.build.util.Console;
import com.turbo.build.util.Jar;
import com.turbo.build.util.JarUtil;

public class BuildPathModifier {
	
	private String classpath;

	public BuildPathModifier(String classpath) {
		this.classpath = classpath;
	}

	public void optimizeBuildPath() {
		
		ClassPathParser parser = new ClassPathParser(classpath);
		
		parser.extractJars();
		
		JarUtil jutil = new JarUtil(parser.getEntries());
		
		jutil.resolveConflict();
		
		Map<String, List<Jar>> jarMap = jutil.getConflictJars();
		
		Document doc = parser.getDocument();
		Element root = doc.getRootElement();
		
		//output the original class path
		Console.println("Original classpath:\n", new Color(null, 255, 0, 0));
		
		Format format = Format.getCompactFormat();
		format.setEncoding("UTF-8");
		format.setIndent("    ");
		XMLOutputter out=new XMLOutputter(format);
		try {
			out.output(doc, Console.getConsole().newMessageStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//optimize the build path
		Set<String> keys = jarMap.keySet();
		
		if(keys.size() > 0) {
			Console.println("\nJars that may conflict:\n", new Color(null, 255, 0, 0));
		} else {
			Console.println("\nThere are not conflict jars.\n", new Color(null, 255, 0, 0));
		}
		
		for (Iterator<String> iter = keys.iterator(); iter.hasNext();) {
			String name = iter.next();
			Console.println(">>> jar: " + name + " <<<", new Color(null, 255, 0, 0));
			List<Jar> list = jarMap.get(name);
			for (Iterator<Jar> iter2 = list.iterator(); iter2.hasNext();) {
				Jar jar = iter2.next();
				Element node = jar.getElement();
				
				Console.println("    "+node.getAttributeValue("path"), new Color(null, 255, 0, 0));
				Console.println("        "+ jar.getPath());
				
				root.removeContent(node);
				root.addContent(node.detach());
			}
		}
		
		//output the optimized class path
		Console.println("\nOptimized classpath:\n", new Color(null, 255, 0, 0));
		
		
		try {
			out.output(doc, Console.getConsole().newMessageStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}