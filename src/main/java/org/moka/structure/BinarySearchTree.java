package org.moka.structure;

import java.util.LinkedList;
import java.util.List;

import org.moka.structure.base.Orders;

/**
 * 이진 탐색 트리
 * */
public class BinarySearchTree {

	public static class Node {
		public int value;
		public Node left, right;

		Node(int val){
			value = val;
			left = right = null;
		}
	}

	public static class Tree{
		public Node root;
		public Tree(int n){
			root = new Node(n);
		}
		// 탐색
		public Node search(int n){
			Node current = root;

			// 현재 노드가 null이 될 때까지 탐색
			// 이유 : 탐색이 끝날 때까지 계속해서 자식 노드로 내려가야하기 때문
			// 다른 방향을 탐색 안해도 되는 이유 :
			//  -> 이진 탐색 트리는 왼쪽 자식 노드는 부모 노드보다 작은 값을 가지고, 오른쪽 자식 노드는 부모 노드보다 큰 값을 가지는 트리이기 때문
			// 예를 들어
			//	 70
			//	/  \
			// 50   80
			// / \    \
			//30 60   90
			// 여기서 만약 "90" 을 찾는 다고 하면
			// 70 < 90 -> right 한번 탐색하면
			// left 에는 "90" 보다 작은 값이 있기 때문에 탐색할 필요가 없다.
			while (current != null) {
				// 1. 현재 노드에서 해당 값인지 확인
				if (current.value == n) {
					return current;
				}
				// 2. 현재 노드가 아닌 경우 (크기 비교)
				if (n < current.value) {
					// 작은 경우 왼쪽으로 탐색
					current = current.left;
				} else {
					// 큰 경우 오른쪽 으로 탐색
					current = current.right;
				}
			}

			return null;
		}
		// 삽입
		public void insert(int n){
			Node someNode = root;

			// 재귀 필요
			while(someNode.value != n){
				if(n < someNode.value){
					// 가운데 값 보다 작은 경우 왼쪽으로 (탐색 혹은 삽입)
					if(someNode.left == null){
						// 삽입
						someNode.left = new Node(n);
					}
					// 탐색
					someNode = someNode.left;
				}else{
					// 가운데 값 보다 큰 경우 오른쪽으로 (탐색 혹은 삽입)
					if(someNode.right == null){
						// 삽입
						someNode.right = new Node(n);
					}
					// 탐색
					someNode = someNode.right;
				}
			}
		} // end insert
		// 최소값 찾기
		Node minValueNode(Node node){
			while (node.left != null) {
				node = node.left;
			}
			return node;
		}
		// 최대값 찾기
		Node maxValueNode(Node node){
			while (node.right != null) {
				node = node.right;
			}
			return node;
		}
		// 삭제
		public void delete(int n){
			root = deleteNode(root, n);
		}
		private Node deleteNode(Node node,int n){
			// CASE1. 자식이 없는 경우 (리프노드)
			// CASE2. 자식이 하나인 경우
			// CASE3. 자식이 둘인 경우
			//	-> 삭제할 노드의 오른쪽 서브트리에서 가장 작은 값을 가진 노드를 찾아서 삭제할 노드의 위치로 이동
			if(node == null) return null;

			if(node.value > n){
				node.left = deleteNode(node.left, n);
				return node;
			}else if(node.value < n){
				node.right = deleteNode(node.right, n);
				return node;
			}

			// 찾았다. 삭제대상인 나를 어떻게 처리할 것인가
			// CASE1. 자식이 없는 경우 (리프노드)
			if(node.left == null && node.right == null){
				// 그냥 삭제 (재귀에 의해서 부모의 left or right 가 null 이 됨)
				return null;
			}
			// CASE2. 자식 중 하나가 null 인 경우
			else if(node.left == null){
				return node.right;
			}else if(node.right == null){
				return node.left;
			}

			// CASE3. 둘다 자식이 있는 경우
			// 오른쪽 서브트리에서 가장 작은 값을 가진 노드를 찾아서 삭제할 노드의 위치로 이동
			Node successor = minValueNode(node.right);
			// 후계자의 값을 삭제할 노드에 복사
			node.value = successor.value;
			// 후계자 노드를 삭제
			node.right = deleteNode(node.right, successor.value);
			return node;
		}
		// 정렬하여 가져오기
		public List<Node> getNodesInOrder(Orders order){
			List<Node> result = new LinkedList<>();
			if(Orders.Ascending == order){
				inorderAsc(root, result);
			}else{
				inorderDes(root, result);
			}
			return result;
		}
		// InorderAsc: 오름 차순
		static void inorderAsc(Node node, List<Node> result){
			if(node != null){
				inorderAsc(node.left, result);
				result.add(node);
				inorderAsc(node.right, result);
			}
		}
		// InorderDes 내림 차순
		static void inorderDes(Node node, List<Node> result){
			if(node != null){
				inorderDes(node.right, result);
				result.add(node);
				inorderDes(node.left, result);
			}
		}
		// default
		public List<Node> getNodesInOrder(){
			return getNodesInOrder(Orders.Ascending);
		}
		// 최대 깊이 탐색
		public int getMaxDepth(){
			// 루트 노드에서 가장 깊은 노드까지의 경로의 길이
			return getMaxDepth(root);
		}
		public int getMaxDepth(Node node){
			if(node == null) return 0;
			int leftDepth = getMaxDepth(node.left);
			int rightDepth = getMaxDepth(node.right);
			return Math.max(leftDepth, rightDepth) + 1;
		}

	}

}
