package org.moka.structure.tree;

/**
 * RedBlackTree 는 자가 균형 이진 탐색 트리의 일종으로, 각 노드가 빨간색 또는 검은색으로 색칠되어 있는 트리입니다.
 * RedBlackTree는 삽입과 삭제 연산 후에도 트리의 균형을 유지하기 위해 특정 규칙을 따릅니다.
 * 이러한 규칙은 트리의 높이를 O(log n)로 유지하여 검색, 삽입, 삭제 연산이 모두 O(log n)의 시간 복잡도를 가지도록 보장합니다.
 * --
 * RedBlackTree의 주요 규칙은 다음과 같습니다:
 * 1. 각 노드는 빨간색 또는 검은색입니다.
 * 2. 루트 노드는 항상 검은색입니다.
 * 3. 모든 리프 노드(NIL 노드)는 검은색입니다.
 * 4. 빨간색 노드의 자식은 모두 검은색입니다 (즉, 빨간색 노드는 연속해서 나타날 수 없습니다).
 * 5. 모든 경로에서, 어떤 노드에서든지 그 노드로부터 자손인 리프 노드까지의 모든 경로에는 동일한 수의 검은색 노드가 존재해야 합니다.
 * --
 * RedBlackTree의 장점과 단점은 다음과 같습니다:
 * * 장점
 * 1. 균형 유지 	: RedBlackTree는 삽입과 삭제 연산 후에도 트리의 균형을 유지하기 위해 특정 규칙을 따르므로, 트리가 균형을 이루고 있습니다.
 * 					이로 인해 검색, 삽입, 삭제 연산이 O(log n)의 시간 복잡도를 가지며, 최악의 경우에도 효율적인 성능을 제공합니다.
 * 2. 빠른 검색 	: RedBlackTree는 균형이 유지되므로, 검색 연산이 빠르게 수행됩니다.
 * 					트리의 높이가 O(log n)로 유지되기 때문에, 검색 연산이 효율적으로 수행됩니다.
 * 3. 다양한 응용	: RedBlackTree는 데이터베이스 인덱스, 메모리 관리, 그래프 알고리즘 등 다양한 응용 분야에서 사용됩니다
 * * --
 * * 단점
 * 1. 삽입과 삭제의 복잡성 : RedBlackTree는 삽입과 삭제 연산 후에도 트리의 균형을 유지하기 위해 특정 규칙을 따르므로, 삽입과 삭제 연산이 O(log n)의 시간 복잡도를 가지지만,
 * 						규칙을 유지하기 위해 추가적인 연산이 필요하기 때문에 구현이 복잡할 수 있습니다.
 * 2. 메모리 사용 		: RedBlackTree는 각 노드가 색상 정보를 저장해야 하므로, 일반적인 이진 탐색 트리에 비해 메모리 사용이 더 많을 수 있습니다.
 * 3. 특정 시나리오에서 성능 저하 : RedBlackTree는 균형을 유지하기 위해 특정 규칙을 따르지만, 특정 시나리오에서는 규칙을 유지하기 위한 연산이 빈번하게 발생할 수 있습니다.
 * 예를 들어, 삽입과 삭제가 빈번하게 발생하는 경우, RedBlackTree는 규칙을 유지하기 위한 연산이 자주 발생하여 성능이 저하될 수 있습니다.
 * --
 * */
enum Color { RED, BLACK }

class Node {
	int val; Color color;
	Node left, right;
	Node(int v) { val = v; color = Color.RED; }
}
public class RedBlackTree {
	private boolean isRed(Node n) {
		return n != null && n.color == Color.RED;
	}

	private Node rotateLeft(Node h) {
		Node x = h.right; h.right = x.left;
		x.left = h; x.color = h.color;
		h.color = Color.RED;
		return x;
	}

	private Node rotateRight(Node h) {
		Node x = h.left; h.left = x.right;
		x.right = h; x.color = h.color;
		h.color = Color.RED;
		return x;
	}

	private void flipColors(Node h) {
		h.color = Color.RED;
		h.left.color = h.right.color = Color.BLACK;
	}

	public Node insert(Node h, int v) {
		if (h == null) return new Node(v);
		if (v < h.val) h.left = insert(h.left, v);
		else if (v > h.val) h.right = insert(h.right, v);
		// Fix-up
		if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
		if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
		if (isRed(h.left) && isRed(h.right)) flipColors(h);
		return h;
	}

	private Node moveRedLeft(Node h) {
		flipColors(h);
		if (isRed(h.right.left)) {
			h.right = rotateRight(h.right);
			h = rotateLeft(h);
			flipColors(h);
		}
		return h;
	}

	private Node moveRedRight(Node h) {
		flipColors(h);
		if (isRed(h.left.left)) {
			h = rotateRight(h);
			flipColors(h);
		}
		return h;
	}

	private Node min(Node h) {
		while (h.left != null) h = h.left;
		return h;
	}

	public Node deleteMin(Node h) {
		if (h.left == null) return null;
		if (!isRed(h.left) && !isRed(h.left.left))
			h = moveRedLeft(h);
		h.left = deleteMin(h.left);
		return fixUp(h);
	}

	private Node fixUp(Node h) {
		if (isRed(h.right)) h = rotateLeft(h);
		if (isRed(h.left) && isRed(h.left.left)) {
			h = rotateRight(h);
		}
		if (isRed(h.left) && isRed(h.right)) flipColors(h);
		return h;
	}

	public Node delete(Node h, int v) {
		if (v < h.val) {
			if (h.left != null) {
				if (!isRed(h.left) && !isRed(h.left.left))
					h = moveRedLeft(h);
				h.left = delete(h.left, v);
			}
		} else {
			if (isRed(h.left)) h = rotateRight(h);
			if (v == h.val && h.right == null) return null;
			if (h.right != null) {
				if (!isRed(h.right) && !isRed(h.right.left))
					h = moveRedRight(h);
				if (v == h.val) {
					Node m = min(h.right);
					h.val = m.val;
					h.right = deleteMin(h.right);
				} else
					h.right = delete(h.right, v);
			}
		}
		return fixUp(h);
	}

	public void inorder(Node h) {
		if (h != null) {
			inorder(h.left);
			System.out.print(h.val + " ");
			inorder(h.right);
		}
	}

	public static void main(String[] args) {
		RedBlackTree t = new RedBlackTree();
		Node root = null;
		for (int v: new int[]{10,20,30,40,50,25})
			root = t.insert(root, v);
		root.color = Color.BLACK;

		System.out.println("Inorder after inserts:");
		t.inorder(root); // 10 20 25 30 40 50

		root = t.delete(root, 30);
		root.color = Color.BLACK;
		System.out.println("\nInorder after delete 30:");
		t.inorder(root); // 10 20 25 40 50
	}

}
