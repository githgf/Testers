package cn.hgf.springdemo.common.testng;

import org.testng.*;

import java.util.List;

/**
 * @Author: FanYing
 * @Date: 2018-08-18 14:54
 * @Desciption:
 */
public class TestNGInterceptor implements IMethodInterceptor {
    @Override
    public List<IMethodInstance> intercept(List<IMethodInstance> list, ITestContext iTestContext) {

        for (IMethodInstance iMethodInstance : list) {

            ITestNGMethod method = iMethodInstance.getMethod();
            System.out.println("TestNGInterceptor >>>>> " + method.getMethodName());
        }

        return list;
    }
}

