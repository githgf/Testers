package cn.hgf.controller;

import cn.hgf.entity.Student;
import cn.hgf.poi.ExcelUtil;
import cn.hgf.poi.ModuleExcel;
import cn.hgf.poi.WordModuleUtil;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/poi")
public class PoiController {

    @RequestMapping("/excel")
    private String testCreateExcel(HttpServletResponse response){

        ExcelUtil.downPoi(response, Student.getStudentList());

        return "redirect:index.jsp";

    }

    /**
     *  写入到模板中
     * @param request
     * @param response
     */
    @RequestMapping("/moduleExcel")
    private void  moduleExcel(HttpServletRequest request,HttpServletResponse response){
        String path = "C:\\Users\\Administrator\\Desktop\\test.xls";

        Workbook workbook = ModuleExcel.importModule(path);

        CellStyle cellStyle = ModuleExcel.createcellStyle(workbook);
        Sheet sheet = workbook.getSheetAt(0);

        Row row = sheet.getRow(3);

        row.createCell(0).setCellValue("1");

        row.createCell(1).setCellValue("金华学院");

        row.createCell(2).setCellValue("男");

        row = sheet.createRow(5);

        row.createCell(1).setCellValue("test");

        row.createCell(6).setCellValue("6");

        ModuleExcel.writeExcel(response,workbook,"testExcel");


    }
    @RequestMapping("/wordModule")
    private void wordModule(HttpServletRequest request,HttpServletResponse response){

        Map<String,String> valueMap = new HashMap<>();

        valueMap.put("name","何高帆");
        valueMap.put("schoolName","金华学院");
        valueMap.put("className","汽修");

        new WordModuleUtil().exportWordModule(response,request,valueMap);
    }

}
