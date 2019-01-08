package cn.hgf.algorithm.leetcode.easy;

import org.junit.Test;

/**
 * @author gaofan
 * @date 2018-10-30 8:41
 *  链表题
 */
public class LeetCode_LinkList {

    //链表反转
    public ListNode reverseList(ListNode head) {

        if(head == null )return null;

        if (head.getNext() == null)return head;

        ListNode curNode = head;

        ListNode nextNode = null,preNode = null;

        while (curNode != null){

            nextNode = curNode.getNext();

            curNode.setNext(preNode);

            preNode = curNode;

            curNode = nextNode;

        }

        return preNode;

    }

    @Test
    public void testReverseList(){

        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);

        node2.setNext(node3);
        node1.setNext(node2);

        ListNode node0 = node1;

        node0.mapString();

        ListNode oldHeadNode = node1;

        ListNode newHeadNode = reverseList(oldHeadNode);

        newHeadNode.mapString();

    }
}

class ListNode{

    int val;
    ListNode next;
    ListNode(int x) { val = x; }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public ListNode getNext() {
        return next;
    }

    public void setNext(ListNode next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "{" +
                "val=" + val +
                '}';
    }
    //遍历整个链表节点
    public void mapString(){
        ListNode curNode = this;
        StringBuilder result = new StringBuilder();
        while (curNode != null){
            result.append(curNode);
            curNode = curNode.getNext();
            result.append(curNode != null ? "-->" : "");
        }
        System.out.println(result.toString());
    }
}