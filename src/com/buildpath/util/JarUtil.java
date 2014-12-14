package com.buildpath.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
			str += jar.name + "\t" + jar.version + "\t" + jar.absolute + "\n";
		}
		return str;
	}
	
	public static void main(String[] args) {
		ClassPathParser parser = new ClassPathParser(".classpath");

		parser.extractJars();
		
		JarUtil jutil = new JarUtil(parser.getEntries());
		
		System.out.println(jutil.toString());
	}
}