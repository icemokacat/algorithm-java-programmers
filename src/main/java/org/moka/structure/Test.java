package org.moka.structure;

import java.util.ArrayList;

import org.moka.structure.base.RunTimer;

public class Test {
	public static void main(String[] args){
		// 이진 트리 장점 테스트
		// 랜덤 숫자 범위 (1~10,000,000)
		// 갯수 : 1,000,000
		int max = 10000000;

		// 일반 리스트
		ArrayList<Integer> list = new ArrayList<>();
		for (int i = 0; i < 1000000; i++) {
			int random = (int) (Math.random() * max) + 1;
			list.add(random);
		}

		// 이진 탐색 트리
		BinarySearchTree.Tree tree = new BinarySearchTree.Tree(max/2);
		for (int i = 0; i < 100000; i++){
			int random = (int) (Math.random() * max) + 1;
			tree.insert(random);
		}

		RunTimer timer = new RunTimer();
		// CASE1 : 오름차순 정렬
		timer.measure(() -> list.sort(Integer::compareTo));

		timer.measure(tree::getNodesInOrder);

		// CASE2: SEARCH
		int target = (int) (Math.random() * max) + 1;
		timer.measure(() -> list.contains(target));
		timer.measure(() -> tree.search(target));

	}

}
