package mar.fourth;

import java.io.*;
import java.util.*;

public class Boj_G2_17472_다리만들기2 {
	static class Edge implements Comparable<Edge> {
		int from, to, d;

		public Edge(int from, int to, int d) {
			super();
			this.from = from;
			this.to = to;
			this.d = d;
		}

		@Override
		public int compareTo(Edge o) {// 거리의 오름차순으로 정렬 정의
			return Integer.compare(this.d, o.d);
		}
	}

	static ArrayList<Edge> el = new ArrayList<>();// 섬과 섬간의 거리 정보를 저장하는 리스트
	static ArrayList<int[]> al = new ArrayList<>();// 섬의 모서리를 저장하는 리스트
	static int[][] map;
	static boolean[][] visit;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int[] p;// 루트를 저장하는 배열
	static int N, M, num;// 행, 열 , 섬의 번호

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());// 행 길이 입력
		M = Integer.parseInt(st.nextToken());// 열 길이 입력
		map = new int[N][M];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());// 맵 정보를 입력받음
			}
		}

		visit = new boolean[N][M];// 섬을 bfs로 찾기 위해 초기화
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (map[i][j] == 1 && !visit[i][j]) {// 땅을 찾았고 아직 방문을 안했다면 섬을 만듬
					makeIsland(i, j);
				}
			}
		}
		visit = new boolean[N][M];// 섬의 모서리를 bfs로 찾기 위해 초기화
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (map[i][j] == 0 && !visit[i][j]) {// 바다를 찾았고 아직 방문을 안했다면 모서리를 찾자
					searchEdge(i, j);
				}
			}
		}

		makeBridge();// 다리를 만듬
		makeSet();// 루트 배열 초기화
		Collections.sort(el);// 다리 길이의 오름차순으로 정렬
		System.out.print(cruskal());// 크루스칼 알고리즘을 통해 최소 비용값 출력
		br.close();
	}

	/* bfs로 섬의 번호를 마킹 */
	static void makeIsland(int x, int y) {
		ArrayDeque<int[]> q = new ArrayDeque<>();
		map[x][y] = ++num;// 섬 번호 마킹
		visit[x][y] = true;
		q.offer(new int[] { x, y });
		while (!q.isEmpty()) {
			int[] cur = q.poll();
			for (int dir = 0; dir < 4; dir++) {
				int nx = cur[0] + dx[dir];
				int ny = cur[1] + dy[dir];
				if (check(nx, ny) || visit[nx][ny]) {
					continue;
				}

				if (map[nx][ny] == 1) {
					map[nx][ny] = num;// 섬 번호 마킹
					visit[nx][ny] = true;
					q.offer(new int[] { nx, ny });
				}
			}
		}
	}

	/* 섬의 가장자리를 bfs로 찾아서 리스트에 추가 */
	static void searchEdge(int x, int y) {
		ArrayDeque<int[]> q = new ArrayDeque<>();
		visit[x][y] = true;
		q.offer(new int[] { x, y });
		while (!q.isEmpty()) {
			int[] cur = q.poll();
			for (int dir = 0; dir < 4; dir++) {
				int nx = cur[0] + dx[dir];
				int ny = cur[1] + dy[dir];
				if (check(nx, ny) || visit[nx][ny]) {
					continue;
				}
				if (map[nx][ny] >= 1) {// 해당 좌표가 섬이라면 리스트에 추가
					al.add(new int[] { nx, ny });
					continue;
				}
				visit[nx][ny] = true;
				q.offer(new int[] { nx, ny });
			}
		}
	}

	/* 섬과 섬간의 정보와 다리의 길이를 리스트에 저장 */
	static void makeBridge() {
		for (int[] a : al) {// 리스트 순회
			for (int dir = 0; dir < 4; dir++) {
				int nx = a[0] + dx[dir];
				int ny = a[1] + dy[dir];
				int len = 0;// 다리 길이 변수
				while (true) {// 현재 모서리에서 출발하여 다음 섬까지의 거리를 계산
					if (check(nx, ny)) {// 배열 범위 벗어나면 탐색 중단
						break;
					}
					if (map[nx][ny] > 0) {// 다음 섬을 찾았고 길이가 2 이상이면 리스트에 추가
						if (len > 1) {
							el.add(new Edge(map[a[0]][a[1]], map[nx][ny], len));
						}
						break;
					}
					++len;
					nx += dx[dir];
					ny += dy[dir];
				}
			}
		}
	}

	/* 크루스칼 알고리즘 */
	static int cruskal() {
		int res = 0, cnt = 0;
		for (Edge e : el) {
			if (union(e.from, e.to)) {
				res += e.d;
				if (++cnt == num - 1) {// 모든 연결이 끝났다면 최소 비용 return
					return res;
				}
			}
		}
		return -1;// 모든 섬을 연결하지 못했다면 -1 return
	}

	/* 루트 배열 초기화 */
	static void makeSet() {
		p = new int[num + 1];
		for (int i = 1; i <= num; i++) {
			p[i] = i;
		}
	}

	/* a의 루트를 find */
	static int find(int a) {
		if (a == p[a]) {
			return a;
		}
		return p[a] = find(p[a]);
	}

	/* a와 b루트가 다르면 b의 루트를 a로 변경 */
	static boolean union(int a, int b) {
		int x = find(a);
		int y = find(b);
		if (x == y) {
			return false;
		}
		p[y] = x;
		return true;
	}

	/* 배열 범위 체크 */
	static boolean check(int x, int y) {
		return x < 0 || x >= N || y < 0 || y >= M;
	}
}