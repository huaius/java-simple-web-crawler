package org.example;

import lombok.SneakyThrows;
import org.jsoup.nodes.Element;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

public class WebCrawler {

    private final WebPageParser webPageParser;
    private final String rootUrl;
    private final String domain;
    private final Map<String, Set<String>> allPages;

    public WebCrawler(final WebPageParser parser, final String urlAddress) {
        webPageParser = parser;
        rootUrl = urlAddress;
        domain = getDomainName(rootUrl);
        allPages = new HashMap<>();
    }

    @SneakyThrows
    public void run() {
        ForkJoinPool customThreadPool = new ForkJoinPool(16);

        Set<String> pageUrls = Set.of(rootUrl);
        while (!pageUrls.isEmpty()) {
            Set<String> finalPageUrls = pageUrls;
            pageUrls = customThreadPool.submit(
                    () -> finalPageUrls.parallelStream()
                            .map(this::processUrl)
                            .flatMap(Set::stream)
                            .collect(Collectors.toSet())).get();
        }
        customThreadPool.shutdown();
        outputPages();
    }

    Set<String> processUrl(final String url) {
        if (!allPages.containsKey(url) && isInScope(url)) {
            final Set<String> links = webPageParser.process(url);
            allPages.put(url, links);
            return links;
        } else {
            return Set.of();
        }
    }

    void outputPages() {
        for(String pageUrl : allPages.keySet()) {
            final Set<String> links = allPages.get(pageUrl);
            System.out.println(pageUrl);
            links.forEach(link -> {
                System.out.println("==> " + link);
            });
        }
    }

    @SneakyThrows
    public String getDomainName(final String url) {
        final URI uri = new URI(url);
        final String domain = uri.getHost();
        if (Objects.isNull(domain)) {
            return null;
        } else {
            return domain.startsWith("www.") ? domain.substring(4) : domain;
        }
    }

    private boolean isInScope(final String url) {
        System.out.println(url);
        final String thisDomain = getDomainName(url);
        return Objects.equals(thisDomain, domain);
    }
}
