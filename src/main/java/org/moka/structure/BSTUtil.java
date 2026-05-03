package org.moka.structure;

public class BSTUtil {

    // ─────────────────────────────────────────────────────
    // maxCharLen 계산
    // ─────────────────────────────────────────────────────
    private static int calcMaxCharLen(BinarySearchTree.Node node) {
        if (node == null) return 0;
        int cur = String.valueOf(node.value).length();
        return Math.max(cur, Math.max(
            calcMaxCharLen(node.left),
            calcMaxCharLen(node.right)
        ));
    }

    // ─────────────────────────────────────────────────────
    // 슬롯 배열 구성
    //
    // 1-based 인덱스:  루트=1, left=i*2, right=i*2+1
    //
    // slots[i]  = 해당 위치 Node (없으면 null)
    // isNull[i] = true  → 실제 null 자리 → N 출력
    //           = false → dead 자리       → * 출력
    // ─────────────────────────────────────────────────────
    private static void fillSlots(BinarySearchTree.Node node, int idx,
        BinarySearchTree.Node[] slots, boolean[] isNull) {
        if (idx >= slots.length) return;

        if (node == null) {
            isNull[idx] = true;
            // 자식은 dead → 기본값(false) 그대로
            return;
        }

        slots[idx] = node;
        fillSlots(node.left,  idx * 2,     slots, isNull);
        fillSlots(node.right, idx * 2 + 1, slots, isNull);
    }

    // ─────────────────────────────────────────────────────
    // 슬롯 하나 → 문자열 변환
    //
    // 결과 길이 = padLen + maxCharLen + padLen
    //
    // 노드 있음 →  ***17**  (우측정렬, 앞 * 채움)
    // 실제 null →  ****N**  (N 우측정렬)
    // dead      →  *******  (* 로만 채움)
    // ─────────────────────────────────────────────────────
    private static String slotToStr(BinarySearchTree.Node node, boolean isNullSlot,
        int padLen, int maxCharLen) {
        String pad = "*".repeat(padLen);

        String inner;
        if (node != null) {
            String num = String.valueOf(node.value);
            inner = "*".repeat(maxCharLen - num.length()) + num;
        } else if (isNullSlot) {
            inner = "*".repeat(maxCharLen - 1) + "N";
        } else {
            inner = "*".repeat(maxCharLen);
        }

        return pad + inner + pad;
    }

    // ─────────────────────────────────────────────────────
    // 피라미드 출력
    // ─────────────────────────────────────────────────────
    public static void print(BinarySearchTree.Tree tree) {
        int depth = tree.getMaxDepth();
        if (depth == 0) return;
        if (depth == 1) { System.out.println(tree.root.value); return; }

        int maxCharLen = calcMaxCharLen(tree.root);

        // 슬롯 배열: 1-based, 크기 = 2^depth
        int size = (int) Math.pow(2, depth);
        BinarySearchTree.Node[]  slots  = new BinarySearchTree.Node[size];
        boolean[]   isNull = new boolean[size];
        fillSlots(tree.root, 1, slots, isNull);

        // 레벨별 노드칸 너비
        // w(lastLevel) = maxCharLen      (padLen = 0)
        // w(d)         = w(d+1) * 2 + 2  (+2 = 구분자 || 2자리)
        int[] nodeWidth = new int[depth];
        nodeWidth[depth - 1] = maxCharLen;
        for (int d = depth - 2; d >= 0; d--) {
            nodeWidth[d] = nodeWidth[d + 1] * 2 + 2;
        }

        for (int level = 0; level < depth; level++) {
            boolean isLast = (level == depth - 1);
            int padLen = isLast ? 0 : (nodeWidth[level] - maxCharLen) / 2;

            int from = (int) Math.pow(2, level);
            int to   = from * 2 - 1;

            StringBuilder sb = new StringBuilder();
            for (int i = from; i <= to; i++) {
                if (i > from) {
                    // 양쪽 모두 dead → ** / 그 외 → ||
                    boolean leftDead  = (slots[i-1] == null && !isNull[i-1]);
                    boolean rightDead = (slots[i]   == null && !isNull[i]);
                    sb.append((leftDead && rightDead) ? "**" : "||");
                }
                sb.append(slotToStr(slots[i], isNull[i], padLen, maxCharLen));
            }
            System.out.println(sb);
        }
    }

    // ─────────────────────────────────────────────────────
    // 꽉 찬 균형 BST 생성
    // 정렬된 배열의 중간값을 루트로 재귀 삽입
    // ─────────────────────────────────────────────────────
    public static BinarySearchTree.Tree generateFull(int depth) {
        int nodeCount = (int) Math.pow(2, depth) - 1;

        int[] arr = new int[nodeCount];
        for (int i = 0; i < nodeCount; i++) arr[i] = i + 1;

        int mid = nodeCount / 2;
        BinarySearchTree.Tree tree = new BinarySearchTree.Tree(arr[mid]);

        insertMiddle(tree, arr, 0, mid - 1);
        insertMiddle(tree, arr, mid + 1, nodeCount - 1);

        return tree;
    }

    private static void insertMiddle(BinarySearchTree.Tree tree, int[] arr, int left, int right) {
        if (left > right) return;
        int mid = (left + right) / 2;
        tree.insert(arr[mid]);
        insertMiddle(tree, arr, left, mid - 1);
        insertMiddle(tree, arr, mid + 1, right);
    }
}
