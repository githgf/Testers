package cn.hgf.springdemo.controller;

import cn.hgf.parseJson.ParseJson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;

/**
 * @author gaofan
 * @date 2018-11-18 17:24
 */
@Controller
@RequestMapping("/data")
public class DataController {

    @GetMapping("/viewHeatMap")
    public String viewHeatMap(Model model){
        ParseJson parseJson = new ParseJson();
        File file = new File("D:\\ideWorks\\SpringBootDemo\\src\\main\\resources\\static\\asserts\\js\\project\\data\\result.json");
        String fileToString = parseJson.parseJsonFileToString(file);
        Object jsonArray = parseJson.parseJsonString(fileToString);
        model.addAttribute("resultData",jsonArray);

        return "map/base/hat_map";
    }

}
