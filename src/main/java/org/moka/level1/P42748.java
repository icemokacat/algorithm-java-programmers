package org.moka.level1;

// https://school.programmers.co.kr/learn/courses/30/lessons/42748

import java.util.Arrays;

/**
 * Level 1
 * K번째수
 * 정답률 73 %
 * */
public class P42748 {
	/*
		배열 array의 i번째 숫자부터 j번째 숫자까지 자르고 정렬했을 때, k번째에 있는 수를 구하려 합니다.
		예를 들어 array가 [1, 5, 2, 6, 3, 7, 4],
			i = 2, j = 5, k = 3이라면
			1. array의 2번째부터 5번째까지 자르면 [5, 2, 6, 3]입니다.
			2. 1에서 나온 배열을 정렬하면 [2, 3, 5, 6]입니다.
			3. 2에서 나온 배열의 3번째 숫자는 5입니다.
		배열 array, [i, j, k]를 원소로 가진 2차원 배열 commands가 매개변수로 주어질 때,
		commands의 모든 원소에 대해 앞서 설명한 연산을 적용했을 때 나온 결과를
		배열에 담아 return 하도록 solution 함수를 작성해주세요.

		제한사항
		array의 길이는 1 이상 100 이하입니다.
		array의 각 원소는 1 이상 100 이하입니다.
		commands의 길이는 1 이상 50 이하입니다.
		commands의 각 원소는 길이가 3입니다.
	*/
	public static void main(String[] args){
		// [1, 5, 2, 6, 3, 7, 4]	[[2, 5, 3], [4, 4, 1], [1, 7, 3]]	[5, 6, 3]
		int[] arr = {1, 5, 2, 6, 3, 7, 4};
		int[][] commands = {
			{2, 5, 3},
			{4, 4, 1},
			{1, 7, 3},
		};
		int[] result = solution(arr,commands);
		System.out.println(Arrays.toString(result));
		// 5,6,3
	}
	public static int[] solution(int[] array, int[][] commands) {
		int[] answer = new int[commands.length];
		for(int i=0;i< commands.length;i++){
			answer[i] = findK(array,commands[i]);
		}
		return answer;
	}

	/*
	 * i = command[0] -> 자르기 시작할 위치 (index+1)
	 * j = command[1] -> 자르기 종료할 위치 (index+1)
	 * k = command[2] -> arr 를 i~j 로 자르고 오름차순으로 정렬했을때 잘라진 배열에서 가져올 숫자의 위치 (index+1)
	 *
	 * 1 <= arr[x] <= 100 (arr 길이도 마찬가지)
	 * */
	public static int findK(int[] arr, int[] command){
		int from 	= command[0]-1;	// n 번째 -1
		int to 		= command[1]-1;	// n 번째 -1
		int k 		= command[2]-1;	// n 번째 -1
		// Step1. 문자열을 자른다.
		int[] sorted = Arrays.copyOfRange(arr, from, to + 1);
		// Step2. 정렬한다.
		Arrays.sort(sorted);
		// Step3. 꺼낸다.
		return sorted[k];
	}

}
