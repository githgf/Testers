package cn.hgf.poi;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 *  在自定义的excel文件中固定位值写入数据
 */
public class ModuleExcel {

    private static HSSFCellStyle cellStyle;

    /**
     *  导入excel模板
     * @param path
     * @return
     */
    public static Workbook importModule(String path){

        InputStream is = null;
        Workbook workbook = null;
        try {
            is = new FileInputStream(path);

            workbook = new HSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return workbook;
    }

    /**
     *  写出数据到模板中
     * @param response
     * @param workbook
     * @param fileName
     */
    public static void writeExcel(HttpServletResponse response,Workbook workbook,String fileName){

        OutputStream outputStream = null;
        try {

            outputStream = response.getOutputStream();//IOException

            response.setContentType("application/msexcel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="
                    .concat(String.valueOf(URLEncoder.encode(fileName + ".xls", "UTF-8"))));
            workbook.write(outputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public static HSSFCellStyle createcellStyle(Workbook wb) {
        cellStyle = (HSSFCellStyle) wb.createCellStyle();
//        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
//        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
//        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return cellStyle;
    }
    private static Cell setcellStyleWithStyleAndValue(CellStyle style, Cell cell, String value) {
        cell.setCellStyle(style);
        cell.setCellValue(value);
        return cell;
    }

    private static Cell setcellStyleWithValue(Cell cell, String value) {
        cell.setCellStyle(cellStyle);
        cell.setCellValue(value);
        return cell;
    }


    private static Cell setcellStyleWithStyleAndValue(CellStyle style, Cell cell, RichTextString value) {
        cell.setCellStyle(style);
        cell.setCellValue(value);
        return cell;
    }

    private static Cell setcellStyleWithValue(Cell cell, int value) {
        cell.setCellStyle(cellStyle);
        cell.setCellValue(value);
        return cell;
    }

    public static Cell setcellStyleWithValue(Cell cell, double value) {
        cell.setCellStyle(cellStyle);
        cell.setCellValue(value);
        return cell;
    }

}
