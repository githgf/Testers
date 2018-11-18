package cn.hgf.Design.proxy;


public class EmployeeServiceImpl implements EmployeeService {

    @Override
    public int register(Employee employee) {
        System.out.println(employee.getEmpName());
        int i = 0;
        if (employee.getEmpName() != null){

            i =1;
        }
        System.out.println(i);
        return i;
    }

    @Override
    public int register(String name) {

        return 1;
    }
    public static void main(String[] args){
        System.out.println(Thread.currentThread().getContextClassLoader());
    }
}
