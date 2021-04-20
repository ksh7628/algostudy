package apr.second;

import java.io.*;
import java.util.StringTokenizer;

/**
 * <pre>
 * apr.second 
 * Boj_S1_1991_트리순회.java
 * </pre>
 *
 * @author	: KimSeonhong
 * @date	: 2021. 4. 20.
 * @version	: 0.1
 *
 * 분류: 트리, 재귀
 * 난이도: 실버1
 * 혼자 품: X
 * 풀이: 노드 클래스와 트리 클래스를 만들고 입력값을 받아서 트리를 주어진 조건에 맞게 생성한 후
 *      재귀를 이용하여 전위, 중위, 후위 순회를 각각 처리해주었다.
 * 느낀 점: 트리에 대한 개념만 있지 코드로 구현한 적이 없어서 일단 다른 코드를 보고 이해를 했다.
 *        트리는 중요한 자료구조중 하나이므로 기초 문제를 통해 확실하게 개념을 익혀야겠다.
 */
public class Boj_S1_1991_트리순회 {
	static class Node {// 노드의 정보를 나타내는 클래스
		char data;
		Node left, right;

		Node(char data) {
			this.data = data;
		}
	}

	static class Tree {
		Node root;

		/* 트리에 노드를 추가 */
		public void addNode(char data, char ldata, char rdata) {
			if (root == null) {// 루트가 없다면
				if (data != '.') {// 데이터가 존재한다면 루트에 data값을 가지는 노드 생성
					root = new Node(data);
				}
				if (ldata != '.') {// 왼쪽 자식 노드 데이터가 존재한다면 ldata값을 가지는 왼쪽 자식 노드 생성
					root.left = new Node(ldata);
				}
				if (rdata != '.') {// 오른쪽 자식 노드 데이터가 존재한다면 rdata값을 가지는 오른쪽 자식 노드 생성
					root.right = new Node(rdata);
				}
			} else {// 루트가 존재한다면 탐색
				searchNode(root, data, ldata, rdata);
			}
		}

		/* 재귀를 이용하여 트리 탐색 */
		public void searchNode(Node root, char data, char ldata, char rdata) {
			if (root == null) {// 도착한 노드가 null이면 return
				return;
			}

			if (root.data == data) {// 데이터가 일치한다면
				if (ldata != '.') {// 왼쪽 데이터가 존재한다면 왼쪽에 추가
					root.left = new Node(ldata);
				}
				if (rdata != '.') {// 오른쪽 데이터가 존재한다면 오른쪽에 추가
					root.right = new Node(rdata);
				}
			} else {// 찾지 못했다면
				searchNode(root.left, data, ldata, rdata);
				searchNode(root.right, data, ldata, rdata);
			}
		}

		/* 전위 순회 */
		public void preOrder(Node root) {
			System.out.print(root.data);
			if (root.left != null) {
				preOrder(root.left);
			}
			if (root.right != null) {
				preOrder(root.right);
			}
		}

		/* 중위 순회 */
		public void inOrder(Node root) {
			if (root.left != null) {
				inOrder(root.left);
			}
			System.out.print(root.data);
			if (root.right != null) {
				inOrder(root.right);
			}
		}

		/* 후위 순회 */
		public void postOrder(Node root) {
			if (root.left != null) {
				postOrder(root.left);
			}
			if (root.right != null) {
				postOrder(root.right);
			}
			System.out.print(root.data);
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		Tree tree = new Tree();
		for (int i = 0; i < n; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			char data = st.nextToken().charAt(0);
			char ldata = st.nextToken().charAt(0);
			char rdata = st.nextToken().charAt(0);
			tree.addNode(data, ldata, rdata);
		}
		
		tree.preOrder(tree.root);
		System.out.println();
		tree.inOrder(tree.root);
		System.out.println();
		tree.postOrder(tree.root);
		br.close();
	}
}