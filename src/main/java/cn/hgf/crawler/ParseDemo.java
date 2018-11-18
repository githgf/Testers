package cn.hgf.crawler;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * @author gaofan
 * @date 2018-11-13 10:25
 */
public class ParseDemo {

    @Test
    public void testJsoup(){

//        String downHtmlFile = HttpClientDemo.downHtmlFile("https://www.csdn.net/",null,null);
        try {
            Document document = Jsoup.connect("https://www.jianshu.com/p/443219930c5b")
                    .header("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:61.0) Gecko/20100101 Firefox/61.0")
                    .header("Content-Type", "application/json; charset=utf-8")
                    .header("Cookie", "_m7e_session=2e930226548924f569cb27ba833346db;locale=zh-CN;default_font=font2;read_mode=day")
                    .get();
            System.out.println(document.body());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testHttpClient(){
        ArrayList<Header> objects = new ArrayList<>();
        objects.add(new BasicHeader("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:61.0) Gecko/20100101 Firefox/61.0"));
        objects.add(new BasicHeader("Content-Type", "application/json; charset=utf-8"));
//        objects.add(new BasicHeader("Cookie", "_m7e_session=2e930226548924f569cb27ba833346db;locale=zh-CN;default_font=font2;read_mode=day"));
        HttpClientDemo.downHtmlFile("https://www.jianshu.com/",null,Arrays.asList(HttpClientDemo.defaultHeaders));

    }

    @Test
    public void parse(){

        File file = new File("D:\\crawler\\html\\jianshu.html");
        try {
            Document document = Jsoup.parse(file, "utf-8");
            Element body = document.body();
            Elements noteListEles = body.select("ul.note-list > li");

            Iterator<Element> iterator = noteListEles.iterator();
            while (iterator.hasNext()){
                //            <li id="note-33942349" data-note-id="33942349" class="">
                //              <div class="content">
                //                <a class="title" target="_blank" href="/p/cf6cfe7b24d6">生下儿子，婆婆送三千红包，妈来送鸡蛋，看了眼厨房，把家砸了</a>
                //                <p class="abstract">
                //                  导语：生下儿子，婆婆送三千红包，妈来送鸡蛋，看了眼厨房，把家砸了 我自从怀孕后经常觉得饿，刚起床就想找点吃的，看到身边熟睡的老公，我气不打一处来...
                //                </p>
                //                <div class="meta">
                //                  <a class="nickname" target="_blank" href="/u/2ef55ff77a02">红尘炫影</a>
                //                  <a target="_blank" href="/p/cf6cfe7b24d6#comments">
                //                    <i class="iconfont ic-list-comments"></i> 58
                //                  </a>
                //                  <span>
                //                    <i class="iconfont ic-list-like"></i> 37</span>
                //                </div>
                //              </div>
                //            </li>
                System.out.println("===========================================================================");
                Element element = iterator.next();

                Element titleElement = element.selectFirst("a.title");
                Node titleNode = titleElement.childNode(0);
                System.out.println(String.format("标题：%s",titleNode != null ? titleNode.outerHtml() : ""));

                Element abstractElement = element.selectFirst("p.abstract");
                Node abstractNode = abstractElement.childNode(0);
                System.out.println(String.format("大概：%s",abstractNode != null ? abstractNode.outerHtml() : ""));

                Element nickNameElement = element.selectFirst("a.nickname");
                Node nickNameNode = nickNameElement.childNode(0);
                System.out.println(String.format("作者昵称：%s",nickNameNode != null ? nickNameNode.outerHtml() : ""));

                // E > F 代表的是E 是 F 的直接上级
                Element likeElement = element.selectFirst("div.meta > span > i");
                Node likeNode = likeElement.nextSibling();
                System.out.println(String.format("点赞数：%s",likeNode != null ? likeNode.outerHtml() : ""));

                // E ~ F 代表F 是 E 的兄弟级(F 在 E 之后)，（不需要紧挨）
                Element readNumElement = element.selectFirst("a.nickname ~ a > i");
                Node readNumNode = readNumElement.nextSibling();
                System.out.println(String.format("阅读量：%s",readNumNode != null ? readNumNode.outerHtml() : ""));


                //当class值带有空格不能通过class选择
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }





}
