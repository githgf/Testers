package cn.hgf.crawler;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

/**
 * @author gaofan
 * @date 2018-11-11 16:53
 */
public class HttpClientDemo {
    static String SOURCE_PATH = HttpClientDemo.class.getClassLoader().getResource("").getPath();
    static String HTML_PATH = "D:\\crawler\\html";
    /**
     *  将url访问的地址下载为html文件
     * @param url       连接地址
     * @return          文件地址
     */
    public static String downHtmlFile(String url,String path,List<Header> headers){
        if (StringUtil.isBlank(url)){return null;}

        String[] split = url.split("\\.");
        String htmlFileName = split[split.length - 2] + ".html";

        path = StringUtil.isBlank(path) ? HTML_PATH : path;

        String filePath = path + File.separator + htmlFileName;
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(filePath)) ){

            HttpEntity httpEntity = getHttpEntity(url,headers);
            if (httpEntity != null){

                String string = EntityUtils.toString(httpEntity, "utf-8");
                printWriter.println(string);
            }

            return filePath;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static String downHtmlFile(String url){

        return downHtmlFile(url,null,null);
    }



    @Test
    public void testWay_2(){

        HttpEntity httpEntity = getHttpEntity("http://www.itcast.cn");

        String string = getStringHttpEntity(httpEntity,null);

        Document parse = Jsoup.parse(string);

        Element body = parse.body();
        Elements select = body.select("a[href]");
        Iterator<Element> iterator = select.iterator();
        while (iterator.hasNext()){
            Element next = iterator.next();
            Attributes attributes = next.attributes();
            List<Attribute> attributesList = attributes.asList();
            for (Attribute attribute : attributesList) {

                System.out.print(attribute.getKey() + "=" + attribute.getValue() + "\t");

            }

            System.out.println("");
        }
    }

    public static String getStringHttpEntity(HttpEntity httpEntity,String charest){
        String result = null;
        if (httpEntity == null){
            return result;
        }
        charest = StringUtil.isBlank(charest) ? "utf-8" : charest;


        try {
            result =EntityUtils.toString(httpEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;

    }

    public static HttpEntity getHttpEntity(String url){

        return getHttpEntity(url,null);
    }

    public static HttpEntity getHttpEntity(String url, List<Header> headers){

        HttpClient httpClient = defaultHttpClientWithdefaultParam();
        HttpGet httpGet = new HttpGet(url);

        if (headers != null){

            for (Header header : headers) {
                httpGet.addHeader(header.getName(),header.getValue());
            }
        }



        HttpResponse execute = null;
        try {
            execute = httpClient.execute(httpGet);
            if (execute != null){

                return execute.getEntity();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static HttpClient defaultHttpClientWithdefaultParam(){

        HttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,10000)
                .setParameter(CoreConnectionPNames.SO_TIMEOUT,10000);
        return httpClient;
    }

    public static Header[] defaultHeaders = new Header[]{
            new BasicHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36"),
//            new BasicHeader("Accept-Language","zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7"),
//            new BasicHeader("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8"),
//            new BasicHeader("accept-encoding","gzip, deflate, br"),
            new BasicHeader("Content-Type", "application/json; charset=utf-8")
    };


}
