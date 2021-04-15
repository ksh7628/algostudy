package apr.second;

import java.io.*;
import java.util.StringTokenizer;

/**
 * <pre>
 * apr.second 
 * Boj_G2_2629_양팔저울.java
 * </pre>
 *
 * @author	: KimSeonhong
 * @date	: 2021. 4. 15.
 * @version	: 0.1
 *
 * 분류: 다이나믹 프로그래밍, 배낭 문제
 * 난이도: 골드2
 * 혼자 품: X
 * 풀이: 배낭 문제를 응용해서 푸는 문제로 그냥 dfs를 돌리는 것이 아닌 기저조건을 잘 세워야 하는 문제이다.
 *      추를 한쪽에 추가하는 경우, 아무데도 안놓는 경우, 반대쪽에 추가하는 경우 세 가지 케이스를 각각 재귀호출하면서
 *      추를 다썻거나 이미 가능한 무게인 경우에 함수 리턴을 하는 방식으로 풀이했다.
 * 느낀 점: 간단한 배낭 문제는 풀만했는데 이 문제는 응용에다가 DP문제를 풀때 생각 한적이 없는 재귀를 기저조건만 잘 세워서 사용하면
 *        시간내에 풀 수 있다는 것을 알게 된 문제였다. DP는 조금만 감을 잃는 순간 아무리 생각해도 못 푸는 경우가 허다했기 때문에
 *        간간히 간단한 문제라도 풀도록 노력해야겠다.
 */
public class Boj_G2_2629_양팔저울 {
	static boolean[][] enable = new boolean[31][15001];// 추를 하나 이상 사용하여 해당 무게를 만들 수 있는지 체크하는 배열
	static int[] w;
	static int N, M;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		w = new int[N + 1];// 배열 크기를 N으로 주게 될 경우 dfs 메서드의 기저조건 순서를 바꿔야함
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			w[i] = Integer.parseInt(st.nextToken());
		}
		dfs(0, 0);// 미리 주어진 추로 모든 경우를 탐색

		M = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < M; i++) {
			int marble = Integer.parseInt(st.nextToken());
			if (marble > 15000) {// 구슬의 무게가 15000이 넘어가면 애초에 만들 수 없으므로 N
				System.out.print("N ");
				continue;
			}

			// 미리 구해둔 값으로 만들 수 있다면 Y, 아니라면 N
			if (enable[N][marble]) {
				System.out.print("Y ");
			} else {
				System.out.print("N ");
			}
		}
		br.close();
	}

	/* 주어진 추를 이용해서 만들 수 있는지 검사 */
	static void dfs(int idx, int weight) {
		// 추의 인덱스를 벗어났거나 이미 만들 수 있다고 체크했다면 return
		if (idx > N || enable[idx][weight]) {
			return;
		}

		enable[idx][weight] = true;// 기저조건에 해당되지 않으므로 만들 수 있다

		dfs(idx + 1, weight + w[idx]);// 한쪽에 추가한 경우
		dfs(idx + 1, weight);// 아무데도 놓지 않은 경우
		dfs(idx + 1, Math.abs(weight - w[idx]));// 반대쪽에 놓은 경우
	}
}