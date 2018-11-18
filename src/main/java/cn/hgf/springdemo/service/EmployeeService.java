package cn.hgf.springdemo.service;

import cn.hgf.springdemo.model.mysql.Employee;

/**
 * @Author: FanYing
 * @Date: 2018-05-21 17:41
 * @Desciption:
 */
public interface EmployeeService {


    Employee selectEmployee(Integer employeeId);

    int insertEmployee(Employee employee);

    int updateEmployee(Employee employee);

    int deleteEmployee(Integer employeeId);


}
