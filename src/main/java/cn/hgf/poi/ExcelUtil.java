package cn.hgf.poi;

import cn.hgf.entity.Student;
import org.apache.poi.hssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *  直接生成新的excel
 *  此方法适合没有要求的简单列表
 */
public class ExcelUtil {

        public static final String FILEEXPORTPATH = "C:\\Users\\Administrator\\Desktop\\";

        /**
         *
         * @param response:响应对象，类型是HttpServletResponse
         * @throws Exception:代表异常对象
         */
        public static void downPoi(HttpServletResponse response,
                                   List<Student> students){
            try {
            String fname = "detial" + getTimeStamp();// Excel文件名
            OutputStream os = response.getOutputStream();// 取得输出流
            response.reset();// 清空输出流
            response.setHeader("Content-disposition", "attachment; filename="
                    + fname + ".xls"); // 设定输出文件头,该方法有两个参数，分别表示应答头的名字和值。
            response.setContentType("application/msexcel");

                new POIS().createFixationSheet(os, students);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 该方法用来产生一个时间字符串（即：时间戳）
         * @return
         */
        public static String getTimeStamp() {
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd hh:MM:ss");
            Date date = new Date();
            return dateFormat.format(date);
        }

    /**
     *  获取excel工作表
     * @param titles
     * @param titleName
     * @param dataMap
     * @param sheetNameList
     * @return
     */
    public static HSSFWorkbook getPLusWorkbook(String[][] titles, String[][] titleName, Map<String,List<Map<String, Object>>> dataMap, String[] sheetNameList){

        HSSFWorkbook wb = new HSSFWorkbook();

        for (int i = 0; i < sheetNameList.length; i++) {
            HSSFSheet sheet=wb.createSheet(sheetNameList[i]);//创建sheet表
            HSSFCellStyle style=wb.createCellStyle();
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER); //创建居中样式，更多单元格格式设置百度“POI导出Excel”

            HSSFRow row0 = sheet.createRow(0);//导入第一行
            for(int index =0 ; index< titleName[i].length;index++){
                HSSFCell cell = row0.createCell((short)index);//导入第一单元格
                cell.setCellValue(titleName[i][index]);
            }
            List<Map<String, Object>> mapList = dataMap.get(sheetNameList[i]);
            for(int index =0 ; index< mapList.size();index++){
                Map<String,Object> map = mapList.get(index);
                HSSFRow row = sheet.createRow((short)(index+1));//导入第一单元格
                for(int j = 0;j< titles[i].length;j++){
                    HSSFCell cell = row.createCell((short)j);//导入第一单元格
                    cell.setCellValue(map.containsKey(titles[i][j])&& map.get(titles[i][j])!=null?map.get(titles[i][j]).toString():"");
                }
            }
        }

        return wb;
    }

    public static HSSFWorkbook getWorkbook(String[] titles, String[] titleName, List<Map<String, Object>> list, String exportFileName){

        //添加excel
        HSSFWorkbook wb = new HSSFWorkbook();
        //创建sheet表
        HSSFSheet sheet=wb.createSheet();
        //参数分别为sheet表索引值，sheet表的名字，处理中文问题用的编码
        wb.setSheetName(0,exportFileName);
        //合并单元格，Region(起始行号,起始列号,终止行号,终止列号)即起始单元格，终止单元格
        //列号限制为short型
        //sheet.addMergedRegion(new Region(0,(short)0,0,(short)3));
        HSSFCellStyle style=wb.createCellStyle();
        //创建居中样式，更多单元格格式设置百度“POI导出Excel”
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //导入第一行
        HSSFRow row0 = sheet.createRow(0);
        for(int index =0 ; index< titleName.length;index++){
            //导入第一单元格
            HSSFCell cell = row0.createCell(index);
            cell.setCellValue(titleName[index]);
        }

        for(int index =0 ; index< list.size();index++){
            Map<String,Object> map = list.get(index);
            //导入第一单元格
            HSSFRow row = sheet.createRow((index+1));
            for(int j = 0;j< titles.length;j++){
                //导入第一单元格
                HSSFCell cell = row.createCell(j);

                String content = "";
                Object o = map.get(titles[j]);
                if (o != null) {
                    content = o.toString();
                }
                cell.setCellValue(map.containsKey(titles[j])? content:"");
            }
        }

        return wb;
    }



    /**
     * 导出excel到本地
     * @param titles
     * @param titleName
     * @param dataMap
     * @param exportFileName
     * @param sheetNameList
     * @return
     */
    public static String exportPlusToLocal(String[][] titles, String[][] titleName, Map<String,List<Map<String, Object>>> dataMap, String exportFileName,String[] sheetNameList){
        //添加excel
        HSSFWorkbook wb = getPLusWorkbook(titles,titleName,dataMap,sheetNameList);

        return exportWbToLocal(wb,exportFileName);
    }

    public static String exportWbToLocal(HSSFWorkbook hssfWorkbook,String fileName){
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(FILEEXPORTPATH,fileName+".xls"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        try {
            hssfWorkbook.write(out);
            out.close();
            hssfWorkbook.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return "" + fileName+".xls";
    }

    public static String exportToLocal(String[] titles, String[] titleName, List<Map<String, Object>> dataMap, String exportFileName){
        //添加excel
        HSSFWorkbook wb = getWorkbook(titles,titleName,dataMap,exportFileName);

        return exportWbToLocal(wb,exportFileName);
    }

}
