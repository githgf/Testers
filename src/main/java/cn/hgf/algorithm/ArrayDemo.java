package cn.hgf.algorithm;


import org.junit.Test;

import java.util.*;

/**
 * @Author: FanYing
 * @Date: 2018-08-25 10:26
 * @Desciption:
 */
public class ArrayDemo {
    /**
     * 给定一个排序数组，你需要在原地删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。
     * <p>
     * 不要使用额外的数组空间，你必须在原地修改输入数组并在使用 O(1) 额外空间的条件下完成。
     */
    public void distinct() {

//        int nums[] = {0,0,1,1,1,2,2,3,3,4};
        int nums[] = {1, 1};

        //已完成排序的数组中最后一数的下标
        int lastRightIndex = 0;

        //中间数，用作交换
        int exchange = 0;

        String resultStr = nums[0] + ",";

        for (int i = 1; i < nums.length - 1; i++) {

            if (nums[i + 1] != nums[lastRightIndex]) {
                exchange = nums[++lastRightIndex];
                nums[lastRightIndex] = nums[i + 1];
                nums[i + 1] = exchange;
            }

        }
        System.out.println("完成去重的新数组长度  " + ++lastRightIndex);
//        removeDuplicates(nums);
        printlnArray(nums);
    }

    public void printlnArray(int nums[]) {

        System.out.print("{");
        for (int num : nums) {
            System.out.print(num + ",");
        }
        System.out.println("}");
    }

    public List<Integer> split(int num) {
        List<Integer> nums = new ArrayList<>();

        if (num % 2 == 0) {

            nums.add(2);
            nums.addAll(split(num / 2));
            return nums;
        }

        if (num > 3) {

            for (int i = 3; i < (num / 2) + 1; i = i + 2) {

                if (num % i == 0) {
                    nums.add(i);
                    nums.addAll(split(num / i));
                    return nums;
                }

            }
        }
        nums.add(num);
        return nums;
    }

    @Test
    public void testSpilt() {

        System.out.println(split(90));

    }

    public boolean verifyDup(int[] nums) {

        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {

            if (!set.add(nums[i])){return true;}

        }
        return false;
    }


}
