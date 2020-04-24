package rotateArray;

import java.util.Arrays;

public class RotateArray {

	public static int[] solution(int A[], int N, int K) {
	
		if (N == 0) return A;
		K = K % N;
		if (K == 0) return A;
		int[] ans = new int[N];
		int idx = 0;
		while (idx < N) {
			for (int i = N - K; i < N; i++) {
				ans[idx++] = A[i];
			}
			for (int j = 0; j < N - K; j++) {
				ans[idx++] = A[j];
			}
		}
		
		return ans;
	}
	public static void main(String[] args) {
		int[] sol = solution(new int[]{3, 8, 9, 7, 6}, 5, 11);
		
		System.out.println(Arrays.toString(sol));
		
	}

}
