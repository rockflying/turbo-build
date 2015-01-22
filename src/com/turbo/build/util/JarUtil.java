package com.turbo.build.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
		
		Iterator<Jar> iter = jars.iterator();  //all jars in the build path
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
				if (jar.compareTo(currentJar) != 0) {
					jarList.add(currentJar);
					conflictJars.put(currentJar.name, jarList);
				}
			} else {
				boolean exists = false;
				for (Iterator<Jar> iter2 = list.iterator(); iter2.hasNext();) {
					if (iter2.next().compareTo(currentJar) == 0) {
						exists = true;
						break;
					}
				}
				if (!exists) {
					list.add(currentJar);
				}
			}
		}
		
		//
		Iterator<Map.Entry<String, List<Jar>>> it = conflictJars.entrySet().iterator();
		
		while (it.hasNext()) {
			Entry<String, List<Jar>> entry = it.next();
			if (entry.getValue().size() <= 1) {
				it.remove();
			}
		}
		
		sortConflicJars();
	}
	
	private void sortConflicJars() {
		
		Set<String> keys = conflictJars.keySet();
		for (Iterator<String> iter = keys.iterator(); iter.hasNext();) {
			String key = iter.next();
			List<Jar> list = conflictJars.get(key);
			// System.out.println(list);
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
	
	public List<Jar[]> findConflictJars() {
		List<Jar[]> confJars = new ArrayList<Jar[]>();

		for (int i = 0; i < jars.size(); ++i) {
			Jar src = jars.get(i);
			for (int j = i + 1; j < jars.size(); ++j) {
				Jar obj = jars.get(j);
				if (src.compareTo(obj) == 0 || src.compareTo(obj) == 4) {
					continue;
				}

				confJars.add(new Jar[] { src, obj });
			}
		}

		return confJars;
	}
	
	@SuppressWarnings("unused")
	// TODO delete if not needed
	private void addUniqJar(ArrayList<Jar> jarList, Jar jar) {
		boolean exists = false;
		for(Jar j : jarList) {
			if(j.compareTo(jar) == 0) {
				exists = true;
			}
		}
		
		if(!exists) {
			jarList.add(jar);
		}
	}
}