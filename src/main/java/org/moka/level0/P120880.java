package org.moka.level0;

// https://school.programmers.co.kr/learn/courses/30/lessons/120880

import java.util.Arrays;

/**
 * Level0
 * 특이한 정렬
 * 정답률 74 %
 * */
public class P120880 {
	/*
		정수 n을 기준으로 n과 가까운 수부터 정렬하려고 합니다.
		이때 n으로부터의 거리가 같다면 더 큰 수를 앞에 오도록 배치합니다.
		정수가 담긴 배열 numlist와 정수 n이 주어질 때
		numlist의 원소를 n으로부터 가까운 순서대로 정렬한 배열을
		return하도록 solution 함수를 완성해주세요.
	*/
	public static void main(String[] args){
		/*
		제한사항
			1 ≤ n ≤ 10,000
			1 ≤ numlist 의 원소 ≤ 10,000
			1 ≤ numlist 의 길이 ≤ 100
			numlist 는 중복된 원소를 갖지 않습니다.
		*/
		/*
			[1, 2, 3, 4, 5, 6]				4	[4, 5, 3, 6, 2, 1]
			[10000,20,36,47,40,6,10,7000]	30	[36, 40, 20, 47, 10, 6, 7000, 10000]
		*/
		int[] nums = {8,35,36,41,44,58,59,76,94};
		//int[] nums = {10000,20,36,47,40,6,10,7000};
		int n = 57;
		//int n = 30;
		int[] result = solution(nums,n);
		System.out.println(Arrays.toString(result));

	}
	public static int[] solution(int[] numlist, int n) {
		int[] answer = getSorted(numlist,n);
		return answer;
	}
	public static int[] getSorted(int[] numlist, int n) {
		if(numlist.length == 1){
			return numlist;
		}

		// 정렬
		Arrays.sort(numlist);

		// 기준값이 가장 왼쪽에 있으면 그대로 반환
		if(numlist[0] >= n){
			return numlist;
		}
		// 기준값이 가장 오른쪽에 있으면 reverse 후 반환
		if(numlist[numlist.length-1] <= n){
			for(int i=0, j = numlist.length - 1; i < j; i++, j--){
				int temp = numlist[i];
				numlist[i] = numlist[j];
				numlist[j] = temp;
			}
			return numlist;
		}

		int[] result = new int[numlist.length];

		// STEP1. 중간지점 탐색 후 left, right 설정
		int k = 0;
		int left = 0;
		int right = 0;
		for(int i=0;i<numlist.length;i++){
			// 기준값이 대략적으로 위치하는 곳을 탐색
			if(numlist[i] >= n){
				left 	= i-1;
				right 	= i;
				break;
			}
		}

		// Step2. 양옆으로 전개하면서 차이비교하여 삽입
		while (k <= numlist.length-1){

			// 한쪽만 남은 경우
 			if(left < 0){
				result[k++] = numlist[right];
				right = right + 1;
				continue;
			}
			if(right >= numlist.length){
				result[k++] = numlist[left];
				left = left - 1;
				continue;
			}

			int diffLeft 	= n - numlist[left];
			int diffRight	= numlist[right] - n;

			// 거리가 같은 경우
			if(diffLeft == diffRight){
				// 큰수가 먼저
				result[k++] = numlist[right];
				result[k++] = numlist[left];
				left--;
				right++;
			}else if(diffLeft < diffRight){
				// 왼쪽이 차이가 작으면 왼쪽만 넣고 포인터를 왼쪽으로
				result[k++] = numlist[left];
				left--;
			}else{
				result[k++] = numlist[right];
				// 오른쪽이 차이가 크면 오른쪽만 넣고 포인터를 오른쪽으로
				right++;
			}

		}

		return result;
	}
}
