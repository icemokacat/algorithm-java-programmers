package org.moka.level0;

// https://school.programmers.co.kr/learn/courses/30/lessons/120886

import java.util.Arrays;

/**
 * Level 0
 * A로 B 만들기
 * 정답률 87 %
 * */
public class P120886 {
	/*
	* 문자열 before와 after가 매개변수로 주어질 때,
	* before의 순서를 바꾸어 after를 만들 수 있으면 1을,
	* 만들 수 없으면 0을 return 하도록 solution 함수를 완성해보세요.
	* --
	* 제한사항
	* 0 < before의 길이 == after의 길이 < 1,000
	* before와 after는 모두 소문자로 이루어져 있습니다.
	* */
	public static void main(String[] args){
		// "olleh"	"hello"	1
		// "allpe"	"apple"	0
		// "abcd"   "dcab"
		String b = "hlleo";
		String a = "hello";
		int result = solution(b,a);
		System.out.println(result);
	}
	public static int solution(String before, String after) {
		if(before.length() != after.length()){
			return 0;
		}
		char[] b = before.toCharArray();
		char[] a = after.toCharArray();
		Arrays.sort(b);
		Arrays.sort(a);
		return Arrays.equals(a, b) ? 1 : 0;
	}
}
