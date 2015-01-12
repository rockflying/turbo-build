package com.turbo.build.test;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.turbo.build.graph.Graph;
import com.turbo.build.opt.ClassPathParser;
import com.turbo.build.util.Jar;
import com.turbo.build.util.JarUtil;

public class Test {
	public static void main(String[] args) {

		ClassPathParser parser = new ClassPathParser("test-cases\\case3\\App\\.classpath");
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
		
		for (Iterator<String> iter = keys.iterator(); iter.hasNext();) {
			String name = iter.next();
			System.out.println(">>> jar: " + name + " <<<");
//			List<Jar> list = jarMap.get(name);
//			System.out.println(list);
		}
		
		Graph g = new Graph(util.findConflictJars());
		System.out.println(g);
//		
//		List<ClasspathEntry> entries = parser.getEntries();
//		for(ClasspathEntry entry : entries) {
//			System.out.println(entry.element.getAttributeValue("path"));
//		}
	}
}
