package com.turbo.build.graph;

import java.util.Iterator;
import java.util.Map;

import com.turbo.build.util.Clazz;
import com.turbo.build.util.Jar;


public class Vertex {
	String name;
	Vertex parent;
	
	Jar    jar;    // Save the jar to get extra info
	
	public void setParent(Vertex parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public Vertex getParent() {
		return parent;
	}

	public Jar getJar() {
		return jar;
	}

	public Vertex(Jar jar) {
		this.name = jar.getFullname();
		this.jar  = jar;
	}

	@Override
	public boolean equals(Object obj) {
		// Override Auto-generated method stub
		if(obj == null || getClass() != obj.getClass()) {
			return false;
		}
		
		if(this == obj) {
			return true;
		}
		
		Vertex vertex = (Vertex)obj;
		if (name == null && vertex.name != null || name != null
				&& !name.equals(vertex.name)) {
			return false;
		}
		
		return true;
	}

	@Override
	public int hashCode() {
		// Override Auto-generated method stub
		final int prime = 137;
		int result = 1;
		result = prime * result + ((jar == null) ? 0 : jar.hashCode());
		
		return result;
	}

	/**
	 * Compare two vertexes
	 * @param vertex
	 * objective vertex
	 * @return
	 * -1: this --> obj, obj.jar contains this.jar;
	 *  0: this == obj , obj,jar is same as this.jar;
	 *  1: this <-- obj, this.jar contains obj.jar;
	 *  4: this and obj are incompatible
	 */
	public int comapreTo(Vertex vertex) {
		Jar src = jar;
		Jar obj = vertex.getJar();
		
		Map<String, Clazz> srcClazzes = src.getClazzes();
		Map<String, Clazz> objClazzes = obj.getClazzes();
		
		boolean srcContainsObj = true;
		boolean objContainsSrc = true;
		
		Iterator<String> iter = objClazzes.keySet().iterator();
		while(iter.hasNext()) {
			String key = iter.next();
			if(srcClazzes.containsKey(key)) {
				Clazz srcClazz = srcClazzes.get(key);
				Clazz objClazz = objClazzes.get(key);
				
				srcClazz.comapreTo(objClazz);
			}else {
				srcContainsObj = false;
				break;
			}
		}
		
		if(srcContainsObj && objContainsSrc) {
			return 0;
		}
		
		return 4;
	}
}
