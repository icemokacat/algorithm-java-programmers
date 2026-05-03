package org.moka.structure;

/**
 * 균형 이진 탐색 트리(Self-balancing Binary Search Tree)의 일종인 AVL 트리(Adelson-Velsky and Landis Tree)는
 * 각 노드의 왼쪽과 오른쪽 서브트리의 높이 차이가 최대 1이 되도록 유지하는 이진 탐색 트리입니다.
 * AVL 트리는 삽입과 삭제 연산 후에도 트리의 균형을 유지하기 위해 회전 연산을 사용합니다.
 * AVL 트리는 검색, 삽입, 삭제 연산이 모두 O(log n)의 시간 복잡도를 가지며, 균형이 유지되므로 최악의 경우에도 효율적인 성능을 제공합니다.
 * --
 * 이진트리와 비교했을때 장단점은
 * * 장점
 * 1. 균형 유지 : AVL 트리는 각 노드의 왼쪽과 오른쪽 서브트리의 높이 차이가 최대 1이 되도록 유지하므로, 트리가 균형을 이루고 있습니다.
 * 				이로 인해 검색, 삽입, 삭제 연산이 O(log n)의 시간 복잡도를 가지며, 최악의 경우에도 효율적인 성능을 제공합니다.
 * 2. 빠른 검색 : AVL 트리는 균형이 유지되므로, 검색 연산이 빠르게 수행됩니다.
 * 				트리의 높이가 O(log n)로 유지되기 때문에, 검색 연산이 효율적으로 수행됩니다.
 * 3. 다양한 응용 : AVL 트리는 데이터베이스 인덱스, 메모리 관리, 그래프 알고리즘 등 다양한 응용 분야에서 사용됩니다.
 * --
 * * 단점
 * 1. 삽입과 삭제의 복잡성 		: AVL 트리는 삽입과 삭제 연산 후에도 트리의 균형을 유지하기 위해 회전 연산을 사용합니다.
 * 									이로 인해 삽입과 삭제 연산이 O(log n)의 시간 복잡도를 가지지만, 회전 연산이 추가로 필요하기 때문에 구현이 복잡할 수 있습니다.
 * 2. 메모리 사용 				: AVL 트리는 각 노드가 높이 정보를 저장해야 하므로, 일반적인 이진 탐색 트리에 비해 메모리 사용이 더 많을 수 있습니다.
 * 3. 특정 시나리오에서 성능 저하 	: AVL 트리는 균형을 유지하기 위해 회전 연산을 사용하지만, 특정 시나리오에서는 회전 연산이 빈번하게 발생할 수 있습니다.
 * 									예를 들어, 삽입과 삭제가 빈번하게 발생하는 경우, AVL 트리는 회전 연산이 자주 발생하여 성능이 저하될 수 있습니다.
 * */
public class AVLTree {
	public static class Node {
		public int value;
		public Node left, right;
		public int height;

		Node(int val) {
			value = val;
			left = right = null;
			height = 1; // 새 노드의 높이는 1로 초기화
		}
	}
	public static class AVL {
		Node insert(Node node, int val){
			if(node == null){
				return new Node(val);
			}
			if(val < node.value){
				node.left = insert(node.left, val);
			}else if(val > node.value){
				node.right = insert(node.right, val);
			}else{
				return node;
			}

			node.height = 1 + Math.max(getHeight(node.left),
										getHeight(node.right));

			int balance = getBalance(node);

			// 왼쪽으로 치우쳐 있는 경우 -> LL
			if (balance > 1 && val < node.left.value){
				return rotateRight(node);
			}
			// 오른쪽으로 치우쳐 있는 경우 -> RR
			if (balance < -1 && val > node.right.value) {
				return rotateLeft(node);
			}
			// LR
			if (balance > 1 && val > node.left.value) {
				node.left = rotateLeft(node.left);
				return rotateRight(node);
			}
			// RL -> 오른쪽
			if (balance < -1 && val < node.right.value) {
				node.right = rotateRight(node.right);
				return rotateLeft(node);
			}
			return node;
		}
		Node delete(Node node, int val){
			if (node == null) return null;

			if(val < node.value){
				node.left = delete(node.left, val);
			}else if(val > node.value){
				node.right = delete(node.right, val);
			}else{
				if(node.left == null){
					return node.right;
				}else if(node.right == null){
					return node.left;
				}
				Node temp = minValue(node.right);
				node.value = temp.value;
				node.right = delete(node.right, temp.value);
			}

			node.height = 1 + Math.max(getHeight(node.left),
										getHeight(node.right));
			int balance = getBalance(node);

			if(balance > 1){
				if (getBalance(node.left) >= 0) {
					// LL
					return rotateRight(node);
				}
				// LR
				node.left = rotateLeft(node.left);
				return rotateRight(node);
			}
			if(balance < -1){
				if (getBalance(node.right) <= 0) {
					// RR
					return rotateLeft(node);
				}
				// RL
				node.right = rotateRight(node.right);
				return rotateLeft(node);
			}
			return node;
		}
		void inorder(Node node){
			if(node != null){
				inorder(node.left);
				System.out.print(node.value + " ");
				inorder(node.right);
			}
		}
		Node minValue(Node node){
			while (node.left != null) {
				node = node.left;
			}
			return node;
		}
		int getHeight(Node node){
			return (node == null) ? 0 : node.height;
		}
		int getBalance(Node node){
			return (node == null) ? 0 :
				getHeight(node.left) - getHeight(node.right);
		}
		/*
		 * 왼쪽으로 치우져 있는 트리를 오른쪽으로 회전
		 * */
		Node rotateRight(Node b){
			Node a = b.left;
			Node T2 = a.right;
			a.right = b;
			b.left = T2;
			b.height = 1 + Math.max(getHeight(b.left),
									getHeight(b.right));
			a.height = 1 + Math.max(getHeight(a.left),
									getHeight(a.right));
			return a;
		}
		Node rotateLeft(Node a){
			Node b = a.right;
			Node T2 = b.left;
			b.left = a;
			a.right = T2;
			a.height = 1 + Math.max(getHeight(a.left),
									getHeight(a.right));
			b.height = 1 + Math.max(getHeight(b.left),
									getHeight(b.right));
			return b;
		}
	} // end avl
	public static void main(String[] args){
		AVL avl = new AVL();
		Node root = avl.insert(null, 30);
		root = avl.insert(root, 10);
		root = avl.insert(root, 20);
		root = avl.insert(root, 30);
		root = avl.insert(root, 40);
		root = avl.insert(root, 50);
		root = avl.insert(root, 25);

		System.out.println("Inorder traversal of the AVL tree is:");
		avl.inorder(root);
		System.out.println();

		root = avl.delete(root, 40);
		System.out.println("Inorder traversal after deletion of 40:");
		avl.inorder(root);
		System.out.println();
	}
}
