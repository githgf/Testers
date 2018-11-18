package cn.hgf.poi;

import cn.hgf.entity.Student;
import org.apache.poi.hssf.usermodel.*;
import java.io.OutputStream;
import java.util.List;

public class POIS {
    public void createFixationSheet(OutputStream os,
                                    List<Student> students) throws Exception {
        // 创建工作薄
        HSSFWorkbook wb = new HSSFWorkbook();
        // 在工作薄上建一张工作表
        HSSFSheet sheet = wb.createSheet();
        HSSFRow row = sheet.createRow((short) 0);
        sheet.createFreezePane(0, 1);
        cteateCell(wb, row, (short) 0, "学号");
        cteateCell(wb, row, (short) 1, "学校");
        cteateCell(wb, row, (short) 2, "性别");
        cteateCell(wb, row, (short) 4, "分数");
        int i = 0;
        for (Student student2 : students){
            HSSFRow rowi = sheet.createRow((short) (++i));
            for (int j = 0; j < 4; j++) {
                cteateCell(wb, rowi, (short) 0, String.valueOf(student2.getStudentId()));
                cteateCell(wb, rowi, (short) 1, student2.getSchool_name());
                cteateCell(wb, rowi, (short) 2, student2.getSex());
                cteateCell(wb, rowi, (short) 3, String.valueOf(student2.getGrade()));
//                cteateCell(wb, rowi, (short) 4, student.get(student2));
            }
        }
        wb.write(os);
        os.flush();
        os.close();
        System.out.println("文件生成");

    }

    @SuppressWarnings("deprecation")
    private void cteateCell(HSSFWorkbook wb, HSSFRow row, short col,
                            String val) {
        HSSFCell cell = row.createCell(col);
        cell.setCellValue(val);
        HSSFCellStyle cellstyle = wb.createCellStyle();
        cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
        cell.setCellStyle(cellstyle);
    }
}
