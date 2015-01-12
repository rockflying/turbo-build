package topsort;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class TopSort {
	enum Color {
		WHITE, GRAY, BLACK
	}

	static class Vertex {
		private String name;
		private Color color;
		private Vertex parent;
		private int discover;
		private int finish;

		public Vertex(String name) {
			this.name = name;
			this.color = Color.WHITE;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Color getColor() {
			return color;
		}

		public void setColor(Color color) {
			this.color = color;
		}

		public Vertex getParent() {
			return parent;
		}

		public void setParent(Vertex parent) {
			this.parent = parent;
		}

		public int getDiscover() {
			return discover;
		}

		public void setDiscover(int discover) {
			this.discover = discover;
		}

		public int getFinish() {
			return finish;
		}

		public void setFinish(int finish) {
			this.finish = finish;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Vertex other = (Vertex) obj;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}
	}

	static class Graph {
		private Set<Vertex> vertexSet = new HashSet<Vertex>();
		// 相邻的节点
		private Map<Vertex, Vertex[]> adjacencys = new HashMap<Vertex, Vertex[]>();

		public Set<Vertex> getVertexSet() {
			return vertexSet;
		}

		public void setVertexSet(Set<Vertex> vertexSet) {
			this.vertexSet = vertexSet;
		}

		public Map<Vertex, Vertex[]> getAdjacencys() {
			return adjacencys;
		}

		public void setAdjacencys(Map<Vertex, Vertex[]> adjacencys) {
			this.adjacencys = adjacencys;
		}
	}

	static class TimeRecorder {
		private int time = 0;

		public int getTime() {
			return time;
		}

		public void setTime(int time) {
			this.time = time;
		}
	}

	public static Vertex[] topologicalSort(Graph graph) {
		Set<Vertex> vertexSet = graph.getVertexSet();
		if (vertexSet.size() < 2) {
			return vertexSet.toArray(new Vertex[0]);
		}

		LinkedList<Vertex> sortedList = new LinkedList<Vertex>();
		TimeRecorder recorder = new TimeRecorder();

		for (Vertex vertex : vertexSet) {
			if (vertex.color == Color.WHITE) {
				visitVertex(graph, vertex, recorder, sortedList);
			}
		}

		return sortedList.toArray(new Vertex[0]);
	}

	/**
	 * 深度优先搜索(Depth First Search)
	 */
	public static void visitVertex(Graph graph, Vertex vertex,
			TimeRecorder recorder, LinkedList<Vertex> sortedList) {
		recorder.time += 1;
		vertex.color = Color.GRAY;
		vertex.discover = recorder.time;

		Map<Vertex, Vertex[]> edgeMap = graph.getAdjacencys();
		Vertex[] adjacencys = edgeMap.get(vertex);
		if (adjacencys != null && adjacencys.length > 0) {
			for (Vertex adjacency : adjacencys) {
				if (adjacency.color == Color.WHITE) {
					adjacency.parent = vertex;
					visitVertex(graph, adjacency, recorder, sortedList);
				}
			}
		}

		recorder.time += 1;
		vertex.color = Color.BLACK;
		vertex.finish = recorder.time;
		sortedList.addLast(vertex);
	}

	public static void main(String[] args) {
		Graph graph = new Graph();
		Set<Vertex> vertexSet = graph.getVertexSet();
		Map<Vertex, Vertex[]> edgeMap = graph.getAdjacencys();

		Vertex twoVertex = new Vertex("2");
		Vertex threeVertex = new Vertex("3");
		Vertex fiveVertex = new Vertex("5");
		Vertex sevenVertex = new Vertex("7");
		Vertex eightVertex = new Vertex("8");
		Vertex nineVertex = new Vertex("9");
		Vertex tenVertex = new Vertex("10");
		Vertex elevenVertex = new Vertex("11");

		vertexSet.add(twoVertex);
		vertexSet.add(threeVertex);
		vertexSet.add(fiveVertex);
		vertexSet.add(sevenVertex);
		vertexSet.add(eightVertex);
		vertexSet.add(nineVertex);
		vertexSet.add(tenVertex);
		vertexSet.add(elevenVertex);

		edgeMap.put(twoVertex, new Vertex[] { elevenVertex });
		edgeMap.put(nineVertex, new Vertex[] { elevenVertex, eightVertex });
		edgeMap.put(tenVertex, new Vertex[] { elevenVertex, threeVertex });
		edgeMap.put(elevenVertex, new Vertex[] { sevenVertex, fiveVertex });
		edgeMap.put(eightVertex, new Vertex[] { sevenVertex, threeVertex });

		Vertex[] sortedVertexs = topologicalSort(graph);
		printVertex(sortedVertexs);
	}

	public static void printVertex(Vertex[] Vertexs) {
		for (Vertex vertex : Vertexs) {
			System.out.println(vertex.getName() + "  discover time:"
					+ vertex.getDiscover() + "  finish time:"
					+ vertex.getFinish());
		}
	}
}
