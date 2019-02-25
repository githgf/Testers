package cn.hgf.crawler.address;

import cn.hgf.crawler.HttpClientDemo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.HttpEntity;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.regex.Pattern;

/**
 *
 * @author hans
 * @date 2019-02-23$
 * @description 爬数据线程$
 *
 */

@Setter
@Getter
public class AreaThread {
    int num = 1;


    public List<AreaVO> run(String url,int level) {
        if (num >= 10)return null;
        Document document = null;
        ArrayList<AreaVO> areaVOS = new ArrayList<>();
        document = getDocumentByHttp(url);
        if (document == null)return null;

            String tagName = LevelMappingEnum.getTagPrefix(level);
            Elements table_tbody_tr = document.body().select("table tbody tr." + tagName + "tr");

            if (level == 1){
                table_tbody_tr = table_tbody_tr.select("td");
            }


            for (Element element : table_tbody_tr) {

                AreaVO childrenArea = new AreaVO();

                Elements tds = element.select("td");
                String name = null;
                String code = null;
                String nextUrl = null;

                if (tds.size() == 2){

                    code = tds.get(0).text();

                    Element aHrefEle = tds.get(0).selectFirst("a");
                    if (aHrefEle != null){
                        nextUrl = aHrefEle.attr("href");
                    }

                    name = tds.get(1).text();
                }else if (tds.size() == 1){
                    name = tds.get(0).text();
                    if (tds.get(0) == null || tds.get(0).selectFirst("a") == null){
                        continue;
                    }
                    nextUrl = tds.get(0).selectFirst("a").attr("href");
                }else if (tds.size() == 3 && level == LevelMappingEnum.VILLAGE.code){
                    code = tds.get(0).text();
                    name = tds.get(2).text();
                }
                else {
                    continue;
                }

                if (name == null)continue;

                Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
                boolean matches = pattern.matcher(name).matches();
                if (matches){
                    System.out.println();
                }

                childrenArea.setCode(code);
                childrenArea.setName(name);
                childrenArea.setLevel(level);

                if (nextUrl != null){
                    nextUrl = getUrl(nextUrl,url);
                    num++;
                    childrenArea.setChildren(run(nextUrl,level + 1));
                }
                areaVOS.add(childrenArea);

                System.out.println(JSON.toJSONString(childrenArea));
            }



        return areaVOS;
    }

    public Document getDocument(String url) throws IOException {
        try{
            Connection conn = Jsoup.connect(url).timeout(50000);
            conn.header("Accept", "*/*");
            conn.header("Accept-Encoding", "gzip, deflate, br");
            conn.header("Connection","keep-alive");
            conn.header("Accept-Language", "zh-CN,zh;q=0.9");
            conn.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
            Connection.Response response = null;
//            do {
//                response = conn.execute();
//                if(count > 0){
//                    Document doc = Jsoup.connect(url)
//                            .data("query", "Java")
//                            .userAgent("Mozilla")
//                            .cookie("auth", "token")
//                            .timeout(3000)
//                            .post();
//                    return doc;
//                }
//            }while(response.statusCode() != 200);
            return  conn.get();
        }catch (IOException e){
            throw e;
        }
    }

    public Document getDocumentByHttp(String url){
        HttpEntity httpEntity = HttpClientDemo.getHttpEntity(url);

        String string = HttpClientDemo.getStringHttpEntity(httpEntity,"gb2312");

        return Jsoup.parse(string);
    }

    public String getUrl(String urlHref,String urlPrefix){
        if (urlHref == null)return null;

        if (urlPrefix.contains(".html")){

            urlPrefix = urlPrefix.substring(0,urlPrefix.lastIndexOf("/") + 1);
        }

        return urlPrefix + urlHref;

    }


}
