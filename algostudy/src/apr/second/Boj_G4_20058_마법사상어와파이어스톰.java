package apr.second;

import java.io.*;
import java.util.*;

/**
 * <pre>
 * apr.second 
 * Boj_G4_20058_마법사상어와파이어스톰.java
 * </pre>
 *
 * @author : KimSeonhong
 * @date : 2021. 4. 19.
 * @version : 0.2
 *
 * 분류: 구현, 그래프 이론, 그래프 탐색, 시뮬레이션 
 * 난이도: 골드4 
 * 혼자 품: O 
 * 풀이: 구현 + bfs로 풀이했다. 먼저 맵의 정보와 단계들을 배열에 저장한 후 문제에서 제시한 단계별로 배열을 시계방향 90도로 회전시키고
 *      인접한 배열들을 체크하여 얼음의 양을 임시 배열에 갱신한 후 원본 배열에 복사해주었다. 그리고 얼음의 양을 세어주고 bfs를 통해
 *      가장 많은 얼음들이 연결된 덩어리의 얼음의 개수를 세어주었다.
 * 느낀 점: 처음에는 배열 복사를 최대한 피하고 스왑 방식을 통해 배열들의 위치만 바꿔줄려 했으나 시간이 지나면서 올바른 로직을 찾지 못해
 *        결국 복사를 해주는 식으로 풀었다. 배열 N의 크기가 최대 64이므로 복사를 해도 시간복잡도가 많이 커지지 않기 때문에
 *        풀렸던 문제였다. 복사를 하지않고 스왑 방식으로도 로직을 잘짜면 풀 수 있을거 같아서 다른 코드를 참조하고 다시 시도해봐야겠다는
 *        생각이 드는 문제였다.
 */
public class Boj_G4_20058_마법사상어와파이어스톰 {
	static int[][] map, tmp;
	static boolean[][] visit;
	static int[] dx = { -1, 1, 0, 0 };// 상하좌우
	static int[] dy = { 0, 0, -1, 1 };
	static int N, Q, area;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = 1 << Integer.parseInt(st.nextToken());// 배열의 크기: N = 2^n
		Q = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		tmp = new int[N][N];
		int[] step = new int[Q];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < Q; i++) {
			step[i] = Integer.parseInt(st.nextToken());
			firestorm(step[i]);// Q번의 파이어스톰 시전
		}

		visit = new boolean[N][N];
		for (int i = 0; i < N; i++) {// 모든 맵을 탐색해서 얼음덩어리 찾으러 감
			for (int j = 0; j < N; j++) {
				if (visit[i][j] || map[i][j] == 0) {
					continue;
				}
				bfs(i, j);
			}
		}

		System.out.println(resultSum());
		System.out.println(area);
		br.close();
	}

	/* 남아있는 얼음의 총합 */
	private static int resultSum() {
		int sum = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				sum += map[i][j];
			}
		}
		return sum;
	}

	/* bfs를 사용하여 남아있는 얼음 중 가장 큰 덩어리가 차지하는 개수를 구함 */
	private static void bfs(int x, int y) {
		int cnt = 1;
		ArrayDeque<int[]> q = new ArrayDeque<>();
		visit[x][y] = true;
		q.offer(new int[] { x, y });
		while (!q.isEmpty()) {
			int[] cur = q.poll();
			for (int dir = 0; dir < 4; dir++) {
				int nx = cur[0] + dx[dir];
				int ny = cur[1] + dy[dir];
				if (check(nx, ny) || visit[nx][ny] || map[nx][ny] == 0) {
					continue;
				}
				visit[nx][ny] = true;
				q.offer(new int[] { nx, ny });
				++cnt;
			}
		}
		area = Math.max(area, cnt);
	}

	/* 단계별 파이어스톰 */
	private static void firestorm(int size) {
		int m = 1 << size;// 격자 구간
		if (size > 0) {// 파이어스톰 시전
			for (int i = 0; i < N; i += m) {
				for (int j = 0; j < N; j += m) {
					rotate(i, j, m);
				}
			}
		}
		iceCheck();
	}

	/* 격자 구간별로 시계방향 90도 회전 */
	private static void rotate(int row, int col, int size) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				tmp[j][size - 1 - i] = map[row + i][col + j];// 90도 회전
			}
		}

		// 회전한 배열을 다시 원래 배열에 복사
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				map[row + i][col + j] = tmp[i][j];
			}
		}
	}

	/* 얼음의 양을 체크 */
	private static void iceCheck() {
		int[][] arr = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (map[i][j] == 0) {// 얼음이 없는 칸이면 인접한 칸을 체크할 필요가 없다
					continue;
				}
				int cnt = 0;// 인접한 칸 중 얼음이 없는 칸의 개수
				for (int dir = 0; dir < 4; dir++) {
					if (cnt > 1) {// 얼음이 없는 칸이 2개 이상이면 더 볼 필요가 없음
						break;
					}
					int nx = i + dx[dir];
					int ny = j + dy[dir];
					if (check(nx, ny) || map[nx][ny] == 0) {// 범위를 벗어났거나 얼음이 없다면 개수 증가
						++cnt;
						continue;
					}
				}
				if (cnt > 1) {// 인접한 칸중 얼음이 있는 칸이 3개 미만이면 얼음의 양을 1 줄어들게 함
					arr[i][j] = map[i][j] - 1;
				} else {
					arr[i][j] = map[i][j];
				}
			}
		}
		copyArray(arr);
	}

	/* 배열 범위 체크 */
	private static boolean check(int x, int y) {
		return x < 0 || x >= N || y < 0 || y >= N;
	}

	/* 배열 복사 */
	private static void copyArray(int[][] arr) {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				map[i][j] = arr[i][j];
			}
		}
	}
}