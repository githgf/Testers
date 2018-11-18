package cn.hgf.springdemo.controller;

import cn.hgf.springdemo.common.entities.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: FanYing
 * @Date: 2018-08-17 10:30
 * @Desciption: 文件上传、下载等操作
 */
@Controller("/file")
public class FileController {

    @ResponseBody
    @PostMapping("/jsonFileUpload")
    public Result jsonFileUpload(@RequestParam("fileName") String fileName,
                                 @RequestParam("fileSource") String fileSource
    ){

        if (StringUtils.isNotBlank(fileName) && StringUtils.isNotBlank(fileSource)){


        }

        return null;
    }
}
