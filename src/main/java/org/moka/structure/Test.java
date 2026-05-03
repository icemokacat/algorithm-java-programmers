package org.moka.structure;

import java.util.ArrayList;

import org.moka.level3.P150367;
import org.moka.structure.base.RunTimer;
import org.moka.structure.utils.BSTUtil;

public class Test {
	public static void main(String[] args){
		int[] numbers = {0,1,2,3,4,5,6};
		BinarySearchTree.Tree tree = new BinarySearchTree.Tree(3);
		int midIndex = numbers.length / 2;

		BSTUtil.insertMiddle(tree, numbers, 0, midIndex - 1);
		BSTUtil.insertMiddle(tree, numbers, midIndex + 1, 7 - 1);

		BSTUtil.print(tree);

	}

}
