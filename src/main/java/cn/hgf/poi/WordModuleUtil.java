package cn.hgf.poi;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Map;

public class WordModuleUtil {

    private Configuration configuration;
    private String fileName;
    private final static String path = "D:\\IMS\\";

    public WordModuleUtil() {
        this.configuration = new Configuration(new Version(1,2,3));
        configuration.setDefaultEncoding("UTF-8");
        try {
            configuration.setDirectoryForTemplateLoading(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  导出数据
     * @param response
     * @param request
     * @param valueMap
     */
    public void exportWordModule(HttpServletResponse response, HttpServletRequest request, Map<String,String> valueMap){
        InputStream is = null;
        File file = null;
        OutputStream outputStream = null;
        try {
            Template freeMark = configuration.getTemplate("moduleWord.ftl");
            file = createWord(valueMap,freeMark);
            is = new FileInputStream(file);

            response.setContentType("application/msword");
            fileName = "test.doc";
            response.setHeader("Content-Disposition", "attachment;filename="
                    .concat(String.valueOf(URLEncoder.encode(fileName, "UTF-8"))));
            response.setCharacterEncoding("UTF-8");

            outputStream = response.getOutputStream();
            byte[] bytes = new byte[1024];
            int len = -1;
            while ((len = is.read(bytes)) != -1){

                outputStream.write(bytes,0,len);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {

            try {
                if (outputStream != null){

                    outputStream.close();

                }
                if (is != null){

                    is.close();
                }
                if (file != null){

                    file.delete();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }


    }

    /**
     *  创建模板文件
     * @param dataMap
     * @param template
     * @return
     */
    public File createWord(Map<String,String> dataMap,Template template){

        String name = "test.doc";
        File file = new File(name);
        ServletOutputStream outputStream = null;
        Writer writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(file),"utf-8");

            template.process(dataMap,writer);


        } catch (TemplateException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
//                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return file;
    }

}
