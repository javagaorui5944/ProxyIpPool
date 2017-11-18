package com.myapp.crawler4j;

import com.myapp.entity.Crawl;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.apache.log4j.Logger;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.myapp.util.PageConfig.list;

/**
 * Created by gaorui on 17/4/20.
 */
public class Controller {
    private static Logger logger = Logger.getLogger(Controller.class);

    public static void fetchProxyIp() {
        String crawlStorageFolder = Paths.get(".").toString();;
        int numberOfCrawlers = 1;

        int size = list.size();
        List<Crawl> crawlList = new ArrayList(size);
        for (int i = 0 ; i < list.size() ; i++) {
            try {
                Crawl c = new Crawl();
                CrawlConfig config = new CrawlConfig();
                config.setMaxDepthOfCrawling(0);
                config.setPolitenessDelay(0);
                config.setCrawlStorageFolder(crawlStorageFolder + File.separator + "Controller"+i);
                c.setCrawlConfig(config);
                PageFetcher pageFetcher = new PageFetcher(config);
                c.setPageFetcherer(pageFetcher);
                RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
                RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
                CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
                controller.addSeed(list.get(i));
                c.setCrawlController(controller);
                c.setCrawlName("Controller"+i);
                crawlList.add(c);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }

        for (Crawl c : crawlList) {
            c.getCrawlController().start(MyCraler.class, numberOfCrawlers);
            c.getCrawlController().waitUntilFinish();
            logger.info("Crawler "+c.getCrawlName()+" is finished.");
        }
    }
}