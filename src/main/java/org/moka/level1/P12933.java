package org.moka.level1;

//https://school.programmers.co.kr/learn/courses/30/lessons/12933

/**
 * Level 1
 * 정수 내림차순으로 배치하기
 * 정답률 88 %
 * */
public class P12933 {
	/*
	 * 함수 solution은 정수 n을 매개변수로 입력받습니다.
	 * n의 각 자릿수를 큰것부터 작은 순으로 정렬한 새로운 정수를 리턴해주세요.
	 * 예를들어 n이 118372면 873211을 리턴하면 됩니다.
	 * --
	 * 제한 조건
	 * n은 1이상 8000000000 이하인 자연수입니다.
	 * */
	public static void main(String[] args) {
		// 118372	873211
		long n = 118372;
		System.out.println(solution(n));
	}

	public static long solution(long n) {
		if(1 <= n && n <= 9){
			return n;
		}
		// 자릿수
		// 10^(k-1) = n (나머지값을 제외한 자릿수)
		// -> log10(n) = k-1
		// -> (int) log10(n) + 1 = k
		int size = (int) Math.log10(n) + 1;
		int[] arr = toIntArray(n, size);
		// 정렬
		quickSort(arr, 0, size-1);
		// 변환 후 반환
		return toLong(arr, size);
	}

	public static int[] toIntArray(long n, int size) {
		if (n == 0)
			return new int[] {0};
		int[] arr = new int[size];
		for (int i = size - 1; i >= 0; i--) {
			arr[i] = (int)(n % 10);
			n /= 10;
		}
		return arr;
	}
	public static long toLong(int[] arr, int size){
		long num = 0;
		for(int i=0;i < size;i++){
			num = num * 10 + arr[i];
		}
		return num;
	}

	/**
	 * 퀵정렬
	 * -> 분할 정복 기법을 사용한 알고리즘, pivot 을 기준으로 작은값/큰값으로 분할 반복
	 * -> 값이 같은 원소의 순서를 보장하지 않는 불안정 정렬
	 * -> 평균 시간복잡도 O(n log n), 최악 O(n^2) (pivot 이 항상 최솟값/최댓값일때)
	 * -> 평균적으로 합병정렬보다 빠름 (캐시 효율, 추가 메모리 불필요)
	 * */
	public static void quickSort(int[] arr, int low, int high) {
		// 9. 크기가 0이나 1이면 종료
		if (low >= high)
			return;

		// 1. pivot 선택 (가운데)
		int mid = (low + high) / 2;
		int pivot = arr[mid];

		int left = low;
		int right = high;

		while (left <= right) {
			while (arr[left] > pivot)
				left++;
			while (arr[right] < pivot)
				right--;
			if (left <= right) {
				swap(left, right, arr);
				left++;
				right--;
			}
		}
		quickSort(arr, low, right);
		quickSort(arr, left, high);
	}

	public static void swap(int a, int b, int[] arr) {
		int temp = arr[a];
		arr[a] = arr[b];
		arr[b] = temp;
	}
}
