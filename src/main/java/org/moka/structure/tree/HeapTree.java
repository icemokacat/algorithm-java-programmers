package org.moka.structure.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 여러 개의 값 중에서 가장 크거나 작은 값을 빠르게 찾기 위해 만든 이진 트리.
 * 짧게 힙(heap)이라고 줄여서 부르기도 한다.
 * --
 * 조건1.  항상 완전 이진 트리의 형태를 띠어야 한다 (즉, 마지막 레벨을 제외한 모든 레벨이 완전히 채워져 있어야 하고, 마지막 레벨은 왼쪽부터 채워져야 한다).
 * 조건2.  부모 노드의 값이 자식 노드의 값보다 크거나 같아야 한다 (최대 힙의 경우)
 * 		또는 부모 노드의 값이 자식 노드의 값보다 작거나 같아야 한다 (최소 힙의 경우).
 * --
 * Heap 의 장단점
 * --
 * * 장점
 * 1. 빠른 최대/최소값 검색 : 힙은 항상 완전 이진 트리의 형태를 띠며, 부모 노드가 자식 노드보다 크거나 작다는 조건을 만족하기 때문에,
 * 							최대 힙에서는 루트 노드가 가장 큰 값을 가지고, 최소 힙에서는 루트 노드가 가장 작은 값을 가집니다.
 * 							따라서, 최대값이나 최소값을 빠르게 검색할 수 있습니다. 이로 인해 우선순위 큐와 같은 자료구조에서 효율적으로 사용됩니다.
 * 2. 효율적인 삽입과 삭제 : 힙은 완전 이진 트리의 형태를 유지하기 때문에, 삽입과 삭제 연산이 O(log n)의 시간 복잡도를 가집니다.
 * 							삽입 시에는 새로운 요소를 트리의 마지막 위치에 추가한 후, 부모 노드와 비교하여 힙 속성을 유지하기 위해 필요한 경우 트리를 재구성합니다.
 * 							삭제 시에는 루트 노드를 제거한 후, 트리의 마지막 요소를 루트로 이동시키고, 자식 노드와 비교하여 힙 속성을 유지하기 위해 필요한 경우 트리를 재구성합니다.
 * 3. 다양한 응용 : 힙은 우선순위 큐, 힙 정렬 알고리즘, 그래프 알고리즘 등 다양한 응용 분야에서 사용됩니다.
 * 					예를 들어, 힙 정렬 알고리즘은 힙을 사용하여 데이터를 정렬하는 효율적인 방법을 제공합니다.
 * --
 * * 단점
 * 1. 검색이 비효율적 : 힙은 최대값이나 최소값을 빠르게 검색할 수 있지만, 특정 값이나 요소를 검색하는 데는 비효율적입니다.
 * 						힙은 완전 이진 트리의 형태를 띠며, 부모 노드가 자식 노드보다 크거나 작다는 조건을 만족하기 때문에, 특정 값을 검색하려면 트리를 전체적으로 탐색해야 할 수 있습니다.
 * 						이로 인해 검색 연산이 O(n)의 시간 복잡도를 가질 수 있습니다.
 * 2. 메모리 사용 	: 힙은 완전 이진 트리의 형태를 유지하기 때문에, 메모리 사용이 비효율적일 수 있습니다.
 * 					특히, 힙이 배열로 구현되는 경우, 배열의 크기가 힙의 크기보다 커질 수 있으며, 이로 인해 메모리 낭비가 발생할 수 있습니다.
 * 3. 특정 시나리오에서 성능 저하 : 힙은 완전 이진 트리의 형태를 유지하기 때문에, 특정 시나리오에서는 힙의 성능이 저하될 수 있습니다.
 * 					예를 들어, 삽입과 삭제가 빈번하게 발생하는 경우, 힙은 트리를 재구성하는 연산이 자주 발생하여 성능이 저하될 수 있습니다.
 * 					또한, 힙은 특정 값이나 요소를 검색하는 데 비효율적이기 때문에, 검색이 빈번하게 발생하는 경우에도 성능이 저하될 수 있습니다.
 * 	특히 정렬하기의 경우 O(n log n) 의 시간 복잡도를 가지는 힙 정렬 알고리즘
 * --
 * */

public class HeapTree {
	public enum HeapType { MAX, MIN }
	public final HeapType type;
	private List<Integer> heap;

	public HeapTree(HeapType type) {
		this.type = type;
		heap = new ArrayList<>();
	}

	private void swap(int i, int j) {
		int temp = heap.get(i);
		heap.set(i, heap.get(j));
		heap.set(j, temp);
	}

	/*
	 * heapifyUp 메서드는 힙 트리에서 새로운 요소가 추가된 후에 힙 속성을 유지하기 위해 필요한 경우 트리를 재구성하는 과정입니다.
	 * 이 메서드는 새로운 요소가 추가된 위치에서 시작하여 부모 노드와 비교하면서 힙 속성을 만족하도록 트리를 조정합니다.
	 * MIN heap 의 경우
	 * 	1. 새로운 요소가 부모 노드보다 작은 경우, 부모 노드와 새로운 요소를 교환합니다.
	 * 	2. 교환 후, 새로운 요소가 부모 노드보다 작은 경우, 부모 노드와 새로운 요소를 계속해서 교환합니다.
	 * 	3. 새로운 요소가 부모 노드보다 크거나 같은 경우, 힙 속성이 만족되므로 조정을 종료합니다.
	 * MAX heap 의 경우
	 * 	1. 새로운 요소가 부모 노드보다 큰 경우, 부모 노드와 새로운 요소를 교환합니다.
	 * 	2. 교환 후, 새로운 요소가 부모 노드보다 큰 경우, 부모 노드와 새로운 요소를 계속해서 교환합니다.
	 * 	3. 새로운 요소가 부모 노드보다 작거나 같은 경우, 힙 속성이 만족되므로 조정을 종료합니다.
	 * min 예시
	 *  0  1  2  3  4  5
	 * [2, 4, 3, 6, 5, 1]
	 * 				2
	 * 			4		3
	 * 		6		5	1
	 * 1. 1이 부모 노드인 3보다 작으므로, 1과 3을 교환합니다.
	 *  0  1  2  3  4  5
	 * [2, 4, 1, 6, 5, 3]
	 * 				2
	 * 			4		1
	 * 		6		5	3
	 * 2. 1이 부모 노드인 2보다 작으므로, 1과 2를 교환합니다.
	 *  0  1  2  3  4  5
	 * [1, 4, 2, 6, 5, 3]
	 * 				1
	 * 			4		2
	 * 		6		5	3
	 * 3. 1이 부모 노드보다 작거나 같으므로, 힙 속성이 만족되며 조정을 종료합니다.
	 * --
	 * */
	private void heapifyUp(int index) {
		while (index > 0) {
			int parentIndex = (index - 1) / 2;
			boolean shouldSwap = (HeapType.MIN == this.type)
				? heap.get(parentIndex) > heap.get(index)
				: heap.get(parentIndex) < heap.get(index);

			if (!shouldSwap) break;
			swap(parentIndex, index);
			index = parentIndex;  // 위로 올라감
		}
	}
	private void heapifyDown(int index) {
		while (true) {
			int leftChild = 2 * index + 1;
			int rightChild = 2 * index + 2;
			int targetIndex = index;

			if (HeapType.MIN == this.type) {
				if (leftChild < heap.size() && heap.get(leftChild) < heap.get(targetIndex)) {
					targetIndex = leftChild;
				}
				if (rightChild < heap.size() && heap.get(rightChild) < heap.get(targetIndex)) {
					targetIndex = rightChild;
				}
			} else {
				if (leftChild < heap.size() && heap.get(leftChild) > heap.get(targetIndex)) {
					targetIndex = leftChild;
				}
				if (rightChild < heap.size() && heap.get(rightChild) > heap.get(targetIndex)) {
					targetIndex = rightChild;
				}
			}

			if (targetIndex == index) break; // 교환 대상 없으면 멈춤

			swap(index, targetIndex);
			index = targetIndex; // 아래로 내려감
		}
	}
	public void buildHeap(List<Integer> arr){
		heap = new ArrayList<>(arr);
		// 리프 바로 위 레벨부터 시작해서 요소 하나하나 heapifyDown 수행 (배열로 치면 중간부터 왼쪽으로)
		for (int i = heap.size() / 2 - 1; i >= 0; i--) {
			heapifyDown(i);
		}
	}
	public void insert(int val){
		heap.add(val);
		heapifyUp(heap.size() - 1);
	}
	public int peek(){
		if (heap.isEmpty()) throw new IllegalStateException("Heap is empty");
		return heap.get(0);
	}
	public int poll(){
		if (heap.isEmpty()) throw new IllegalStateException("Heap is empty");
		int rootValue = heap.get(0);
		int lastValue = heap.remove(heap.size() - 1);
		if (!heap.isEmpty()) {
			heap.set(0, lastValue);
			heapifyDown(0);
		}
		return rootValue;
	}

	public static void main(String[] args){
		HeapTree minHeap = new HeapTree(HeapType.MIN);
		minHeap.buildHeap(List.of(5,3,53,20,22,17));
		minHeap.insert(2);
		System.out.println(minHeap.heap);
		System.out.println(minHeap.peek());
		System.out.println(minHeap.poll());
		System.out.println(minHeap.heap);

		System.out.println("-------------------");

		HeapTree maxHeap = new HeapTree(HeapType.MAX);
		maxHeap.buildHeap(List.of(5,3,53,20,22,17));
		maxHeap.insert(80);
		System.out.println(maxHeap.heap);
		System.out.println(maxHeap.peek());
		System.out.println(maxHeap.poll());
		System.out.println(maxHeap.heap);
	}

}
