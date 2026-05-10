package org.moka.level0;

// https://school.programmers.co.kr/learn/courses/30/lessons/120815

/**
 * Level 0
 * 피자 나눠 먹기 (2)
 * 정답률 81 %
 * */
public class P120815 {
	public static void main(String[] args){
		// 6 1
		// 10 5
		// 4 2
		int n = 8;
		System.out.println(solution(n));
	}
	public static int solution(int n) {
		int answer = 0;
		// 6x % n = 0 을 만족하는 x 를 구하기
		// 6과 n 의 최소 공배수 k 구하기
		// k / 6 이 answer

		// 최소공배수는 두수 6*n*GCD
		// 즉 두수를 곱하고 최대공약수로 나누면 구할 있다.
		int a = 6;	// 6조각
		int gcd = getGcd(a,n);
		/*
		int k = (a*n)/gcd;
		return k / a;
		*/
		return n/gcd;
	}
	// 최대 공약수
	public static int getGcd(int a,int b){
		while (a % b != 0){
			int temp = a % b;
			a = b;
			b = temp;
		}
		return b;
	}
}
