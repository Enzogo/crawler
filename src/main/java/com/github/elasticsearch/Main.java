package com.github.elasticsearch;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {
        // 未处理的链接池
        List<String> linkPool = new ArrayList<>();
        // 已处理的连接池
        Set<String> processedPool = new HashSet<>();
        linkPool.add("https://www.sina.cn");


        while (true) {
            if (linkPool.isEmpty()) {
                break;
            }
            String link = linkPool.get(linkPool.size() - 1);

            // ArrayList从尾部删除更有效率
            //     Removes the element at the specified position in this list (optional
            //     operation).  Shifts any subsequent elements to the left (subtracts one
            //     from their indices).  Returns the element that was removed from the
            //     list.
            linkPool.remove(linkPool.size() - 1);

            // 等同于上面的两步操作
            // String link  = linkPool.remove(linkPool.size() -1);

            if (processedPool.contains(link)) {
                continue;
            }
            if ((link.contains("news.sina.cn") || "https://www.sina.cn".equals(link))) {
                // 需要处理的链接 之处理新浪站内的链接
                CloseableHttpClient httpclient = HttpClients.createDefault();

                System.out.println(link);
                if (link.startsWith("//")) {
                    link = "https:" + link;
                }

                HttpGet httpGet = new HttpGet(link);

                httpGet.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.80 Safari/537.36");

                try (CloseableHttpResponse response1 = httpclient.execute(httpGet)) {
//                    System.out.println(link);
                    System.out.println(response1.getStatusLine());
                    HttpEntity entity1 = response1.getEntity();
                    String html = EntityUtils.toString(entity1);
                    Document doc = Jsoup.parse(html);
                    ArrayList<Element> links = doc.select("a");

                    for (Element aTag : links) {
                        linkPool.add(aTag.attr("href"));
                    }

                    //加入这是一个新闻的详情页面 就存入数据库 否则 就什么都不做

                    ArrayList<Element> articleTags = doc.select("article");
                    if (!articleTags.isEmpty()) {
                        for (Element articleTag : articleTags) {
                            String title = articleTags.get(0).child(0).text();
                            System.out.println(title);
                        }
                    }

                    processedPool.add(link);

                }
            } else {
                // 不需要处理的链接
                continue;
            }

        }
    }
}
