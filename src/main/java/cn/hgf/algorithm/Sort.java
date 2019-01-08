package cn.hgf.algorithm;

import com.sun.deploy.util.ArrayUtil;
import org.junit.Test;

import java.util.Arrays;

/**
 * @Author: FanYing
 * @Date: 2018-08-23 21:46
 * @Desciption: 排序算法
 */
public class Sort {

    public static int model[] = {1,5,2,2};

    /**
     *  插入排序
     */
    public static void addSort(int[] nums){
        //临时数字
        int tempNum = 0;

        for (int i = 1; i < model.length; i++) {

            tempNum = model[i];

            int j = i -1;
            for ( ;j >=0 && tempNum < model[j]; j--) {

                model[j + 1] = model[j];

            }
            model[j + 1] = tempNum;

        }

//        Arrays.asList(nums).forEach(System.out :: println);
    }

    @Test
    public void testInsertSort(){
        addSort(model);
        System.out.println(model);
    }


}
