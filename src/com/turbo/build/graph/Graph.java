package com.turbo.build.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph {
	private Set<Vertex> vertexSet = new HashSet<Vertex>();
	
	private Map<Vertex, ArrayList<Vertex>> edgeMap = new HashMap<Vertex, ArrayList<Vertex>>();
	
	public Graph() {
		
	}

	public Set<Vertex> getVertexSet() {
		return vertexSet;
	}

	public Map<Vertex, ArrayList<Vertex>> getEdgeMap() {
		return edgeMap;
	}
}
