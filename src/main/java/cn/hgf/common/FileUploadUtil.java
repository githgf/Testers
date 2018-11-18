package cn.hgf.common;

import cn.hgf.springdemo.common.config.FileUploadConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 *
 * 上传工具类
 */
@Component
public class FileUploadUtil
{
    private static FileUploadUtil uploadUtil;
    private static final String BOUNDARY = "WebKitFormBoundaryB11u1ohkhtdQ2YEe"; // 边界标识
    // 随机生成
    private static final String PREFIX = "--";
    private static final String LINE_END = "\r\n";
    private static final String CONTENT_TYPE = "multipart/form-data"; // 内容类型


    static String filePath;

    @Value("${common.filePath}")
    public void setFilePath(String filePath) {
        FileUploadUtil.filePath = filePath;
    }

    public static String getFilePath() {
        return filePath;
    }

    /**
     * 单例模式获取上传工具类
     *
     * @return
     */
    public static FileUploadUtil getInstance()
    {
        if (null == uploadUtil)
        {
            uploadUtil = new FileUploadUtil();
        }
        return uploadUtil;
    }


    private static int readTimeOut = 10 * 1000; // 读取超时
    private static int connectTimeout = 10 * 1000; // 超时时间


    /***
     * 请求使用多长时间
     */
    private static int requestTime = 0;


    private static final String CHARSET = "utf-8"; // 设置编码


    /***
     * 上传成功
     */
    public static final int UPLOAD_SUCCESS_CODE = 1;


    /**
     * 文件不存在
     */
    public static final int UPLOAD_FILE_NOT_EXISTS_CODE = 2;


    /**
     * 服务器出错
     */
    public static final int UPLOAD_SERVER_ERROR_CODE = 3;
    protected static final int WHAT_TO_UPLOAD = 1;
    protected static final int WHAT_UPLOAD_DONE = 2;


    /**
     * 上传文件到服务器
     *
     * @param filePath
     *            需要上传的文件的路径
     * @param fileKey
     *            在网页上<input type=file name=xxx/> xxx就是这里的fileKey
     * @param RequestURL
     *            请求的URL
     */
    /*public static String uploadFile(String filePath, String fileKey, HttpServletRequest req, Map<String, String> param)
    {
        ParameterBean parameter = CommonUtil.getParameterValue("SERVER_UPLOAD", req);
        if(!StringUtils.isNotBlank(parameter))
        {
            File file = new File(filePath);

            return uploadFile(file, fileKey,"dizhi", param);
        }
        return null;
    }*/


    /**
     * 上传文件
     */
    public static String uploadFile(File file, String fileKey, String RequestURL, Map<String, String> param)
    {
        String result = null;
        requestTime = 0;


        long requestTime = System.currentTimeMillis();
        long responseTime = 0;


        try
        {
            URL url = new URL(RequestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(readTimeOut);
            conn.setConnectTimeout(connectTimeout);
            conn.setDoInput(true); // 允许输入流
            conn.setDoOutput(true); // 允许输出流
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("POST"); // 请求方式
            conn.setRequestProperty("Charset", CHARSET); // 设置编码
            conn.setRequestProperty("connection", "keep-alive");
//            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=----" + BOUNDARY);


            /**
             * 当文件不为空，把文件包装并且上传
             */
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            StringBuffer sb = null;
            String params = "";


            /***
             * 以下是用于上传参数
             */
            if (param != null && param.size() > 0)
            {
                Iterator<String> it = param.keySet().iterator();
                while (it.hasNext())
                {
                    sb = null;
                    sb = new StringBuffer();
                    String key = it.next();
                    String value = param.get(key);
                    sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
                    sb.append("Content-Disposition: form-data; name=\"").append(key).append("\"").append(LINE_END).append(LINE_END);
                    sb.append(value).append(LINE_END);
                    params = sb.toString();
                    dos.write(params.getBytes());
                }
            }


            sb = null;
            params = null;
            sb = new StringBuffer();
            /**
             * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
             * filename是文件的名字，包含后缀名的 比如:abc.png
             */
            sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
            sb.append("Content-Disposition:form-data; name=\"" + fileKey + "\"; filename=\"" + file.getName() + "\"" + LINE_END);
            sb.append("Content-Type:"+ CONTENT_TYPE + LINE_END); // 这里配置的Content-type很重要的
            // ，用于服务器端辨别文件的类型的
            sb.append(LINE_END);
            params = sb.toString();
            sb = null;


            dos.write(params.getBytes());

            /** 上传文件 */
            InputStream is = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = is.read(bytes)) != -1)
            {
                dos.write(bytes, 0, len);
            }
            is.close();


            dos.write(LINE_END.getBytes());
            byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
            dos.write(end_data);
            dos.flush();
            //
            // dos.write(tempOutputStream.toByteArray());
            /**
             * 获取响应码 200=成功 当响应成功，获取响应的流
             */
            int res = conn.getResponseCode();
            responseTime = System.currentTimeMillis();
            requestTime = (int) ((responseTime - requestTime) / 1000);
            if (res == 200)
            {
                InputStream input = conn.getInputStream();
                StringBuffer sb1 = new StringBuffer();
                int ss;
                while ((ss = input.read()) != -1)
                {
                    sb1.append((char) ss);
                }
                result = sb1.toString();
            }
        }
        catch (MalformedURLException e)
        {
            System.out.println("文件读写异常" +  e);
        }
        catch (IOException e)
        {
            System.out.println("文件读写异常" + e);
        }


        return result;
    }


    /**
     * 获取上传使用的时间
     *
     * @return
     */
    public static int getRequestTime()
    {
        return requestTime;
    }

    public static String saveFileToLocal(InputStream inputStream,String fileName){

        if (inputStream == null || StringUtils.isBlank(fileName)){
            System.out.println("文件不能为空");
        }

        File file = new File(FileUploadUtil.getFilePath());

        if (file == null){file.mkdirs();}

        FileOutputStream fileOutputStream = null;
        String localFileName = file.getPath() + File.separator + "test-" +fileName;
        try {
            int len = 0;
            fileOutputStream = new FileOutputStream(localFileName);
            byte[] bs = new byte[1024];
            if ((len = inputStream.read(bs)) != -1){
                fileOutputStream.write(bs,0,len);
            }
            return localFileName;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("文件有毒");
        }finally {

            if (fileOutputStream != null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    public static void main(String[] args){

    }
}