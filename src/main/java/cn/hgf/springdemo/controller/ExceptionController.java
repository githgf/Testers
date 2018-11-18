package cn.hgf.springdemo.controller;

import cn.hgf.springdemo.common.entities.CustomerException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: FanYing
 * @Date: 2018-05-19 17:40
 * @Desciption:
 */
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(CustomerException.class)
    public String exceptionHandler(Exception e, HttpServletRequest request){
        Map<String,Object> objectMap = new HashMap<>();

        objectMap.put("code","自定义异常");
        objectMap.put("message",e.getMessage());

        //一定要设置错误状态码，否则不能跳转到自定义 错误页面
        request.setAttribute("javax.servlet.error.status_code",400);

        return "forward:/error";

    }

}
