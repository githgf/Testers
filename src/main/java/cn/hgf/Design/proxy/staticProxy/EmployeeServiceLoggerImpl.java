package cn.hgf.Design.proxy.staticProxy;


import proxy.Employee;
import proxy.EmployeeService;

import java.util.logging.Logger;


//目标类的二级代理（输出日志）
public class EmployeeServiceLoggerImpl implements EmployeeService {

    private  EmployeeService employeeService;
    public EmployeeServiceLoggerImpl(EmployeeService employeeService){
        this.employeeService = employeeService;

    }

    @Override
    public int register(Employee employee) {

        /*Logger logger = Logger.getLogger(EmployeeServiceLoggerImpl.class.getName());

        logger.info(employee.getEmpName()+"开始注册");*/
        System.out.println(employee.getEmpName() + "开始注册");
        employeeService.register(employee);
        System.out.println(employee.getEmpName() + "注册结束");
        /*logger.info(employee.getEmpName()+"注册结束");*/

        return 1;
    }

    @Override
    public int register(String name) {
        return 0;
    }
}
