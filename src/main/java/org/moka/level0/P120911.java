package org.moka.level0;

// https://school.programmers.co.kr/learn/courses/30/lessons/120911

import java.util.Arrays;

/**
 * Level0
 * 문자열 정렬하기2
 * 정답률 89 %
 * */
public class P120911 {
	/*
		영어 대소문자로 이루어진 문자열 my_string이 매개변수로 주어질 때,
		my_string을 모두 소문자로 바꾸고
		알파벳 순서대로 정렬한 문자열을 return 하도록 solution 함수를 완성해보세요.

		제한사항
		0 < my_string 길이 < 100
	*/
	public static void main(String[] args){
		/*"Bcad"	"abcd"
		"heLLo"	"ehllo"
		"Python"	"hnopty"*/
		String a = "Python";
		String result = solution(a);
		System.out.println(result);
	}
	public static String solution(String my_string) {
		String answer = getSmallAlphas(my_string);
		return answer;
	}
	public static String getSmallAlphas(String s){
		char[] chars = s.toCharArray();
		for(int i=0;i<chars.length;i++){
			char ch = chars[i];
			if('A' <= ch && ch <= 'Z'){
				chars[i] = (char)(ch + 32);
			}
		}
		Arrays.sort(chars);
		return new String(chars);
	}
}
