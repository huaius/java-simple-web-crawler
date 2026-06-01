package org.example;

import org.jsoup.nodes.Element;

import java.util.List;

public class WebCrawler {

    private final WebPageParser webPageParser;

    public WebCrawler(final WebPageParser parser) {
        webPageParser = parser;
    }

    public void run(final String url) {
        final List<Element> links = webPageParser.process(url);
        links.forEach(link -> {
            System.out.println(webPageParser.getLinkUrl(link));
        });
    }
}
