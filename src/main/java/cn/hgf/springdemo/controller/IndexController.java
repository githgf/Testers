package cn.hgf.springdemo.controller;

import cn.hgf.common.JwtUtil;
import cn.hgf.springdemo.common.entities.CustomerException;
import cn.hgf.springdemo.common.entities.Result;
import cn.hgf.springdemo.common.framwork.annotation.CustomerVaild;
import cn.hgf.springdemo.model.mysql.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: FanYing
 * @Date: 2018-05-01 10:26
 * @Desciption:
 */

@Controller
@RequestMapping("/login")
public class IndexController {

    /**
     *  登录验证之后去首页面
     *  否则回到登录页面
     * @param userName
     * @param password
     * @return  result
     */
    @GetMapping("/verify")
    @ResponseBody
    public Result login(@RequestParam("userName") String userName,
                        @RequestParam("password") String password) {
        if (userName != null){
            if (password != null && password.equals("123")){
                return Result.success(JwtUtil.createToken(userName,password,0));
            }
        }

        return Result.fail("密码错误");
    }

    @GetMapping("/testException/{id}")
    public String customerException(@PathVariable Integer id){

        if (id != null){

            throw new CustomerException();
        }

        return "hello world";
    }

    @GetMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    @GetMapping("test")
    @ResponseBody
    public Result test(@RequestParam(value = "age",defaultValue = "156") long age){
        return Result.success(age);
    }

    @GetMapping("test2")
    @ResponseBody
    public Result test2(@CustomerVaild long age){
        return Result.success(age);
    }

    @PostMapping("testEmployee")
    @ResponseBody
    public Result testDate(@RequestBody Employee employee){
        return Result.success(employee);
    }
}
