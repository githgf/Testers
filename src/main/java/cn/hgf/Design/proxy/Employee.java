package cn.hgf.Design.proxy;

public class Employee {

    private String empName;

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empName='" + empName + '\'' +
                '}';
    }

    public Employee(String empName) {
        this.empName = empName;
    }

    public Employee(){}



}
