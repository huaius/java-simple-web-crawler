package org.example;

import lombok.SneakyThrows;
import org.jsoup.nodes.Element;

import java.net.URI;
import java.util.Set;

public class WebCrawler {

    private final WebPageParser webPageParser;
    private final String url;
    private final String domain;

    public WebCrawler(final WebPageParser parser, final String urlAddress) {
        webPageParser = parser;
        url = urlAddress;
        domain = getDomainName(url);
    }

    public void run() {
        final Set<Element> links = webPageParser.process(url);
        System.out.println(domain);
        links.forEach(link -> {
            System.out.println(webPageParser.getLinkUrl(link));
        });
    }

    @SneakyThrows
    public String getDomainName(final String url) {
        final URI uri = new URI(url);
        final String domain = uri.getHost();
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }
}
