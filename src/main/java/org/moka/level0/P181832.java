package org.moka.level0;

// https://school.programmers.co.kr/learn/courses/30/lessons/181832

/**
 * Level 0
 * 정수를 나선형으로 배치하기
 * 정답률 52 %
 * */
public class P181832 {
	public static void main(String[] args){
		/*
		4	[[1, 2, 3, 4], [12, 13, 14, 5], [11, 16, 15, 6], [10, 9, 8, 7]]
		5	[[1, 2, 3, 4, 5], [16, 17, 18, 19, 6], [15, 24, 25, 20, 7], [14, 23, 22, 21, 8], [13, 12, 11, 10, 9]]
		*/
		int n = 30;
		int[][] arr = {
			{1, 2, 3, 4},
			{12, 13, 14, 5},
			{11, 16, 15, 6},
			{10, 9, 8, 7}
		};
		int[][] result = solution(n);
		System.out.println();
		for (int[] ints : result) {
			for (int anInt : ints) {
				System.out.print(anInt + " ");
			}
			System.out.println();
		}
	}
	// 양의 정수 n이 매개변수로 주어집니다.
	// n × n 배열에 1부터 n2 까지 정수를 인덱스 [0][0]부터 시계방향 나선형으로 배치한
	// 이차원 배열을 return 하는 solution 함수를 작성해 주세요.
	public static int[][] solution(int n) {
		int[][] answer = new int[n][n];
		/*
			int number = 1;
			int i=0;
			int j=0;
			fillArraySimple(answer,i,j,n,number, n*n);
		*/
		fillArray(answer,n);

		return answer;
	}
	public static void fillArray(int[][] array,int n){
		int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};
		int d = 0, i = 0, j = 0;

		for (int num = 1; num <= n*n; num++) {
			array[i][j] = num;
			int nextI = i + dir[d][0];
			int nextJ = j + dir[d][1];

			// 다음들어갈 arr의 범위를 벗어나거나
			boolean isOver = !(0 <= nextI && nextI < n) || !(0 <= nextJ && nextJ < n);
			// 다음들어갈 arr에 이미 값이 있으면
			boolean isFill = !isOver && array[nextI][nextJ] != 0;
			// 방향을 전환
			if (isOver || isFill) {
				// 방향은 4번씩 반복
				/*
				 * 0 1		// 첫번째 → 세로,가로 방향
				 * 1 0		// 두번째 ↓ 세로,가로 방향
				 * 0 -1		// 세번째 ← 세로,가로 방향
				 * -1 0		// 네번째 ↑ 세로,가로 방향
				 * */
				d = (d + 1) % 4;
			}
			i += dir[d][0];
			j += dir[d][1];
		}
	}
	/**
	 * 간단한 풀이
	 * */
	public static void fillArraySimple(int[][] array, int i, int j, int n, int number,final int maxNumber){
		if(n <= 0 || number > maxNumber) return;
		int max = n == 1 ? 1 : n*n - (n-2)*(n-2);
		int cnt = 1;
		while(cnt <= max && number <= maxNumber){
			array[i][j] = number++;
			// 방향전환
			if(cnt < n){
				j++;
			}else if(cnt < (2*n - 1)){
				++i;
			}else if(cnt < (3*n - 2)){
				j--;
			}else if(cnt < (4*n - 4)){
				i--;
			}else{
				break;
			}
			cnt++;
		}
		// →  ↓  ← ↑ 한바퀴 끝나면 반복
		fillArraySimple(array, i, j+1,n-2,number,maxNumber);
	}
}
