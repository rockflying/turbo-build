package com.turbo.build.graph;

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

}
