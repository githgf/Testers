package cn.hgf.algorithm.leetcode.easy;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author gaofan
 * @date 2018-09-29 8:15
 */
public class LeetCode {

    //给定一个数组，将数组中的元素向右移动 k 个位置，其中 k 是非负数。
    //输入: [1,2,3,4,5,6,7] 和 k = 3
    //输出: [5,6,7,1,2,3,4]
    //TODO 测试ok 时间长
    public void rotate(int[] nums, int k) {
       int modNum = 0;
       k = k > nums.length ? k - nums.length : k;

        for (int i = 0; i < k; i++) {
            modNum = nums[0];

            nums[0] = nums[nums.length - 1];

            nums[nums.length - 1] = modNum;
            for (int j = nums.length -1 ; j >  1 ; j--) {

                modNum = nums[j];

                nums[j] = nums[j -1];

                nums[j - 1] = modNum;


            }

        }

    }
    //解法二
    //分成三部分旋转
    //a[0]...a[k]
    //a[k + 1]...a[len - 1]
    //整体旋转
    public void rotate2(int[] nums, int k) {
        if ( nums == null ||nums.length == 1)return;

        k = k >= nums.length ? k % (nums.length): k;

        if (k <= 0)return;

        if (k == nums.length - 1) k = 0;
        reserve(nums,0,k);
        reserve(nums,k + 1,nums.length - 1);
        if (nums.length == 2 && k == 1){return;}
        reserve(nums,0,nums.length - 1);
    }

    public void reserve(int[] nums,int begin,int end){
        int mod = 0;
        if (begin >= nums.length || end >= nums.length || begin > end)return;
        for (int i = 0; i < ((end - begin) / 2) + 1; i++) {

            mod = nums[begin + i];

            nums[begin + i] = nums[end - i];

            nums[end - i] = mod;
        }

    }

    @Test
    public void testRo(){
        int[] nums = new int[]{1,2,3,4,5,6};
        rotate2(nums,1);
        printArray(nums);
    }

    @Test
    public void testReserve(){

        int[] nums = new int[]{1,2,3,4,5,6,7};

        reserve(nums,0,5);

        printArray(nums);
    }

    public void printArray(int[] nums){
        System.out.print("[");
        for (int i = 0; i < nums.length; i++) {

            System.out.print(nums[i] + ",");

        }
        System.out.print("]");
    }

    //给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。

    public int singleNumber(int[] nums) {
        Arrays.sort(nums);

        int fiag = 0;
        if (nums.length == 1 || nums.length == 2)return 0;

        for (int i = 1; i < nums.length; i++) {

            if (nums[i] != nums[i - 1]){

                if (i - fiag <= 1){

                    return nums[i - 1];
                }
                if (i == nums.length -1){
                    return nums[nums.length -1];
                }

                fiag = i;

            }

        }

        return 0;
    }

    @Test
    public void testSingleNumber(){
        System.out.println(singleNumber(new int[]{2,2,3}));
    }
}

