package com.buildpath.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.buildpath.opt.ClassPathParser;

public class JarUtil {
	private List<Jar> jars = new ArrayList<Jar>();

	public JarUtil(List<ClasspathEntry> entries) {
		// constructor stub
		Iterator<ClasspathEntry> iter = entries.iterator();
		while (iter.hasNext()) {
			ClasspathEntry entry = iter.next();
			jars.add(new Jar(entry));
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String str = "";
		Iterator<Jar> iter = jars.iterator();
		while(iter.hasNext()) {
			Jar jar = iter.next();
			str += jar.name + "\t" + jar.version + "\n";
		}
		return str;
	}
	
	public void resolveConflict() {
		Map<String, Jar> map = new HashMap<String, Jar>();
		
		Iterator<Jar> iter = jars.iterator();
		while(iter.hasNext()) {
			Jar currentJar = iter.next();
			if(!map.containsKey(currentJar.name)) {
				map.put(currentJar.name, currentJar);
				continue;
			}
			
			Jar jar = map.get(currentJar.name);
			if(jar.compareTo(currentJar) < 0) {
				
			}
		}
	}
	public static void main(String[] args) {
		ClassPathParser parser = new ClassPathParser(".classpath");

		parser.extractJars();
		
		JarUtil jutil = new JarUtil(parser.getEntries());
		
		System.out.println(jutil.toString());
	}
}