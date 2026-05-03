package org.moka.level3;

// https://school.programmers.co.kr/learn/courses/30/lessons/150367

import org.moka.structure.BinarySearchTree;
import org.moka.structure.utils.BSTUtil;

/**
 * Level3
 * 표현 가능한 이진트리
 * 정답률 35 %
 * */
public class P150367 {
	public static void main(String[] args) {
		long[] numbers = {128};
		int[] result = solution(numbers);
		System.out.println();
		for(int i : result){
			System.out.print(i + " ");
		}
		System.out.println();
	}
	public static int[] solution(long[] numbers) {
		int[] answer = new int[numbers.length];

		for(int i=0;i<numbers.length;i++){
			answer[i] = isAvailable(numbers[i]);
		}

		return answer;
	}
	public static int isAvailable(long number){
		if(number == 0) return 0;
		if(number == 1) return 1;

		// 1로만 구성되었는지 확인
		if(isFullBinary(number)){
			return 1;
		}

		// 2진수 사이즈 계산
		// log2(number) 를 int 로 형변환 후 +1
		// -> 이는 곧 트리의 node 수와 같음 (앞에 0이 없는 2진수)
		int arraySize = getBinarySize(number);
		// 이때 포화이진트리를 만족하기 위한 노드 수는
		// 2^( (int) log2(arraySize) + 1 ) - 1
		int totalNodeCount = getTotalNodeSize(arraySize);

		// 중앙 index 값
		int midIndex = totalNodeCount / 2;
		// 2진수 변환
		int[] binaryArray = new int[totalNodeCount];
		// 먼저 totalNodeCount-arraySize 만큼 0으로 채움
		// 초기화시 이미 0 으로 채워져있음 나머지는 2진수 표현되는 갯수가 정해져 있으므로 충돌나지 않음

		int idx = totalNodeCount;

		while (number > 0) {
			int n = (int)(number % 2);
			binaryArray[--idx] = n;
			if(midIndex == idx && 0 == n){
				// 가운데가 0 이면 root node 가 없다(더미노드)는 뜻이므로 0 반환
				return 0;
			}
			number /= 2;
		}

		// 사이즈 만큼 index 의 배열을 만듬
		int[] indexArray = new int[totalNodeCount];
		for(int i=0;i<totalNodeCount;i++){
			indexArray[i] = i;
		}
		// 균형잡힌 이진트리
		Tree tree = new Tree(indexArray);

		for(int i=0;i<binaryArray.length;i++){
			int binaryValue = binaryArray[i];
			if(binaryValue == 0){
				Node node = tree.search(indexArray[i]);
				// 해당 노드에 자식이 하나라도 있다
				if(node.left != null || node.right != null){
					if(node.left != null && node.right != null){
						int left 	= binaryArray[node.left.value];
						int right 	= binaryArray[node.right.value];
						if(left == 0 && right == 0){
							continue;
						}else{
							return 0;
						}
					}else{
						return 0;
					}
				}
			}
		}

		return 1;
	}
	public static int getBinarySize(long tenNumber){
		return (int)(Math.log(tenNumber) / Math.log(2)) + 1;
	}
	public static int getTotalNodeSize(int arrayCount){
		return Integer.highestOneBit(arrayCount) * 2 - 1;
	}
	public static boolean isFullBinary(long tenNumber){
		return (tenNumber & (tenNumber + 1)) == 0;
	}
	public static boolean isPerfectBinary(long tenNumber){
		return (tenNumber & (tenNumber - 1)) == 0;
	}
	public static class Node {
		public int value;
		public Node left, right;

		Node(int val){
			value = val;
			left = right = null;
		}
	}
	public static class Tree {
		public Node root;
		public Tree(int n){
			root = new Node(n);
		}
		public Node search(int n) {
			Node current = root;
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
		public Tree(int[] numbers){
			// 0~n 까지의 숫자로된 배열을 균형 삽입
			int midIndex = numbers.length / 2;
			root = new Node(numbers[midIndex]);

			insertMiddle(this, numbers, 0, midIndex - 1);
			insertMiddle(this, numbers, midIndex + 1, numbers.length - 1);
		}
		// 중앙 삽입
		private static void insertMiddle(Tree tree, int[] arr, int left, int right) {
			if (left > right) return;
			int mid = (left + right) / 2;
			tree.insert(arr[mid]);
			insertMiddle(tree, arr, left, mid - 1);
			insertMiddle(tree, arr, mid + 1, right);
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
	}
}
