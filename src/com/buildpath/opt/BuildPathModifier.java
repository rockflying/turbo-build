package com.buildpath.opt;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jdom2.Element;

import com.buildpath.util.Jar;
import com.buildpath.util.JarUtil;

public class BuildPathModifier {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClassPathParser parser = new ClassPathParser(".classpath");

		parser.extractJars();
		
		JarUtil jutil = new JarUtil(parser.getEntries());
		
		jutil.resolveConflict();
		
		Map<String, List<Jar>> jarMap = jutil.getConflictJars();

		Set<String> keys = jarMap.keySet();
		Element node = null;
		for(Iterator<String>iter = keys.iterator(); iter.hasNext();) {
			String key = iter.next();
			List<Jar> list = jarMap.get(key);
			node = list.get(1).getElement();
			System.out.println(list);
		}
		
		List<Element> list = parser.getDocument().getRootElement().getChildren();
		System.out.println(list.contains(node));
	}

}
