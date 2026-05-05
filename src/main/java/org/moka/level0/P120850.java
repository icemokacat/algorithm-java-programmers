package org.moka.level0;
// https://school.programmers.co.kr/learn/courses/30/lessons/120850

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collector;

/**
 * Level0
 * 문자열 정렬하기1
 * 정답률 89 %
 * */
public class P120850 {
	/*
	* 문자열 my_string이 매개변수로 주어질 때, my_string 안에 있는 숫자만 골라
	* 오름차순 정렬한 리스트를 return 하도록 solution 함수를 작성해보세요.
	* 1 ≤ my_string의 길이 ≤ 100
	* my_string에는 숫자가 한 개 이상 포함되어 있습니다.
	* my_string은 영어 소문자 또는 0부터 9까지의 숫자로 이루어져 있습니다.
	* */
	public static void main(String[] args){
		/*"hi12392"	[1, 2, 2, 3, 9]
		"p2o4i8gj2"	[2, 2, 4, 8]
		"abcde0"	[0]*/
		String a = "hi12392";
		int[] result = solution(a);
		System.out.println(Arrays.toString(result));
	}
	public static int[] solution(String my_string) {
		int[] answer = getAnswer(my_string);
		return answer;
	}
	public static int[] getAnswer(String s){
		char[] chars = s.toCharArray();
		ArrayList<Integer> numbers = new ArrayList<>();
		for(char ch : chars){
			if(Character.isDigit(ch)){
				numbers.add(ch - '0');
			}
		}
		return numbers.stream().sorted().mapToInt(Integer::intValue).toArray();
	}
}
