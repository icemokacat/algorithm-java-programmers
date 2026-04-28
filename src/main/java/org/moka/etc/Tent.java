package org.moka.etc;

import java.util.ArrayList;
import java.util.Arrays;

// #2304, #14719
// https://www.acmicpc.net/problem/2304

public class Tent {

	/**
	 * 텐트 세우기
	 * --
	 * 😊문제
	 * N 개의 막대 기둥이 일렬로 세워져 있다.
	 * 기둥들의 폭은 모두 1 m이며 높이는 다를 수 있다.
	 * 이 기둥들을 이용하여 양철로 된 창고를 제작하려고 한다.
	 * 창고에는 모든 기둥이 들어간다.
	 * 이 창고의 지붕을 다음과 같이 만든다.
	 * --
	 * 지붕은 수평 부분과 수직 부분으로 구성되며, 모두 연결되어야 한다.
	 * 지붕의 수평 부분은 반드시 어떤 기둥의 윗면과 닿아야 한다.
	 * 지붕의 수직 부분은 반드시 어떤 기둥의 옆면과 닿아야 한다.
	 * 지붕의 가장자리는 땅에 닿아야 한다.
	 * 비가 올 때 물이 고이지 않도록 지붕의 어떤 부분도 오목하게 들어간 부분이 없어야 한다.
	 * 그림 1은 창고를 옆에서 본 모습을 그린 것이다.
	 * 이 그림에서 굵은 선으로 표시된 부분이 지붕에 해당되고, 지붕과 땅으로 둘러싸인 다각형이 창고를 옆에서 본 모습이다.
	 * 이 다각형을 창고 다각형이라고 하자.
	 * --
	 * 그림1 (링크 참고)
	 * */
	 // https://github.com/user-attachments/assets/b0c4fedf-df0a-4421-b814-d2d186e17980
	 /**
	 * ⚡  입력
	 * 	 첫 줄에는 기둥의 개수를 나타내는 정수 N이 주어진다.
	 * 	 N은 1 이상 1,000 이하이다.
	 * 	 그 다음 N 개의 줄에는 각 줄에 각 기둥의 왼쪽 면의 위치를 나타내는 정수 L과
	 * 	 높이를 나타내는 정수 H가 한 개의 빈 칸을 사이에 두고 주어진다.
	 * 	 L과 H는 둘 다 1 이상 1,000 이하이다.
	 * --
	 * ✨ 출력
	 *  첫 줄에 창고 다각형의 면적을 나타내는 정수를 출력한다.
	 * --
	 * 🛰️ 예시입력1
	 * 7
	 * 2 4
	 * 11 4
	 * 15 8
	 * 4 6
	 * 5 3
	 * 8 10
	 * 13 6
	 * --
	 * 예시답 : 98
	 * */
	 public static void main(String[] args) {
		 // 1 <= n <= 1,000
		 int n = 7;
		 // 1 <= L,H <= 1,000
		 /*int[][] vals = {
				 {2, 4},
				 {11, 4},
				 {15, 8},
				 {4, 6},
				 {5, 3},
				 {8, 10},
				 {13, 6}
		 };*/
		 int[][] vals = {
			 {24, 6},
			 {16, 14},
			 {2, 5},
			 {4, 10},
			 {5, 4},
			 {8, 2},
			 {11,6},
			 {14,8},
			 {18,9},
			 {20,11},
			 {21,14},
			 {22,9},
		 };
		 int result1 = solution1(vals);
		 System.out.println("완전탐색 결과 {" + result1 + "}");
	 }

	 public static int solution1(int[][] vals){
		 int len = vals.length;
		 int result = 0;
		 int maxH = 0;
		 // 최대 값 기둥 리스트를 먼저 만든다.
		 // A. 최대값을 먼저 구한다.
		 // 구하면서 첫번째 기둥과 마지막 기둥 위치를 구한다.
		 int minL = 1001;
		 int maxL = 0;
		 for (int[] val : vals) {
			 // 높이
			 int H = val[1];
			 // 좌표
			 int L = val[0];
			 maxH = Math.max(maxH, H);
			 minL = Math.min(minL, L);
			 maxL = Math.max(maxL, L);
		 }
		 // B. 최대값인 기둥의 X 좌표의 최소값과 최대값을 구한다.
		 int minX = 1001;
		 int maxX = 0;
		 for (int[] val : vals) {
			 int L = val[0];
			 int H = val[1];
			 if (H == maxH) {
				 minX = Math.min(minX, L);
				 maxX = Math.max(maxX, L);
			 }
		 }
		 // C. 최대값인 기둥의 면적을 구한다
		 // 이때 2개 이상이라도 면적은 동일하게 구한다 (움푹 파이면 안되므로)
		 result += (maxX - minX + 1) * maxH;
		 // C2. 이때 최대값인 기둥이 양측 끝에 있으면 그게 면적이다.
		 if(minX == minL && maxX == maxL){
			 return result;
		 }
		 // D. 최대 값인 기둥을 양측으로 배열을 2개 생성한다.
		 int leftLen 	= minX - minL;
		 int rightLen 	= maxL - maxX;

		 // D1. 좌측 배열을 구성한다.
		 int[] leftArr = new int[leftLen];
		 for (int[] val : vals) {
			 int L = val[0];
			 int H = val[1];
			 if (L < minX) {
				 leftArr[L - minL] = H;
			 }
		 }
		 System.out.println("좌측 배열 : " + Arrays.toString(leftArr));
		 // D2. 우측 배열을 구성한다.
		 int[] rightArr = new int[rightLen];
		 for (int[] val : vals){
			 int L = val[0];
			 int H = val[1];
			 if (L > maxX){
				 rightArr[L - maxX - 1] = H;
			 }
		 }
		 System.out.println("우측 배열 : " + Arrays.toString(rightArr));

		 // E. 각 측에서 텐트의 크기를 계산한다.
		 // E1. 좌측 텐트의 경우 왼쪽에서 오른쪽으로 진행하면서 값을 더한다.
		 // 이때, 높이값이 이전 보다 높아지는 경우 그 높이가 있는 X 좌표를 기준으로 가로를 잰다.
		 // ex) 4, 0, 6, 3, 0, 0 => 4*2 + 6*4 (마지막은 무조건 곱셉 수행)
		 int leftMaxH = leftArr[0];
		 int w1 = 0;
		 for (int i=0;i<leftLen;i++){
			 int H = leftArr[i];
			 if (H > leftMaxH){
				 // 높이가 증가하는 경우, 이전 높이까지의 면적을 계산한다.
				 result += leftMaxH * w1;
				 System.out.println("좌측 텐트 면적 계산 : " + leftMaxH + " * " + w1 + " = " + (leftMaxH * w1));
				 w1 = 0;
				 leftMaxH = H;
			 }
			 w1++;
			 // 마지막인 경우 면적 계산
			 if (i == leftLen - 1) {
				 result += leftMaxH * w1;
				 System.out.println("좌측 텐트 면적 계산 (마지막) : " + leftMaxH + " * " + w1 + " = " + (leftMaxH * w1));
			 }
		 }
		 // E2. 우측 텐트의 면적을 계산한다.
		 // 우측은 역순으로 탐색한다.
		 int rightMaxH = rightArr[rightLen-1];
		 int w2 = 0;
		 for (int i=rightLen-1;i>=0;i--){
			 int H = rightArr[i];
			 if (H > rightMaxH){
				 // 높이가 증가하는 경우, 이전 높이까지의 면적을 계산한다.
				 result += rightMaxH * w2;
				 System.out.println("우측 텐트 면적 계산 : " + rightMaxH + " * " + w2 + " = " + (rightMaxH * w2));
				 rightMaxH = H;
				 w2 = 0;
			 }
			 w2++;
			 // 마지막인 경우 면적 계산
			 if (i == 0) {
				 result += rightMaxH * w2;
				 System.out.println("우측 텐트 면적 계산 (마지막) : " + rightMaxH + " * " + w2 + " = " + (rightMaxH * w2));
			 }
		 }

		 return result;
	 }


}
