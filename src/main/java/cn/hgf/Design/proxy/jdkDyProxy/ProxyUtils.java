package cn.hgf.Design.proxy.jdkDyProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;

/***
 * 动态代理类工厂，更具传入的参数返回所需要的代理类
 */

public class ProxyUtils implements InvocationHandler{

    Object target;

    Object getProxyInstance(Object target,Class<?> ... interfaceClass){
        this.target = target;
        Object proxy = null;
        try {
            if (interfaceClass == null ||interfaceClass.length == 0)
                proxy = Proxy.newProxyInstance(
                        ProxyUtils.class.getClassLoader(),
                        target.getClass().getInterfaces(),
                        this
                );
            else
                proxy = Proxy.newProxyInstance(
                        ProxyUtils.class.getClassLoader(),
                        interfaceClass,
                        this
                );

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw  new RuntimeException("你有毒！！！给个不存在的类@@###￥#￥#￥");
        }

        return proxy;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        Object result = null;

        try {

            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<  before   >>>>>>>>>>>");
            System.out.println("target>>>>>>>>"+target);
            result=method.invoke(target,args);

            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<  after   >>>>>>>>>>>");

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return result;
    }

}
