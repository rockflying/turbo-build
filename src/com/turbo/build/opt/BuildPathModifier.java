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
		
		Set<String> keys = jarMap.keySet();
		for (Iterator<String> iter = keys.iterator(); iter.hasNext();) {
			
			List<Jar> list = jarMap.get(iter.next());
			for (Iterator<Jar> iter2 = list.iterator(); iter2.hasNext();) {
				Element node = iter2.next().getElement();
				root.removeContent(node);
				root.addContent(node.detach());
			}
		}
		
		//output the optimized class path
		Console.println("Optimized classpath:", new Color(null, 255, 0, 0));
		
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
	}
}