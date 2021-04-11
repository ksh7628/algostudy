package march.fourth;

import java.io.*;
import java.util.*;

/**
 * <pre>
 * march.fourth 
 * Boj_G4_17281_야구.java
 * </pre>
 *
 * @author  : KimSeonhong
 * @date    : 2021. 4. 11.
 * @version :
 * 
 * 분류: 구현, 브루트포스 알고리즘
 * 난이도: 골드4
 * 혼자 품: X
 * 풀이: 순열을 이용해서 타자들의 타석을 지정한 후 문제의 요구사항을 그대로 구현하는 문제
 * 		순열을 생성할 때 주의해야 할 점은 1번선수는 4번타자로 고정되어 있으므로 4번타자를 제외한 나머지 타자들의 순열만 구해주어야 함
 * 느낀점: 이 문제도 저번에 풀지 못해서 다시 시도를 했으나, 구현 부분에서 계속 막히게 되어 다른 사람의 소스코드를 참조하였다.
 * 		  다른 알고리즘을 사용하지 않고 순열 + 구현만으로도 풀 수 있는 문제라서 난이도가 골드4로 책정된것 같다.
 * 		  구현하는 사고력을 더 길러서 다시 풀어봐야 될것 같다.
 */
public class Boj_G4_17281_야구 {
	static int[][] inning;
	static int[] base, batting;
	static boolean[] used;
	static int N, sum, res;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		inning = new int[N][10];
		base = new int[4];
		batting = new int[10];
		used = new boolean[10];
		StringTokenizer st = null;
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 1; j <= 9; j++) {
				inning[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		batting[4] = 1;// 1번 선수는 4번타자로 고정
		used[4] = true;// 순열을 구할때 4번타석은 제외
		perm(2);// 1번 선수를 이미 고정했으므로 2번 선수부터
		System.out.print(res);
		br.close();
	}

	/* 순열 생성 */
	static void perm(int idx) {
		if (idx == 10) {// 하나의 순열을 다 만들었으면 야구 시작
			baseball();
			return;
		}

		for (int i = 1; i <= 9; i++) {
			if (used[i]) {
				continue;
			}
			batting[i] = idx;
			used[i] = true;
			perm(idx + 1);
			used[i] = false;
		}
	}

	/* 입력받은 이닝 정보에 따라 게임을 진행하는 메서드 */
	static void baseball() {
		sum = 0;
		int start = 1;// 1번 타자부터 시작
		for (int i = 0; i < N; i++) {
			int[] tmp = { 0, 0, 0, 0, 0 };// 0번 인덱스: 아웃 횟수, 1~4번 인덱스: 1루, 2루, 3루, 홈까지 진루
			while (tmp[0] < 3) {// 아웃 횟수가 3미만이면 계속 진행
				running(tmp, inning[i][batting[start]]);
				start = (start % 9) + 1;// 마지막 타자까지 공을 쳤으면 다시 1번타자부터
			}
			sum += tmp[4];
		}
		res = Math.max(res, sum);
	}

	/* 각 루에 있는 타자들을 이닝 정보에 따라 진루하는 메서드 */
	static void running(int[] tmp, int num) {
		for (int i = 0; i < num; i++) {
			tmp[4] += tmp[3];
			tmp[3] = tmp[2];
			tmp[2] = tmp[1];
			tmp[1] = 0;
		}
		tmp[num]++;// 타석에 선 선수를 결과에 따라 진출
	}
}
