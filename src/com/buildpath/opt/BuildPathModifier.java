package com.buildpath.opt;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
		for(Iterator<String>iter = keys.iterator(); iter.hasNext();) {
			String key = iter.next();
			List<Jar> list = jarMap.get(key);
			System.out.println(list);
		}
	}

}
