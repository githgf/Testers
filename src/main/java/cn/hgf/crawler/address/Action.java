package cn.hgf.crawler.address;

import com.alibaba.fastjson.JSONArray;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.List;
import java.util.concurrent.*;

/**
 *
 * @author hans
 * @date 2019-02-23$
 * @description 爬取国家统计区地址位置省市区街道、村数据$
 *
 */

public class Action {

    private static final ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("areaCrawler-pool-%d").build();
    //创建线程池
    public static final ExecutorService pool     = new ThreadPoolExecutor(10, 200, 0L,
            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(16), namedThreadFactory,
            new ThreadPoolExecutor.AbortPolicy());

    public static String URL_PREFIX = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2017/";
    public static void main(String[] args){

        String url = URL_PREFIX;

        List<AreaVO> run = new AreaThread().run(url, 1);

        System.out.println();

    }

}
