package cn.hgf.Design.proxy.staticProxy;


import cn.hgf.Design.proxy.Employee;
import cn.hgf.Design.proxy.EmployeeService;
import cn.hgf.Design.proxy.EmployeeServiceImpl;

public class TestStatic {

   public static void main(String[] args){
       EmployeeService employeeService = new EmployeeServiceImpl();

       EmployeeServiceLoggerImpl employeeServiceLogger = new EmployeeServiceLoggerImpl(employeeService);

       EmployeeServiceTimerImpl employeeServiceTimer = new EmployeeServiceTimerImpl(employeeServiceLogger);

       employeeServiceTimer.register(new Employee("jack"));
   }
}
