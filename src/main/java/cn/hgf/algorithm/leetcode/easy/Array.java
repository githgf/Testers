package cn.hgf.algorithm.leetcode.easy;

import com.alibaba.fastjson.JSONArray;
import org.junit.Test;


public class Array {

    /**
     * ********** 从排序数组中删除重复项 ******************
     * @param nums      排序数组
     * @param k         删除重复项之后的个数
     */
    public void removeDuplicates(int[] nums, int k) {
        k = 0;
        int mod = 0;
        boolean needAdd = false;
        for (int i = 1; i < nums.length; i++) {

            if (nums[i] != nums[i - 1]){
                if (i != nums.length - 1 && nums[i + 1] == nums[i]){
                    needAdd = true;
                }
                if (k != i -1){

                    mod = nums[i];
                    nums[i] = nums[k + 1];
                    nums[k + 1] = mod;
                }
                k++;
                i = needAdd ? ++i : i;
                needAdd = false;
            }

        }
        k++;

    }

    @Test
    public void testRemoveDuplicates(){
        int[] nums = new int[]{0,1};
        int k = 0;
        removeDuplicates(nums,k);
        System.out.println("nums after rotate : " + JSONArray.toJSONString(nums));
    }

    /**
     * 给定一个数组，将数组中的元素向右移动 k 个位置，其中 k 是非负数。
     * @param nums      指定数组
     * @param k         位移量
     */
    public void rotate(int[] nums, int k) {
        if ( nums == null ||nums.length == 1)return;

        k = k >= nums.length ? k % (nums.length): k;

        if (k <= 0)return;


        reverseOrder(nums,0,nums.length - k - 1);
        reverseOrder(nums,nums.length - k,nums.length - 1);
        reverseOrder(nums,0,nums.length - 1);

    }

    @Test
    public void testRotate(){
        int[] nums = new int[]{1,2};
        rotate(nums,1);
        System.out.println(JSONArray.toJSONString(nums));
    }


    /**
     *  将制定数组[start,end] 中的元素倒序排序
     * @param nums      数组
     * @param start     开始排序的下标
     * @param end       结束排序的下标
     */
    void reverseOrder(int[] nums,int start,int end){
        if (start >= nums.length || end >= nums.length || start > end)return;
        for (int i = start; i <= (end + start) / 2; i++) {
            //将 nums[(start + end) - i] 和 nums[i] 交换位置

            if (start + end  == i * 2)continue;

            nums[(start + end) - i] += nums[i];
            nums[i] = nums[(start + end) - i] - nums[i];
            nums[(start + end) - i] -= nums[i];

        }
    }

    @Test
    public void testReverseOrder(){
        int[] nums = new int[]{1,2,4,6,8,9};
        reverseOrder(nums,0,4);
        System.out.println(JSONArray.toJSONString(nums));

    }


    @Test
    public void testSwapValue(){
        swapValue(8,9);
    }

    public void swapValue(Integer source,Integer target){
        System.out.println("交换之前：source = "+ source + "     target = " + target);
        target += source;
        source = target - source;
        target -= source;
        System.out.println("交换之后：source = "+ source + ",    target = " + target);

    }

}
