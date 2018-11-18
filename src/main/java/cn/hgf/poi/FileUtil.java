package cn.hgf.poi;

import cn.hgf.common.CommonParam;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Author: FanYing
 * @Date: 2018-08-09 11:55
 * @Desciption:
 */
public class FileUtil {
    public static void main(String[] args) {
        String fileName = "ffffffff74365221607268032.csv";
        File file = new File(ExcelUtil.FILEEXPORTPATH + fileName);
        back(parse(file,CommonParam.CODE_UTF8,CommonParam.LINETYPE_WINDOWS),CommonParam.CODE_GBK,new File(ExcelUtil.FILEEXPORTPATH + fileName));
    }

    /**
     *  解析文件
     * @param file              被解析文件
     * @param code              编码格式
     * @param lineType          换行符种类   0或其他-windows(/r/n) 2-linux(/n) 1-mac(/n)
     * @return
     */
    public static String parse(File file,String code,int lineType){
        StringBuffer buffer=new StringBuffer();
        try {
            FileInputStream fis=new FileInputStream(file);
            InputStreamReader isr=new InputStreamReader(fis, StringUtils.isNotBlank(code) ? code : CommonParam.CODE_UTF8);
            BufferedReader br=new BufferedReader(isr);
            String line=null;
//            br.skip(1);
            while ((line=br.readLine())!=null) {
                buffer.append(line);
                switch (lineType){
                    case CommonParam.LINETYPE_MAC : buffer.append("\r");break;
                    case CommonParam.LINETYPE_LINUX : buffer.append("\n");break;
                    default:buffer.append("\r\n");break;
                }
            }
//            buffer.delete(buffer.length()-2,buffer.length());
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(buffer);
        return buffer.toString();
    }

    /**
     *  将指定的文件集压缩成包
     * @param finalZipFilePath			压缩包指定的文件路径
     * @param zipName					压缩包名
     * @param needZipFileList			被压缩的文件，可以多个
     * @return							压缩包的全名
     */
    public static String zipFiles(String finalZipFilePath,String zipName,String... needZipFileList)  {

        if (needZipFileList == null || needZipFileList.length == 0)return null;

        if (StringUtils.isBlank(zipName)){
            zipName = String.valueOf(new Date().getTime()) + ".zip";
        }
        if (StringUtils.isBlank(finalZipFilePath)){

            finalZipFilePath = needZipFileList[0];

            finalZipFilePath = finalZipFilePath.substring(0,finalZipFilePath.lastIndexOf(File.separator));

        }

        File finalZipFile = new File(finalZipFilePath,zipName);


        FileOutputStream fileOutputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        ZipOutputStream zipOutputStream = null;


        FileInputStream fileInputStream = null;
        BufferedInputStream bufferedInputStream = null;
        try {

            fileOutputStream = new FileOutputStream(finalZipFile);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            zipOutputStream = new ZipOutputStream(bufferedOutputStream);

            byte[] bytes = new byte[1024];
            for (String needZipFile : needZipFileList) {

                fileInputStream = new FileInputStream(needZipFile);
                bufferedInputStream = new BufferedInputStream(fileInputStream);
                String substring = needZipFile.substring(needZipFile.lastIndexOf(File.separator) + 1);
                int len = 0;
                ZipEntry zipEntry = new ZipEntry(substring);
                zipOutputStream.putNextEntry(zipEntry);
                while ((len = bufferedInputStream.read(bytes)) != -1){

                    zipOutputStream.write(bytes,0,len);
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

            try {
                if (bufferedInputStream != null){
                    bufferedInputStream.close();
                }
                if (fileInputStream != null){
                    fileInputStream.close();
                }
                if (zipOutputStream != null){
                    zipOutputStream.close();
                }
                if (bufferedOutputStream != null){
                    bufferedOutputStream.close();
                }
                if (fileOutputStream != null){
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        return finalZipFile.getPath();
    }

    /**
     * 	验证附件大小是否超标
     * @param fileSize	文件大小
     * @param size		指定的大小
     * @return
     */
    public static boolean verifyFileSize(long fileSize,int size){

        fileSize /= 1024;

        fileSize /= 1024;

        return fileSize < size;

    }

    public static void back(String stringBuffer,String code,File file){
        try {
            FileOutputStream fos=new FileOutputStream(file);
            OutputStreamWriter osw=new OutputStreamWriter(fos, StringUtils.isNotBlank(code) ? code : CommonParam.CODE_UTF8);
            osw.write(stringBuffer);
            osw.flush();
            osw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
