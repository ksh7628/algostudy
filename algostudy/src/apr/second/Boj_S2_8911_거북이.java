package apr.second;

import java.io.*;

/**
 * <pre>
 * apr.second 
 * Boj_S2_8911_거북이.java
 * </pre>
 *
 * @author : KimSeonhong
 * @date : 2021. 4. 14.
 * @version :
 * 
 * 분류: 구현, 시뮬레이션
 * 난이도: 실버2
 * 혼자 품: O
 * 풀이: 입력받은 문자열의 각 문자에 따른 명령들을 그대로 수행해나가면  된다.
 *      굳이 배열을 생성하지 않고 이동할때마다 x,y 좌표의 최대 최소값을 각각 구해주면서
 *      마지막에 최소값 x,y와 최대값 x,y의 차를 구한 후 곱해주면 최대 넓이를 가지는 직사각형을 만족하게 된다.
 * 느낀 점: 실버 문제라서 문제 이해도 쉬웠고 그대로 구현하기만 되는 문제여서 딱히 어려운 점은 없었다.
 *        최종 최대값을 가지는 직사각형의 면적을 구할 때 좌표값의 최소 최대값만 알면 넓이를 구할 수 있는 문제였다.
 */
public class Boj_S2_8911_거북이 {
	static int[] dx = { 1, 0, -1, 0 };// 북서남동
	static int[] dy = { 0, -1, 0, 1 };
	static int minRow, minCol, maxRow, maxCol;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= T; tc++) {
			char[] cmd = br.readLine().toCharArray();// 명령어 문자 배열 생성
			turtleBot(cmd);
			sb.append(area()).append("\n");
		}
		System.out.print(sb);
		br.close();
	}

	/* 각 명령어를 수행하는 메서드 */
	static void turtleBot(char[] cmd) {
		minRow = 0;
		minCol = 0;
		maxRow = 0;
		maxCol = 0;
		int dir = 0, x = 0, y = 0;
		for (int i = 0; i < cmd.length; i++) {
			boolean mv = false;
			switch (cmd[i]) {
			case 'F':
				mv = true;
				x += dx[dir];
				y += dy[dir];
				break;
			case 'B':
				mv = true;
				x += dx[(dir + 2) % 4];
				y += dy[(dir + 2) % 4];
				break;
			case 'L':
				dir = (dir + 3) % 4;
				break;
			case 'R':
				dir = (dir + 1) % 4;
				break;
			}
			if (mv) {
				minRow = Math.min(minRow, x);
				minCol = Math.min(minCol, y);
				maxRow = Math.max(maxRow, x);
				maxCol = Math.max(maxCol, y);
			}
		}
	}

	static int area() {
		return (maxRow - minRow) * (maxCol - minCol);
	}
}
