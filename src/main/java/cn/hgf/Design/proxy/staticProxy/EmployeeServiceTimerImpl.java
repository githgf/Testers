package cn.hgf.Design.proxy.staticProxy;


import cn.hgf.Design.proxy.Employee;
import cn.hgf.Design.proxy.EmployeeService;
import java.text.SimpleDateFormat;
import java.util.Date;

//目标类的一级代理（输出执行程序运行话费时间）
public class EmployeeServiceTimerImpl implements EmployeeService {
    private EmployeeService employeeService;


    public EmployeeServiceTimerImpl(EmployeeService employeeService){

        this.employeeService = employeeService;

    }

    @Override
    public int register(Employee employee) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long start = System.currentTimeMillis();
        System.out.println("注册执行开始  " + simpleDateFormat.format(new Date()));
        employeeService.register(employee);

        long end = System.currentTimeMillis();
        System.out.println("注册结束时间  " + simpleDateFormat.format(new Date()));
        System.out.println("执行时间总共为"+(end-start));
        return 0;
    }

    @Override
    public int register(String name) {
        return 0;
    }
}
