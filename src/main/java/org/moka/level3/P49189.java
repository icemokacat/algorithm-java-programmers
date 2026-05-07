package org.moka.level3;

// https://school.programmers.co.kr/learn/courses/30/lessons/49189

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;


/**
 * Level 3
 * 가장 먼 노드
 * 정답률 51%
 * */
public class P49189 {
	/*
	 * n개의 노드가 있는 그래프가 있습니다.
	 * 각 노드는 1부터 n까지 번호가 적혀있습니다.
	 * 1번 노드에서 가장 멀리 떨어진 노드의 갯수를 구하려고 합니다.
	 * 가장 멀리 떨어진 노드란 최단경로로 이동했을 때 간선의 개수가 가장 많은 노드들을 의미합니다.
	 * 노드의 개수 n, 간선에 대한 정보가 담긴 2차원 배열 vertex가 매개변수로 주어질 때,
	 * 1번 노드로부터 가장 멀리 떨어진 노드가 몇 개인지를 return 하도록 solution 함수를 작성해주세요.
	 * --
	 * 제한사항
	 * 	노드의 개수 n은 2 이상 20,000 이하입니다.
	 * 	간선은 양방향이며 총 1개 이상 50,000개 이하의 간선이 있습니다.
	 * 	vertex 배열 각 행 [a, b]는 a번 노드와 b번 노드 사이에 간선이 있다는 의미입니다.
	 * */
	public static void main(String[] args) {
		// 6	[[3, 6], [4, 3], [3, 2], [1, 3], [1, 2], [2, 4], [5, 2]]	3
		int n = 6;
		int[][] edge = {
			{3, 6},
			{4, 3},
			{3, 2},
			{1, 3},
			{1, 2},
			{2, 4},
			{5, 2},
		};
		int result = solution(n, edge);
		System.out.println("result {"+result+"}");
	}

	/**
	 * 재해석
	 * 모든 노드까지의 최단거리 를 각각 구하고, 그 중 가장 큰 거리값을 가진 노드가 몇 개
	 * */
	public static int solution(int n, int[][] edge) {
		Graph graph = create(n,edge);
		return search(graph);
	}
	public static int search(Graph graph){
		Node start = graph.get(1);
		return getCnt(start);
	}
	public static int getCnt(Node firstNode){
		// 1. Queue 큐 생성
		Queue<Node> queue = new LinkedList<>();

		// 2. 첫번째 노드 삽입
		queue.add(firstNode);
		// 방문표기
		firstNode.mark();

		// 3. 초기화
		//int distance = 0;
		int cnt = 0;

		while (!queue.isEmpty()){
			// 같은 거리의 node 가 몇개
			int distanceGroup = queue.size();
			// 그게 곧 node 의 갯수 -> 마지막에 실행된 loop 가 가장 먼거리
			cnt = distanceGroup;
			//System.out.println("Level {"+distance+"} node cnt : {"+distanceGroup+"}");

			// Loop1. 같은 거리(level)의 수만큼 반복
			for(int i=0;i<distanceGroup;i++){

				// Loop2. queue 에서 node 를 꺼내고
				Node node = queue.poll();
				//System.out.print(node.value + " ");

				// Loop3. 인접 노드를 탐색
				assert node != null;
				for(Node n : node.neighbors){
					// 방문하지 않은 노드만
					if(!n.visited){
						// queue 에 담고
						queue.add(n);
						// 방문을 표시
						n.mark();
					}
				}
			}

			//distance++;
			//System.out.println();
		}

		return cnt;
	}
	/**
	 * 그래프생성
	 * */
	public static Graph create(int n, int[][] edge){
		Graph graph = new Graph();
		// 1. 노드 생성
		for(int i=0;i<n;i++){
			graph.addNode(i+1);
		}
		// 2. Edge 생성
		for (int[] ints : edge) {
			graph.addEdge(ints[0], ints[1]);
		}
		return graph;
	}
	/**
	 * 그래프
	 * */
	public static class Graph {
		Map<Integer, Node> nodeMap;
		public Graph() {
			nodeMap = new HashMap<>();
		}
		public void addNode(int value) {
			nodeMap.computeIfAbsent(value,Node::new);
		}
		public void addEdge(int from, int to){
			Node n1 = this.get(from);
			Node n2 = this.get(to);
			if(n1 != null && n2 != null){
				n1.neighbors.add(n2);
				// 양방향
				n2.neighbors.add(n1);
			}
		}
		public Node get(int value){
			return nodeMap.get(value);
		}
	}
	/**
	 * 노드
	 * */
	public static class Node {
		int value;
		boolean visited;
		LinkedList<Node> neighbors;

		// 생성자
		public Node(int value) {
			this.value = value;
			this.visited = false;
			neighbors = new LinkedList<>();
		}
		// 방문표기
		void mark() {
			this.visited = true;
		}
	}
}
