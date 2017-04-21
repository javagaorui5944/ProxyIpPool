package crawler4j;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

/**
 * Created by gaorui on 17/4/20.
 */
public class Controller {
    public static void main(String[] args) throws Exception {
        String crawlStorageFolder = "/Users/gaorui/";
        int numberOfCrawlers = 1;

        CrawlConfig config1 = new CrawlConfig();
        //config.setResumableCrawling(true);
        config1.setProxyHost("124.88.67.39");
        config1.setProxyPort(80);
        config1.setMaxDepthOfCrawling(0);

        config1.setCrawlStorageFolder(crawlStorageFolder+"/Controller1");

        CrawlConfig config2 = new CrawlConfig();
        //config.setResumableCrawling(true);
        config2.setProxyHost("124.88.67.39");
        config2.setProxyPort(80);
        config2.setMaxDepthOfCrawling(0);

        config2.setCrawlStorageFolder(crawlStorageFolder+"/Controller2");

        /*
         * Instantiate the controller for this crawl.
         */
        PageFetcher pageFetcher1 = new PageFetcher(config1);
        PageFetcher pageFetcher2 = new PageFetcher(config2);

        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher1);
        CrawlController controller1 = new CrawlController(config1, pageFetcher1, robotstxtServer);
        CrawlController controller2 = new CrawlController(config2, pageFetcher2, robotstxtServer);

        /*
         * For each crawl, you need to add some seed urls. These are the first
         * URLs that are fetched and then the crawler starts following links
         * which are found in these pages
         */
        controller1.addSeed("http://www.ip181.com/");
        controller2.addSeed("http://www.kxdaili.com/");
        //controller.addSeed("http://www.ics.uci.edu/");

        /*
         * Start the crawl. This is a blocking operation, meaning that your code
         * will reach the line after this only when crawling is finished.
         */
        controller1.start(MyCraler.class, numberOfCrawlers);
        controller2.start(MyCraler.class, numberOfCrawlers);

        controller1.waitUntilFinish();
        System.out.println("Crawler 1 is finished.");

        controller2.waitUntilFinish();
        System.out.println("Crawler 2 is finished.");


    }
}