package com.bob.wanandroid.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * created by cly on 2022/2/25
 */
public class JavaTest {

    @org.junit.Test
    public void test() {
//        new AA();
//        add(0);

        RecursionTest.TreeNode threeLeftNode = new RecursionTest.TreeNode(15);
        RecursionTest.TreeNode threeRightNode = new RecursionTest.TreeNode(7);
        RecursionTest.TreeNode twoLeftNode = new RecursionTest.TreeNode(9);
        RecursionTest.TreeNode twoRightNode = new RecursionTest.TreeNode(20, threeLeftNode, threeRightNode);
        RecursionTest.TreeNode rootNode = new RecursionTest.TreeNode(3, twoLeftNode, twoRightNode);

//        List<Double> doubles = averageOfLevels(rootNode);
//        System.out.println(doubles);

        int[] ints = twoSum(new int[]{-3, 4, 3, 90}, 0);
        System.out.println(ints);
    }

    public int[] twoSum(int[] nums, int target) {
        int[] result = new int[0];
//        for (int i = 0; i < nums.length; i++) {
//            for (int j = i + 1; j < nums.length; j++) {
//                if (nums[i] + nums[j] == target) {
//                    result = new int[]{i, j};
//                    break;
//                }
//            }
//        }
        return result;
    }

    private void sum(int value) {

    }


    private List<Double> averageOfLevels(RecursionTest.TreeNode root) {
        List<Double> res = new ArrayList<>();
        if (root == null) return res;
        Queue<RecursionTest.TreeNode> treeNodes = new LinkedList<>();
        treeNodes.add(root);
        while (treeNodes.size() != 0) {
            int len = treeNodes.size();
            double sum = 0;
            for (int i = 0; i < len; i++) {
                RecursionTest.TreeNode node = treeNodes.poll();
                sum += node.val;
                if (node.left != null) treeNodes.add(node.left);
                if (node.right != null) treeNodes.add(node.right);
            }
            res.add(sum / len);
        }
        return res;
    }

    private void add(int index) {
        System.out.println("@cly - " + index + "");
        if (index > 3) return;
        System.out.println("@cly - " + "执行+1 - index = " + index);
        add(index + 1);
        System.out.println("@cly - " + "执行+2 - index = " + index);
        add(index + 2);
    }
}
