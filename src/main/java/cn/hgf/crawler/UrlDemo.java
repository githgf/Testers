package cn.hgf.crawler;

import org.junit.Test;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author gaofan
 * @date 2018-11-11 16:06
 *  用最简单的url发送请求
 */
public class UrlDemo {

    public static void main(String[] args){
        URL url = null;
        URLConnection urlConnection = null;
        BufferedReader bufferedReader = null;
        PrintWriter printWriter = null;
        String path = UrlDemo.class.getClassLoader().getResource("").getPath();


        //String regex = "http://[\\w+\\.?/?]+\\.[A-Za-z]+";
        String regex = "https://[\\w+\\.?/?]+\\.[A-Za-z]+";//url匹配规则
        Pattern p = Pattern.compile(regex);
        try {
            url = new URL("https://www.rndsystems.com/cn");
            urlConnection = url.openConnection();

            printWriter = new PrintWriter(new FileWriter(path + File.separator + "baidu.html"),true);

//            urlConnection.connect();
            bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String buf = null;

            while ((buf = bufferedReader.readLine()) != null){
                Matcher matcher = p.matcher(buf);
                while (matcher.find()){

                    printWriter.println(matcher.group());
                }

            }

        } catch (MalformedURLException e) {
            System.out.println("url 打开异常");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            printWriter.close();;
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @Test
    public void testGetResource(){
        String path = this.getClass().getClassLoader().getResource("").getPath();
        System.out.println(path);
    }
}
