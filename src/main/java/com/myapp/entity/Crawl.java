package com.myapp.entity;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;

/**
 * Created by gaorui on 17/11/6.
 */
public class Crawl {

    private String crawlName;
    private CrawlConfig crawlConfig;

    public String getCrawlName() {
        return crawlName;
    }

    public void setCrawlName(String crawlName) {
        this.crawlName = crawlName;
    }

    public PageFetcher getPageFetcherer() {
        return pageFetcherer;
    }

    public void setPageFetcherer(PageFetcher pageFetcherer) {
        this.pageFetcherer = pageFetcherer;
    }

    public CrawlController getCrawlController() {
        return crawlController;
    }

    public void setCrawlController(CrawlController crawlController) {
        this.crawlController = crawlController;
    }

    public CrawlConfig getCrawlConfig() {
        return crawlConfig;
    }

    public void setCrawlConfig(CrawlConfig crawlConfig) {
        this.crawlConfig = crawlConfig;
    }

    private PageFetcher pageFetcherer;
    private CrawlController crawlController;
}
