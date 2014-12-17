package com.turbo.build.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JarUtil {
	private List<Jar> jars = new ArrayList<Jar>();
	private Map<String, List<Jar>> conflictJars = new HashMap<String, List<Jar>>();

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
		Map<String, Jar> jarMap = new HashMap<String, Jar>();
		
		Iterator<Jar> iter = jars.iterator();
		while(iter.hasNext()) {
			Jar currentJar = iter.next();
			if(!jarMap.containsKey(currentJar.name)) {
				jarMap.put(currentJar.name, currentJar);
				continue;
			}
			
			List<Jar> list = conflictJars.get(currentJar.name);
			if (list == null) {
				Jar jar = jarMap.get(currentJar.name);
				List<Jar> jarList = new ArrayList<Jar>();
				jarList.add(jar);
				jarList.add(currentJar);
				conflictJars.put(currentJar.name, jarList);
			} else {
				list.add(currentJar);
			}
		}
		
		sortConflicJars();
	}
	
	private void sortConflicJars() {
		
		Set<String> keys = conflictJars.keySet();
		for(Iterator<String>iter = keys.iterator(); iter.hasNext();) {
			String key = iter.next();
			List<Jar> list = conflictJars.get(key);
//			System.out.println(list);
			Collections.sort(list, new Comparator<Jar>() {
				@Override
				public int compare(Jar jar1, Jar jar2) {
					return jar1.compareTo(jar2);
				}
			});
		}
	}
	
	public List<Jar> getJars() {
		return jars;
	}
	
	public Map<String, List<Jar>> getConflictJars() {
		return conflictJars;
	}
}