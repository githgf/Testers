package cn.hgf.springdemo.service;

import cn.hgf.springdemo.model.mysql.Employee;
import cn.hgf.springdemo.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @Author: FanYing
 * @Date: 2018-05-20 20:07
 * @Desciption:
 */
@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeMapper employeeMapper;

    @Cacheable(key = "#root.methodName_+#employeeId")
    public Employee selectEmployee(Integer employeeId){
        return employeeMapper.selectEmployee(employeeId);
    }

    public int insertEmployee(Employee employee){
        return employeeMapper.insertEmployee(employee);
    }

    public int updateEmployee(Employee employee){
        return employeeMapper.updateEmployee(employee);
    }

    public int deleteEmployee(Integer employeeId){
        return employeeMapper.deleteEmployee(employeeId);
    }

}
