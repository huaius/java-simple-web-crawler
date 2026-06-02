package org.example;

import org.junit.Before;
import org.junit.Test;

public class WebCrawlerTest {

    private WebCrawler crawler;

    @Before
    public void setUp() throws Exception {
        WebPageParser parser = new WebPageParser();
        crawler = new WebCrawler(parser, "https://crawlme.monzo.com/", true);
    }

    @Test
    public void run() {
        crawler.run();
    }
}
