package cn.hgf.Design.proxy.staticProxy;

import proxy.Employee;
import proxy.EmployeeService;
import proxy.EmployeeServiceImpl;

public class TestStatic {

   public static void main(String[] args){
       EmployeeService employeeService = new EmployeeServiceImpl();

       EmployeeServiceLoggerImpl employeeServiceLogger = new EmployeeServiceLoggerImpl(employeeService);

       EmployeeServiceTimerImpl employeeServiceTimer = new EmployeeServiceTimerImpl(employeeServiceLogger);

       employeeServiceTimer.register(new Employee("jack"));
   }
}
