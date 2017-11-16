package com.myapp.crawler4j;

import com.myapp.client.CrawlClient;
import com.myapp.main.MaintenanceService;
import com.myapp.util.RedisOnMessageUtil;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by gaorui on 17/4/20.
 */
public class MyCraler extends WebCrawler {

    private static Logger logger = Logger.getLogger(MyCraler.class);

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
            + "|png|mp3|mp3|zip|gz))$");

    private static String page1 = "http://www.kxdaili.com/";
    private static String page2 = "http://www.ip181.com/";


    /**
     * This method receives two parameters. The first parameter is the page
     * in which we have discovered this new url and the second parameter is
     * the new url. You should implement this function to specify whether
     * the given url should be crawled or not (based on your crawling logic).
     * In this example, we are instructing the crawler to ignore urls that
     * have css, js, git, ... extensions and to only accept urls that start
     * with "http://www.ics.uci.edu/". In this case, we didn't need the
     * referringPage parameter to make the decision.
     */
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        return !FILTERS.matcher(href).matches();
    }

    /**
     * This function is called when a page is fetched and ready
     * to be processed by your program.
     */
    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String text = htmlParseData.getText();
            String html = htmlParseData.getHtml();
            Set<WebURL> links = htmlParseData.getOutgoingUrls();


            try {
                Document doc = Jsoup.parse(html);
                if (page1.equals(url)) {
                    for (int i = 1; i < 10; i++) {
                        Elements trs = doc.select("table").get(1).select("tr");
                        Elements tds = trs.get(i).select("td");

                        String ip = tds.get(0).text();
                        int port = Integer.parseInt(tds.get(1).text());
                        logger.info(url+"#"+ip+":"+port);
                        String area = tds.get(5).text();
                        CrawlClient.proxyPool.add(ip, port);
                        //RedisOnMessageUtil.Push(area, ip, port);
                    }
                } else if (page2.equals(url)) {
                    for (int i = 1; i < 50; i++) {
                        Elements trs = doc.select("table").select("tr");
                        Elements tds = trs.get(i).select("td");

                        String ip = tds.get(0).text();
                        int port = Integer.parseInt(tds.get(1).text());
                        logger.info(url+"#"+ip+":"+port);
                        String area = tds.get(5).text();
                        CrawlClient.proxyPool.add(ip, port);

                        //RedisOnMessageUtil.Push(area, ip, port);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
