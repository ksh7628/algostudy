package apr.second;

import java.io.*;
import java.util.StringTokenizer;

/**
 * <pre>
 * apr.second 
 * Boj_G3_14890_경사로.java
 * </pre>
 *
 * @author	: KimSeonhong
 * @date	: 2021. 4. 14.
 * @version	: 
 * 
 * 분류: 구현
 * 난이도: 골드3
 * 혼자 품: X
 * 풀이: 최대 2N개의 길중에 경사로를 몇개 놓을 수 있는지 판단해야 되는 문제이다.
 * 	        오르막길 구간과 내리막길 구간의 경사로를 놓을 때 체크하는 방법을 각각 다르게 하여 접근하였다.
 * 	        문제에서 요구하는 여러 조건들에 대해 구현만 잘 하면 풀 수 있는 문제
 * 느낀점: 처음 문제를 풀려고 시도했을 때에는 '_/' 구간과 '\_' 구간의 처리조건을 같게 생각해서 도무지 풀리지가 않았다.
 * 		  그래서 수업시간에 들은 내용을 바탕으로 다시 풀어보았는데 구간별 조건처리만 잘하면 풀 수 있는 문제였다.
 */
public class Boj_G3_14890_경사로 {
	static int[][] map, revmap;// 행 체크 배열, 열 체크 배열
	static int N, L;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		revmap = new int[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				revmap[j][i] = map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		System.out.println(buildRoad());
		br.close();
	}

	/* 경사로를 만들 수 있는지 체크하고 만들 수 있으면 경우의 수 증가 */
	static int buildRoad() {
		int cnt = 0;
		for (int i = 0; i < N; i++) {
			if (makeRoad(map[i])) {// i행 경사로 건설 가능 여부 체크
				++cnt;
			}
			if (makeRoad(revmap[i])) {// i열 경사로 건설 가능 여부 체크
				++cnt;
			}
		}
		return cnt;
	}

	/* 경사로를 만들 수 있는지 판단하는 메서드 */
	static boolean makeRoad(int[] arr) {
		int j = 0, len = 0, height = arr[0];
		while (j < N) {
			if (Math.abs(arr[j] - height) > 1) {// 차이가 1을 초과하면 false
				return false;
			}
			if (height - 1 == arr[j]) {// 내리막길 경사로
				int cnt = 0;
				for (int k = j; k < N; k++) {
					if (arr[k] != height - 1) {
						break;
					}
					if (++cnt == L) {
						break;
					}
				}
				if (cnt < L) {
					return false;
				}
				j += L;
				len = 0;
				--height;
			} else if (height + 1 == arr[j]) {// 오르막길 경사로
				if (len < L) {
					return false;
				}
				++j;
				len = 1;
				++height;
			} else {// 길이가 같음
				++j;
				++len;
			}
		}
		return true;
	}
}