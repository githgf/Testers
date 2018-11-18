package cn.hgf.Design.proxy.jdkDyProxy;

import org.apache.commons.collections4.Bag;

public class TestDyJDKProxy {

    public static void main(String[] args){

        ProxyUtils proxyUtils = new ProxyUtils();

        /*EmployeeService employeeService = (EmployeeService) proxyUtils
                                            .getProxyInstance(new EmployeeServiceImpl());
        try {
            employeeService.register(new Employee("jack"));
        } catch (Exception e) {
            e.printStackTrace();

        }*/
        test2();
    }

    public static void test2(){
        ProxyUtils proxyUtils = new ProxyUtils();
        Object proxyInstance = proxyUtils.getProxyInstance(Bag.class, Bag.class);
        System.out.println(proxyInstance);
    }
}
