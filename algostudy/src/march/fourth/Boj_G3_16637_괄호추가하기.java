package march.fourth;

import java.io.*;
import java.util.*;

/**
 * <pre>
 * march.fourth 
 * Boj_G3_16637_괄호추가하기.java
 * </pre>
 *
 * @author	: KimSeonhong
 * @date	: 2021. 4. 11.
 * @version	: 
 * 
 * 분류: 브루트포스 알고리즘
 * 난이도: 골드3
 * 혼자 품: X
 * 풀이: N의 길이가 작게 한정되어 있으므로 전형적인 브루트포스로 풀리는 문제
 *		숫자와 연산자가 번갈아서 나오므로 숫자 리스트와 연산자 리스트를 따로 생성 
 *		괄호를 사용할때랑 괄호를 사용하지 않을때를 각각 계산하여 인덱스를 맞춰줌
 * 느낀점: 저번에 시도했을 때 인덱스를 제대로 잡지못해서 해멘 문제
 * 		  인덱스만 주의하면 골3 치곤 쉬운 문제, 다시 풀어봐야 함
 */

public class Boj_G3_16637_괄호추가하기 {
	static List<Integer> numList = new ArrayList<>();// 숫자를 저장하는 리스트
	static List<Character> opList = new ArrayList<>();// 연산자를 저장하는 리스트
	static int N, res = Integer.MIN_VALUE;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		String str = br.readLine();
		for (int i = 0; i < N; i++) {
			if (i % 2 == 0) {// 입력 문자열의 문자인덱스가 짝수이면 숫자 리스트에 저장
				numList.add(str.charAt(i) - '0');
			} else {// 홀수이면 연산자 리스트에 저장
				opList.add(str.charAt(i));
			}
		}
		dfs(0, numList.get(0));// 처음에 제일 앞에 있는 수를 가지고감
		System.out.print(res);
		br.close();
	}

	/* dfs로 모든 경우에 대해 탐색  */
	static void dfs(int idx, int ans) {
		if (idx >= opList.size()) {// 더 이상 계산할 연산자가 없으면 최대값 갱신후 return
			res = Math.max(res, ans);
			return;
		}

		// 괄호를 사용하지 않을때
		int unable = cal(opList.get(idx), ans, numList.get(idx + 1));
		dfs(idx + 1, unable);

		// 괄호를 사용할 때
		if (idx + 1 < opList.size()) {
			int enable = cal(opList.get(idx + 1), numList.get(idx + 1), numList.get(idx + 2));
			int tmp = cal(opList.get(idx), ans, enable);
			dfs(idx + 2, tmp);
		}
	}

	/* +, -, *에 대해 연산 */
	static int cal(char op, int x, int y) {
		int ans = 0;
		switch (op) {
		case '+':
			ans = x + y;
			break;
		case '-':
			ans = x - y;
			break;
		case '*':
			ans = x * y;
			break;
		}
		return ans;
	}
}