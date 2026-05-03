package org.moka.level0;

// https://school.programmers.co.kr/learn/courses/30/lessons/181838

/**
 * Level0
 * 날짜 비교하기
 * 정답률 88 %
 * */
public class P181838 {
	// 정수 배열 date1과 date2가 주어집니다.
	// 두 배열은 각각 날짜를 나타내며 [year, month, day] 꼴로 주어집니다.
	// 각 배열에서 year는 연도를, month는 월을, day는 날짜를 나타냅니다.
	// 만약 date1이 date2보다 앞서는 날짜라면 1을, 아니면 0을 return 하는 solution 함수를 완성해 주세요.
	// 제한사항
	// date1의 길이 = date2 의 길이 = 3
	// 0 <= year <= 10,000
	// 1 <= month <= 12
	// day 는 month에 따라 가능한 날짜로 주어집니다.
	public static void main(String[] args) {
		P181838 p = new P181838();
		int[] date1 = {2021, 12, 28};
		int[] date2 = {2021, 12, 29};
		// return 1
		int result = p.solution(date1, date2);
		System.out.println(result);

		// int[] date1 = {1024, 10, 24}
		// int[] date2 = {1024, 10, 24}
		// return 0

	}
	public int solution(int[] date1, int[] date2){
		int answer = 0;
		// date2가 최근 날짜이면서 같은 날짜가 아닐때 1 반환

		int year 	= date1[0];
		int month	= date1[1];
		int day 	= date1[2];

		int date1Sum = year*20 		+ month*40 		+ day;
		int date2Sum = date2[0]*20 	+ date2[1]*40 	+ date2[2];

		answer = date2Sum > date1Sum ? 1 : 0;

		return answer;
	}
}
