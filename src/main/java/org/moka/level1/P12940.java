package org.moka.level1;

// https://school.programmers.co.kr/learn/courses/30/lessons/12940

/**
 * Level1
 * 최대공약수와 최소공배수
 * */

public class P12940 {

	public static void main(String[] args) {
		P12940 p = new P12940();
		int n = 196;
		int m = 21;
		int[] result = p.solution(n, m);
		for (int i : result) {
			System.out.println(i);
		}
		int a = 196 % 21;
		//System.out.println(a);
	}

	/**
	 * 문제 설명
	 * 	두 수를 입력받아 두 수의 최대공약수와 최소공배수를 반환하는 함수, solution을 완성해 보세요.
	 * 	배열의 맨 앞에 최대공약수, 그다음 최소공배수를 넣어 반환하면 됩니다.
	 * 	예를 들어 두 수 3, 12의 최대공약수는 3, 최소공배수는 12이므로 solution(3, 12)는 [3, 12]를 반환해야 합니다.
	 * <p>
	 * 제한 사항
	 * 	두 수는 1이상 1000000이하의 자연수입니다.
	 * <p>
	 * 입출력 예
	 * 	n	m	return
	 * 	3	12	[3, 12]
	 * 	2	5	[1, 10]
	 * */

	public int[] solution(int n, int m) {
		int[] answer = {};
		// 최대 공약수
		int GCD = getGcd(n, m);
		// 최소 공배수 : 2개 이상의 자연수에서 공통된 배수(공배수) 중 가장 작은 수
		// > 서로 다른 기준(주기, 단위)을 하나로 맞출 때 필요한 최소 기준
		// 예시) A는 21일 마다 반복
		//      B는 28일 마다 반복
		// A와 B가 동시에 실행되는 (최초의) 일을 구하고 싶음
		// => A와 B의 최소 공배수를 구하면 됨
		// 최소 공배수는 최대 공약수를 알면 구할 수 있다.
		// 최소 공배수 = (두 수의 곱) / 최대 공약수
		// 21 = 3*7
		// 28 = 2^2*7
		// 7은 "공통된 약수" 이면서 그 중 가장 큰 약수
		// => 고로 한번만 곱하면 된다
		// LCM = (21*28)/7
		//     = (3*7 * 2^2*7) / 7
		// 두수를 곱하면 최대공약수를 2번 곱하므로
		// => 84일 (A는 4번 반복하면, B는 3번 반복하면 둘이 만남)
		int LCM = (n*m) / GCD;
		answer = new int[] {GCD, LCM};
		return answer;
	}

	/**
	 * 최대 공약수
	 *  - 두 수의 공통된 약수 중 가장 큰 수
	 *  hint1 최대 공약수라는 말은 두 수 중 하나로 나누어서 나머지가 생기지 않는 다는 말이다
	 *  hint2 한 수를 가능한 만큼 갭을 줄인다.
	 *  hint3 하나의 수를 작은수로 될 수 있을만큼 뺀다는 말은
	 *    나누고 나서 나머지를 뜻한다
	 *    ex) 196 21 => 196-21-21-21 .... 나머지 : 7
	 * */
	public int getGcd(int a,int b){
		while (a % b != 0){
			int temp = a % b;
			a = b;
			b = temp;
		}
		return b;
	}

}
