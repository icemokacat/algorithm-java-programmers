package org.moka.level0;

// https://school.programmers.co.kr/learn/courses/30/lessons/181938

/**
 * Level 0
 * 두 수의 연산값 비교하기
 * 정답률 81 %
 * */
public class P181938 {
	/*
	* 연산 ⊕는 두 정수에 대한 연산으로 두 정수를 붙여서 쓴 값을 반환합니다. 예를 들면 다음과 같습니다.
	*	12 ⊕ 3 = 123
	*	3 ⊕ 12 = 312
	* 양의 정수 a와 b가 주어졌을 때, a ⊕ b와 2 * a * b 중 더 큰 값을 return하는 solution 함수를 완성해 주세요.
	* 단, a ⊕ b와 2 * a * b가 같으면 a ⊕ b를 return 합니다.
	* 1 ≤ a, b < 10,000
	* */
	public static void main(String[] args){
		// 2	91	364
		// 91	2	912
		int a = 2;
		int b = 91;
		int result = solution(a,b);
		System.out.println(result);	// 364
	}
	public static int solution(int a, int b) {
		String as = String.valueOf(a);
		String bs = String.valueOf(b);
		int type1 = Integer.parseInt(as+bs);
		int type2 = 2*a*b;
		return Math.max(type1, type2);
	}
	public static int solutionOnlyMath(int a, int b) {
		/*
		b=3   → log10(3)=0.47 → (int)=0 → +1 = 1자리 → 10^1=10
		b=12  → log10(12)=1.07 → (int)=1 → +1 = 2자리 → 10^2=100
		b=100 → log10(100)=2 → (int)=2 → +1 = 3자리 → 10^3=1000
		*/
		// b의 자리수 만큼 0 을 늘림
		// 12 ⊕ 3 = 12 * 10^1 + 3 = 123
		// 3 ⊕ 12 = 3 * 10^2 + 12 = 312
		// a*10^n + b
		// n = (int) log10(b) + 1
		return Math.max(
			(int)Math.pow(10, (int)Math.log10(b) + 1) * a + b,
			2 * a * b
		);
	}
}
