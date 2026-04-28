package org.moka.etc;

// https://www.acmicpc.net/problem/14252

import java.util.Arrays;

public class CommitmentSeq {

	/**
	 * 공약 수열
	 * --
	 * 😊문제
	 * 서로 다른 양의 정수로 이루어진 크기가 N인 집합 A가 주어진다.
	 * 영선이는 집합에 새로운 양의 정수를 추가하려고 한다.
	 * 이때, 집합에 있는 수를 정렬한 결과에서 인접한 두 수의 공약수가 1을 넘으면 안 된다.
	 * 그러기 위해서 수를 최소 몇 개 추가해야하는지 구하는 프로그램을 작성하시오.
	 * --
	 * ⚡  입력
	 * 첫째 줄에 N이 주어진다. (1 ≤ N ≤ 50)
	 * 둘째 줄에는 집합에 포함되어 있는 수가 주어진다. 주어지는 수는 100,000보다 작거나 같은 자연수이다.
	 * --
	 * ✨ 출력
	 * 첫째 줄에 수를 최소 몇 개 추가해야하는지 출력한다.
	 * --
	 * 🛰️ 예시입력1
	 * 4
	 * 2200 42 2184 17
	 * 🚩️ 예시출력1
	 * 3
	 * --
	 * 🛰️ 예시입력2
	 * 5
	 * 13 1 6 20 33
	 * 🚩️ 예시출력2
	 * 0
	 * hint. 예제 1의 경우에 {43, 2195, 2199}를 추가하면 된다.
	 * */
	public static void main(String[] args) {
		CommitmentSeq seqClass = new CommitmentSeq();

		int[] vals = {2200, 42, 2184, 17};
		// 1. 오름차순 정렬
		Arrays.sort(vals);

		// 2. 완전 탐색으로 구하기
		int result1 = solution1(vals);
		System.out.println("입력값 : " + Arrays.toString(vals));
		System.out.println("완전탐색 결과 {" + result1 + "}");

	}

	public static int solution1(int[] vals){
		// 사전에 정렬이 되어있다 가정 (오름차순)
		int count = 0;
		int len = vals.length;
		for(int i=0;i<len-1;i++){
			int a = vals[i];
			int b = vals[i+1];
			if (getGcd(a, b) > 1){
				// 공약수가 1보다 크면, 사이에 수를 추가해야한다.
				count += getBetweenCount(a, b);
			}
		}
		return count;
	}

	// 최대공약수
	public static int getGcd(int a,int b){
		while (a % b != 0){
			int temp = a % b;
			a = b;
			b = temp;
		}
		return b;
	}

	// 두 수 사이의 사잇값 갯수 찾기
	public static int getBetweenCount(int a, int b) {
		// a와 b가 서로소면 추가 불필요
		if (getGcd(a, b) == 1) return 0;

		// a와 b 사이에 양쪽 모두 서로소인 수가 있으면 1개로 해결
		for (int p = a + 1; p < b; p++) {
			if (getGcd(a, p) == 1 && getGcd(p, b) == 1) {
				return 1;
			}
		}

		// 없으면 a+1을 삽입하고, a+1과 b 사이를 재귀로 해결
		return 1 + getBetweenCount(a + 1, b);
	}


}
