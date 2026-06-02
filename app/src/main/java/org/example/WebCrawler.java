package org.example;

import lombok.SneakyThrows;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class WebCrawler {
    private static final Logger LOGGER = Logger.getLogger( WebCrawler.class.getName() );
    private static final String FILE_NAME = "all_site_links.txt";

    private final WebPageParser webPageParser;
    private final String rootUrl;
    private final String domain;
    private final Map<String, Set<String>> allPages;
    private final boolean isTesting;

    public WebCrawler(final WebPageParser parser, final String urlAddress, final boolean testing) {
        webPageParser = parser;
        rootUrl = urlAddress;
        domain = getDomainName(rootUrl);
        allPages = new HashMap<>();
        isTesting = testing;
    }

    @SneakyThrows
    public void run() {
        ForkJoinPool customThreadPool = new ForkJoinPool(32);

        Set<String> pageUrls = Set.of(rootUrl);
        int level = 0;
        while (!pageUrls.isEmpty()) {
            level++;
            if (isTesting && level > 3) {
                break;
            }
            LOGGER.info("======== Current level: " + level);
            final Set<String> finalPageUrls = pageUrls;
            pageUrls = customThreadPool.submit(
                    () -> finalPageUrls.parallelStream()
                            .filter(url -> !allPages.containsKey(url) && isInScope(url))
                            .map(this::processUrl)
                            .flatMap(Set::stream)
                            .collect(Collectors.toSet())).get();
        }
        customThreadPool.shutdown();
        outputPages();
    }

    Set<String> processUrl(final String url) {
        //LOGGER.info("Processing: " + url);
        final Set<String> links = webPageParser.process(url);
        allPages.put(url, links);
        return links;
    }

    @SneakyThrows
    void outputPages() {
        LOGGER.info("Writing result to file: " + FILE_NAME);
        final FileWriter myWriter = new FileWriter(FILE_NAME);

        for(String pageUrl : allPages.keySet()) {
            final Set<String> links = allPages.get(pageUrl);
            myWriter.write(pageUrl + "\n");
            for (final String link: links) {
                myWriter.write("==> " + link + "\n");
            }
        }
        myWriter.close();  // must close manually
        LOGGER.info("Complete.");
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
        final String thisDomain = getDomainName(url);
        return Objects.equals(thisDomain, domain);
    }
}
