package org.moka.structure.graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/**
 * 그래프 BFS, DFS 구현
 * BFS (Breadth-First Search) : 넓이 우선 탐색
 * DFS (Depth-First Search) : 깊이 우선 탐색
 * */

public class GraphSearch {
	private static Graph createDummyData() {
		Graph graph = new Graph();
		graph.addNode(17);
		graph.addNode(6);
		graph.addNode(8);
		graph.addNode(3);
		graph.addNode(12);
		graph.addNode(11);
		graph.addNode(13);
		graph.addNode(7);
		graph.addNode(2);
		graph.addNode(5);
		graph.addNode(19);
		graph.addNode(4);

		graph.addEdge(2, 6);
		graph.addEdge(2, 8);
		graph.addEdge(2, 3);
		graph.addEdge(6, 5);
		graph.addEdge(3, 12);
		graph.addEdge(3, 11);
		graph.addEdge(12, 4);
		graph.addEdge(11, 19);
		graph.addEdge(5, 7);
		graph.addEdge(19, 13);
		graph.addEdge(17, 3);
		graph.addEdge(4, 8);
		graph.addEdge(4, 7);
		return graph;
	}

	public static void main(String[] args) {
		Graph data = createDummyData();
		data.printDot();
		// 깊이 우선 탐색
		dfs(data.get(2));
		data.reset();
		// 넓이 우선 탐색
		bfs(data.get(2));
		data.reset();

		// DFS 재귀 형식
		System.out.println("====================");
		dfsRecursion(data.get(2), 0);
	}

	/**
	 * DFS (Depth-First Search) : 깊이 우선 탐색
	 * Stack (Last In first Out) 을 활용
	 *  -> LIFO 나중에 들어간것이 맨 먼저 나온다.
	 *  깊이 우선 탐색이므로, 깊숙히 들어간다음 leaf 까지 stack 을 쌓고
	 *  leaf 부터 처리한다.
	 * */
	private static void dfs(Node firstNode) {
		// 1. 스택 생성
		Stack<Node> stack = new Stack<>();

		// 2. 첫번째 노드 삽입(아무 노드)
		stack.add(firstNode);
		firstNode.mark();

		System.out.println();

		while (!stack.empty()) {
			// Loop1. 스택에서 꺼내고
			Node pick = stack.pop();
			// Loop2. 출력하고
			pick.print();
			// Loop3. 자식노드 (인접노드)를 Stack 에 넣는다.
			for(Node node : pick.neighbors){
				// 방문하지 않은 노드 일때
				if(!node.isVisited){
					// 스택에 넣고
					stack.add(node);
					// 방문 표기를 한다.
					node.mark();
				}
			}
		}

		System.out.println();
	} // end dfs
	/**
	 * BFS (Breadth-First Search) : 넓이 우선 탐색
	 * Queue (First In First Out) 를 활용
	 *  -> FIFO 먼저 들어온게 먼저 나간다.
	 * */
	private static void bfs(Node firstNode){
		// 1. Queue 큐 생성
		Queue<Node> queue = new LinkedList<>();

		// 2. 첫번째 노드 삽입
		queue.add(firstNode);
		firstNode.mark();

		int level = 0;
		System.out.println();

		while (!queue.isEmpty()){
			//int levelSize = queue.size();
			//System.out.println("Level " + level + ":");

			//for(int i=0;i<levelSize;i++){
				// Loop1. 노드를 꺼내고
				Node pick = queue.poll();
				// Loop2. 자기자신을 출력한다.
				pick.print();
				// Loop3. 자식(인접노드)을 Queue 에 넣고
				for(Node node : pick.neighbors){
					// 방문하지 않은 노드 일때
					if(!node.isVisited){
						// queue 에 넣고
						queue.add(node);
						// 방문을 표시한다.
						node.mark();
					}
				}
			//}

			System.out.println();
			//level++;
		}
		System.out.println();
	} // end bfs
	private static void dfsRecursion(Node node,int depth){
		// node가 없거나 방문했을때 return 한다.
		if(node == null || node.isVisited) return;

		printSpace(depth);
		node.print();
		System.out.print(" (depth:"+depth+")");
		System.out.println();
		node.mark();
		for(Node child : node.neighbors){
			dfsRecursion(child,depth + 1);
		}
	}
	private static void printSpace(int depth){
		for(int i=0;i<depth;i++){
			System.out.print(" ");
		}
	}

}

class Graph {
	Map<Integer, Node> nodeMap;

	public Graph() {
		nodeMap = new HashMap<>();
	}

	public void addNode(int value) {
		nodeMap.computeIfAbsent(value, Node::new);
	}

	public void addEdge(int from, int to) {
		Node fromNode = this.get(from);
		Node toNode = this.get(to);
		if (fromNode != null && toNode != null) {
			fromNode.neighbors.add(toNode);
		}
	}

	public Node get(int value) {
		return nodeMap.get(value);
	}

	public void reset(){
		for(Node node : nodeMap.values()){
			node.isVisited = false;
		}
	}

	public void printDot() {
		System.out.println("digraph G {");
		for (Map.Entry<Integer, Node> entry : nodeMap.entrySet()) {
			Node node = entry.getValue();
			for (Node neighbor : node.neighbors) {
				System.out.println("  " + node.value + " -> " + neighbor.value + ";");
			}
		}
		System.out.println("}");
		System.out.println();
		System.out.println("Copy to https://dreampuf.github.io/GraphvizOnline");
	}
}

class Node {
	int value;
	boolean isVisited = false;
	LinkedList<Node> neighbors;

	public Node(int value) {
		this.value = value;
		this.neighbors = new LinkedList<>();
	}

	public void print() {
		System.out.print(this.value + " ");
	}

	public void mark() {
		this.isVisited = true;
	}
}

