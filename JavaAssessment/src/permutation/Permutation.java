package permutation;

import java.util.HashSet;
import java.util.Set;

public class Permutation {

	public static int solution(int A[], int N) {
		Set<Integer> set = new HashSet<>();
		if (A.length < N || A.length > N) return 0;
		for (int a : A) {
			if (set.contains(a)) return 0;
			else {
				set.add(a);
			}
		}
		return 1;
	}
	
	public static void main(String[] args) {
		int sol = solution(new int[]{3, 2, 1}, 4);
		System.out.println(sol);
	}

}
