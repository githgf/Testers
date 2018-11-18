package cn.hgf.springdemo.controller;

import cn.hgf.springdemo.common.entities.Result;
import cn.hgf.springdemo.dao.DepartmentDao;
import cn.hgf.springdemo.dao.EmployeeDao;
import cn.hgf.springdemo.model.mysql.Employee;
import cn.hgf.springdemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

/**
 * @Author: FanYing
 * @Date: 2018-05-02 16:43
 * @Desciption:
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentDao departmentDao;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("/getCustomers")
    public String getCustomers(Model model){
        Collection<Employee> employees = employeeDao.getAll();

        model.addAttribute("customers",employees);
        return "list";
    }
    @GetMapping("/toAdd")
    public String toAdd(Model model){

        model.addAttribute("departments",departmentDao.getDepartments());

        return "emp/addEmployee";
    }

    @PostMapping("/saveAdd")
    public String saveAdd(Employee employee){

        System.out.println("保存的员工信息...."+employee);
        employeeDao.save(employee);
        return "redirect:/employee/getCustomers";
    }

    @PutMapping("/saveAdd")
    public String saveUpdate(Employee employee){
        System.out.println("修改的员工信息"+employee);

        employeeDao.save(employee);

        return "redirect:/employee/getCustomers";

    }

    @GetMapping("/toUpdate/{employeeId}")
     public String toUpdate(@PathVariable("employeeId") Integer employeeId,
                            Model model){
        Employee employee = employeeDao.get(employeeId);
        model.addAttribute("departments",departmentDao.getDepartments());
        model.addAttribute("employee",employee);
        return "emp/addEmployee";

    }

    @DeleteMapping("/toDelete/{employeeId}")
    public String deleteEmployee(@PathVariable Integer employeeId){

         employeeDao.delete(employeeId);

         return "redirect:/employee/getCustomers";
    }


    @ResponseBody
    @PostMapping("/testAdd")
    public Employee testEmployeeAdd(Employee employee){

        employeeService.insertEmployee(employee);

        return employee;

    }
    @GetMapping("/toFileUpload")
    public String toFileUpload(){
        return "UploadFiles";
    }

    /*@PostMapping("/fileUpload")
    public void testUploadFiles(@RequestParam("fileName") MultipartFile file, HttpServletResponse response){
        System.out.println("success!!");
    }*/

    @ResponseBody
    @DeleteMapping("/testDelete/{id}")
    public Integer testDeleteEmployee(@PathVariable  Integer id){
        return employeeService.deleteEmployee(id);
    }

    @ResponseBody
    @GetMapping("/test")
    public String test(){
        return "test success";
    }


    @ResponseBody
    @PutMapping("/testUpdate")
    public Integer testUpdateEmployee(Employee employee){
        return employeeService.updateEmployee(employee);
    }

    @ResponseBody
    @GetMapping("/testSelect/{id}")
    public Employee getEmployeeById(@PathVariable Integer id){
        return employeeService.selectEmployee(id);
    }




}
