package org.moka.structure;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * 정렬 알고리즘
 * */
public class SortAlgorithm {
	public static void swap(int a,int b,int[] arr){
		int temp = arr[a];
		arr[a] = arr[b];
		arr[b] = temp;
	}
	/**
	 * 버블정렬
	 * 시간복잡도 O(n^2)
	 * -> 정렬이 어느정도 되어있으면 횟수가 줄어듦
	 * */
	public static void bubbleSort(int[] arr){
		int n = arr.length;
		boolean swapped;
		for( int i = 0; i<n; i++){
			swapped = false;
			for(int j=0;j<n-i-1;j++){
				if(arr[j] > arr[j+1]) {
					swap(j,j+1,arr);
					swapped = true;
				}
			}
			if (!swapped) break;
		}
	}// end bubbleShort
	/**
	 * 선택정렬
	 * 시간복잡도 O(n^2)
	 *  -> 정렬상태와 상관없이 시간이 소요됨
	 * */
	public static void selectionSort(int[] arr) {
		int n = arr.length;

		for (int i = 0; i < n; i++) {
			int minIndex = i;
			for (int j = i + 1; j < n; j++) {
				if (arr[j] < arr[minIndex]) {
					minIndex = j;
				}
			}
			swap(i,minIndex,arr);
		}
	} // end selectionSort
	/**
	 * 삽입 정렬
	 * 시간복잡도 O(n^2)
	 *  -> 역시 최악의 경우 O(n^2) 이지만 어느정도 정렬되어 있을때 버블,선택보다 효율적이다
	 * */
	public static void insertionSort(int[] arr) {
		for (int i = 1; i < arr.length; i++) {
			int key = arr[i];

			int j = i - 1;

			// key 의 왼쪽
			int target = arr[j];

			// target이 존재 (index가 0보다 큼) 해야 하고
			// 왼쪽이 오른쪽 보다 큰경우 (swap 을 해야 하는 경우)
			while (j >= 0 && target > key) {
				// 현재 target 값을 오른쪽 으로 이동
				arr[j + 1] = target;
				// 비교대상을 왼쪽으로
				j--;
			}
			// 끝났으니 비교대상을 오른쪽으로 이동
			arr[j + 1] = key;
		}
	}

	/**
	 * 합병정렬
	 *  -> 분할 정복 기법을 사용한 알고리즘, 데이터가 정렬되어 있지 않아도 성능이 일관적임
	 *  -> 값이 같은 원소의 순서를 유지하는 안정정렬 (동점자 처리,동명이인 등 애초에 같은 값일때 원래의 정렬 기준에 맞게 순서 보장)
	 *  시간복잡도 (n Log n)
	 * */
	public static void mergeSort(int[] arr, int left, int right) {
		// left 	: 배열의 시작점
		// right 	: 배열의 끝점
		// 쪼갤 수 있을만큼 쪼개기 때문 (mid 혹은 mid+1 로 중간지점이 left 로 업데이트 되므로)
		if (left >= right) return;
		// 현재기준 중앙지점의 index 를 찾고
		int mid = (left + right) / 2;
		// 왼쪽을 다시 mergeSort (left~mid)
		mergeSort(arr, left, mid);
		// 오른쪽을 다시 mergeSort (mid~right)
		mergeSort(arr, mid + 1, right);
		// 이후 요소를 sort and merge 한다.
		// 이 merge 함수는 더이상 쪼갤 수 없을때 처음 실행된다.
		// -> 실제로 쪼개는 것이 아닌 지점을 잡는 것 (내부 mergeSort 자체는)
		merge(arr, left, mid, right);
	}
	public static void merge(int[] arr, int left, int mid, int right) {
		// 임시 배열을 생성한다.
		// 이때 배열이 {64, 34, 25, 12, 90, 22, 14} 이라면
		// 최초의 right 는 1
		// 최초의 left 는 0
		// 최초의 mid 는 0
		// 배열의 크기는 1-0+1 = 2 가 된다 (64와 34)
		int[] temp = new int[right - left + 1];
		int i = left, j = mid + 1, k = 0;

		// 예시 왼쪽배열 		{12,14,22,25}
		// 		오른쪽배열	{34,64,90}
		// => 배열이 2개가 아니라 2개로 생각해서 기준점을 잡고 나눈 것
		// 준비된 배열 {0,0,0,0,   0,0,0}
		while (i <= mid && j <= right) {
			// 두 배열 중 가장 작은 값을 순서대로 temp 에 넣는다
			// 1. 12 <= 34 ? => arr[0] = 12 (L[0]) (이후 왼쪽 포인트를 +1)
			// 2. 14 <= 34 ? => arr[1] = 14 (L[1])
			// 3. 22 <= 34 ? => arr[2] = 22 (L[2])
			// 4. 25 <= 34 ? => arr[3] = 25 (L[3])
			// 만약 25 다음에 50 이 있다면
			// if 50 <= 34 ? 만족하지 않으므로
			if (arr[i] <= arr[j]) temp[k++] = arr[i++];
			// else 로 빠져서 => arr[4] = 34 (R[0]) 가 되는 것 (L[j] 에서 j 가 한번도 증가하지 않았으므로)
			else temp[k++] = arr[j++];
		}
		// 왼쪽을 담고
		while (i <= mid) temp[k++] = arr[i++];
		// 오른쪽을 담는다
		while (j <= right) temp[k++] = arr[j++];

		System.arraycopy(temp, 0, arr, left, temp.length);
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
		if (low >= high) return;

		// 1. pivot 선택 (가운데)
		int mid   = (low + high) / 2;
		// int firstPivot = arr[low]; 	// 첫번째를 피봇으로
		// int lastPivot = arr[high];	// 마지막을 피봇으로
		// int randomPivot = arr[low + (int)(Math.random() * (high - low + 1))];
		int pivot = arr[mid];

		int left  = low;
		int right = high;

		// 교차하기전 까지 반복 (left right 포인터를 이동 및 정복)
		while (left <= right) {
			// 2. left  → 오른쪽으로 이동하며 pivot 보다 큰 수 탐색
			//    [1, 3, 5, 8, 4, 2]
			//         →→↑
			while (arr[left]  < pivot) left++;
			// -> left >= pivot (5) 인 순간 left 가 멈춤 (왼쪽이 pivot 보다 큰 경우 -> 그러면 안되니깐)

			// 3. right → 왼쪽으로 이동하며 pivot 보다 작은 수 탐색
			//    [1, 3, 5, 8, 4, 2]
			//                    ↑←←←
			while (arr[right] > pivot) right--;
			// -> pivot(5) >= right  인 순간 right 가 멈춤

			// 5. left 와 right 가 교차하지 않았으면
			if (left <= right) {
				// 4. 교환
				//    [1, 3, 5, 8, 4, 2]
				//           ↑ ←swap→ ↑
				swap(left, right, arr);
				left++;
				right--;
				// => [1, 3, 2, 8, 4, 5]
			}
		}
		// 6. 교차 후 pivot 기준으로 분할 완료
		//    left 기준으로 왼쪽은 pivot 보다 작은값, 오른쪽은 큰값
		//    [1, 3, 2, 4, | 8, 5]
		//           ↑right(4,index=3) ↑left(8,index=4)
		//              분할선

		// 8. 왼쪽/오른쪽 재귀
		//    [low ~ right] | [left ~ high]
		//	[1, 3, 2, 4]
		quickSort(arr, low,  right); // ← 왼쪽 구간
		//  [8, 5]
		quickSort(arr, left, high);  // → 오른쪽 구간
	}

	public static void main(String[] args){
		//int[] data = {64, 34, 25, 12, 90, 22, 14};
		int[] data = {1, 3, 5, 8, 4, 2};
		quickSort(data,0,data.length-1);
		System.out.println(Arrays.toString(data));
	}

}
