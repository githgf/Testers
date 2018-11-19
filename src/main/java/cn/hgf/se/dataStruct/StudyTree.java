package cn.hgf.se.dataStruct;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *  树结构
 *      概念：
 *          深度：从节点到叶子节点的最长简单路径的边条数即为该节点的深度
 *          高度：从叶子节点往上数到指定节点的最长简单路径的边的条数值即为该节点的高度
 *                  a
 *                b    c
 *              e  f  g  h
 *             i
 *           如上图 i为叶子节点，其深度为4，高度为1
 *                  b节点的深度为2，高度为3
 */
public class StudyTree {
    TreeNode ROOT = null;

    /*
        中序遍历（非递归）：左、根、右
        思路：
            从哪个节点开始遍历就先判断是否为空，如果不为空将当前节点存在栈中，
            （按照中序遍历的顺序）只要栈中的元素不为空就一直循环，
            先遍历左子树，
            如果左子树不为空，将左节点放在栈顶，同时当前节点的左节点设置为空，否则一直循环
            如果左子树为空，将节点的值放在list中，如果右子树不为空，将右节点推入栈顶

            也就是说只有左子树遍历完成，才能进行根、右子树的遍历，
     */
    List<Integer> middleOrder(TreeNode treeNode){
        if (treeNode == null)return null;
        List<Integer> nodes = new ArrayList<>();

        Stack<TreeNode> treeNodeStack = new Stack<>();
        treeNodeStack.push(treeNode);

        while (!treeNodeStack.empty()){

            TreeNode pop = treeNodeStack.peek();
            if (pop.left != null){
                treeNodeStack.push(pop.left);
                //这一步不能省，否则死循环
                pop.left = null;
            }else {
                pop = treeNodeStack.pop();
                nodes.add(pop.val);
                if (pop.right != null){

                    treeNodeStack.push(pop.right);
                }
            }

        }
        return nodes;
    }

    /*
        先序遍历（非递归）：根、左、右
     */
    List<Integer> preOrder(TreeNode treeNode){
        if (treeNode == null)return null;
        List<Integer> nodes = new ArrayList<>();

        Stack<TreeNode> treeNodeStack = new Stack<>();
        treeNodeStack.push(treeNode);

        while (!treeNodeStack.empty()){

            TreeNode pop = treeNodeStack.pop();
            nodes.add(pop.val);
            if (pop.right != null){

                treeNodeStack.push(pop.right);
            }
            if (pop.left != null){

                treeNodeStack.push(pop.left);
            }
        }
        return nodes;
    }


    @Test
    public void testMiddleOrder(){
        preOrder(ROOT).forEach(System.out :: println);

    }

    @Before
    public void initTreeData(){
        ROOT = new TreeNode(1);
        TreeNode treeNode_2 = new TreeNode(2);
        TreeNode treeNode_3 = new TreeNode(3);
        TreeNode treeNode_4 = new TreeNode(4);
        TreeNode treeNode_5 = new TreeNode(5);
        TreeNode treeNode_6 = new TreeNode(6);
        TreeNode treeNode_7 = new TreeNode(7);
        ROOT.left = treeNode_2;
        ROOT.right = treeNode_3;

        treeNode_2.left = treeNode_4;
        treeNode_2.right = treeNode_5;

        treeNode_3.left = treeNode_6;
        treeNode_3.right = treeNode_7;

    }


}

class TreeNode{
    TreeNode left;
    TreeNode right;
    int val;

    public TreeNode(int val) {
        this.val = val;
    }
}