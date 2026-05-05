package org.moka.level2;

// https://school.programmers.co.kr/learn/courses/30/lessons/42747

import java.util.Arrays;

/**
 * Level 2
 * H-Index
 * 정답률 68 %
 * */
public class P42747 {
	/*
	*
	* 	H-Index는 과학자의 생산성과 영향력을 나타내는 지표입니다.
	* 	어느 과학자의 H-Index를 나타내는 값인 h를 구하려고 합니다.
	*
	* 	위키백과1에 따르면, H-Index는 다음과 같이 구합니다.
	*	어떤 과학자가 발표한 논문 n편 중,
	* 	h번 이상 인용된 논문이 h편 이상이고 나머지 논문이 h번 이하 인용되었다면
	* 	h의 최댓값이 이 과학자의 H-Index입니다.
	*
	*	어떤 과학자가 발표한 논문의 인용 횟수를 담은 배열 citations가 매개변수로 주어질 때,
	* 	이 과학자의 H-Index를 return 하도록 solution 함수를 작성해주세요.
	* */
	public static void main(String[] args){
		// [3, 0, 6, 1, 5]	3
		int[] a = {3, 0, 6, 1, 5};
		int result = solution(a);
		System.out.println(result);
		// 3
	}
	/*
	 * 	과학자가 발표한 논문의 수는 1편 이상 1,000편 이하입니다.
	 *	논문별 인용 횟수는 0회 이상 10,000회 이하입니다.
	 * */
	public static int solution(int[] citations) {
		int answer = getHIndex(citations);
		return answer;
	}

	/**
	 * 1 <= 논문의 수 <= 1,000
	 * 0 <= 인용 횟수 <= 10,000
	 * 논문의 수(n) : citations 의 길이
	 * - h번 이상 인용된 논문이 h편 이상
	 * - 나머지 논문이 h번 이하 인용
	 * 이 중 h 의 최대값이 H-Index
	 * ex) 3,0,6,1,5 -> 논문의 수 5개
	 * 그중 "3" 편의 논문은 3회 이상 인용
	 * "나머지" 2편의 논문은 "3회" 이하 인용
	 * -------
	 * 재해석
	 * h 회 이상 인용된 논문이 h개이상 이라면, 나머지 논문 n개는 h회 이하 인용
	 * 되도록하는 h의 최대값을 구하라
	 * -> h <= citations[x] 인 논문의 수가 h개 이상
	 * */
	public static int getHIndex(int[] citations){
		int totalN = citations.length;
		Arrays.sort(citations);
		int min = citations[0];
		int max = citations[citations.length-1];
		// 인용수가 0 밖에 없다면 0 return
		if(max == 0){
			return 0;
		}
		// 논문의 총 수가 최솟(인용수)값 보다 작다면
		// ex) [10,20,14]
		if(totalN < min){
			// 논문의 수를 반환
			return totalN;
		}
		// 나머지는 min <= totalN
		// h <= citations[x] 인 citations[x] 갯수(n)는 최대 논문 편수를 넘을 수 없다
		// 따라서 0인 경우를 제외하고 1 <= h <= totalN
		int maxH = 0;
		loop:
		for(int n=totalN;n>=1;n--){
			int count = 0;
			for(int i=totalN-1;i>=0;i--){
				if(n <= citations[i]){
					count++;
				}else{
					break;
				}
				if(n == count){
					maxH = n;
					break loop;
				}
			}
		}
		return maxH;
	}
	public static int getHIndex2(int[] citations){
		int totalN = citations.length;
		Arrays.sort(citations);
		// {0,1,3,5,6}
		for(int i = 0; i < totalN; i++){
			// 남은 논문 수 (5-0,5-1,5-4...)
			int h = totalN - i;
			// 처음 만족하는 순간이 최댓값
			if(citations[i] >= h){
				return h;
			}
		}
		return 0;
	}

}
