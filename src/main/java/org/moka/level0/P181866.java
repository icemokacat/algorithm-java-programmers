package org.moka.level0;
// https://school.programmers.co.kr/learn/courses/30/lessons/181866

import java.util.Arrays;

/**
 * Level0
 * 문자열 잘라서 정렬하기
 * 정답률 89 %
 * */
public class P181866 {
	/*
	* 문자열 myString 이 주어질때 "x" 를 기준으로 해당 문자열을 잘라내 배열을 만든 후
	* 사전순으로 정렬 하여 배열로 리턴
	* */
	public static void main(String[] args){
		String a = "axbxcxdx";
		System.out.println(Arrays.toString(solution(a)));
	}
	public static String[] solution(String myString) {
		String[] answer = getValidAlpha(myString);
		return answer;
	}
	public static String[] getValidAlpha(String s){
		return Arrays.stream(s.split("x", -1))
			.filter(str -> !str.isEmpty())
			.sorted()
			.toArray(String[]::new);
	}
}
