package cn.hgf.poi;


import cn.hgf.common.CommonParam;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Author: FanYing
 * @Date: 2018-08-09 10:22
 * @Desciption:
 */
public class CsvUtil {
    /**
     * 生成为CVS文件
     *
     * @param exportData 源数据List
     * @param titleNames        csv文件的列表头map
     * @param titles
     * @param fileName   文件名称
     * @param spiltChar 分隔符
     * @return
     */
    public static File createCSVFile(List<Map<String, Object>> exportData, String[] titles,String[] titleNames, String fileName,String code,String spiltChar) {
        File csvFile = null;
        BufferedWriter csvFileOutputStream = null;
        File file = new File(ExcelUtil.FILEEXPORTPATH);
        try {
            if (!file.exists()) {
                file.mkdir();
            }
            //定义文件名格式并创建
            csvFile = File.createTempFile(fileName, ".csv", new File(ExcelUtil.FILEEXPORTPATH));
            System.out.println("csvFile：" + csvFile);
            // 使正确读取分隔符","
            csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), StringUtils.isNotBlank(code) ? code : CommonParam.CODE_UTF8), 1024);
            System.out.println("csvFileOutputStream：" + csvFileOutputStream);
            // 写入文件头部
            for (int i = 0; i < titleNames.length; i++) {

                String titleName = titleNames[i];
                if (StringUtils.isNotBlank(titleName)){
                    csvFileOutputStream.write("\"" + titleName + "\"");

                    if (i != titleNames.length - 1 && StringUtils.isNotBlank(titleNames[i + 1])){

                        csvFileOutputStream.write(spiltChar);
                    }
                }

            }
            csvFileOutputStream.newLine();

            // 写入文件内容
            for (Iterator iterator = exportData.iterator(); iterator.hasNext();) {
                Map<String, Object> exportDatum = (Map<String, Object>) iterator.next();
                for (int i = 0; i < titles.length; i++) {

                    String title = titles[i];

                    if (StringUtils.isNotBlank(title)){

                        csvFileOutputStream.write(exportDatum.get(title) != null ? exportDatum.get(title).toString() : "");
                        if (i != titles.length - 1 && StringUtils.isNotBlank(titles[i + 1])){
                            csvFileOutputStream.write(spiltChar);
                        }
                    }
                }

                if (iterator.hasNext()){
                    csvFileOutputStream.newLine();
                }
            }


            csvFileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
            if (csvFile.exists()){
                csvFile.delete();
            }
        } finally {
            try {
                csvFileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return csvFile;
    }
    public static void main(String[] args){
        File file = new File("C:\\Users\\Administrator\\Desktop\\ffffffff4010274107821385778.csv");
        file.delete();
    }

}
