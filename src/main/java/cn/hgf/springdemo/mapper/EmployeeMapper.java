package cn.hgf.springdemo.mapper;

import cn.hgf.springdemo.model.mysql.Employee;
import org.apache.ibatis.annotations.*;

/**
 * @Author: FanYing
 * @Date: 2018-05-20 10:10
 * @Desciption:
 */
@Mapper
public interface EmployeeMapper {


    @Select("select * from employee where id = #{id}")
    public Employee selectEmployee(Integer id);

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into employee(last_name) value(#{lastName})")
    public int insertEmployee(Employee employee);

    @Update("update employee set last_name = #{lastName} where id = #{id}")
    public int updateEmployee(Employee employee);

    @Delete("delete from employee where id = #{id}")
    public int deleteEmployee(Integer id);
}
