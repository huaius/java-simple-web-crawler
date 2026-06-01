package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class WebPageParser {
    private static final String LINK = "a";
    private static final String HREF = "abs:href";

    public Optional<Document> getDocument(final String url) {
        try {
            return Optional.of(Jsoup.connect(url).get());
        } catch (IOException e) {
            // Todo: log error
            return Optional.empty();
        }
    }

    public Set<String> getLinks(final Document doc) {
        return doc.select(LINK).stream()
                .map(this::getLinkUrl)
                .collect(Collectors.toSet());
    }

    public String getLinkUrl(final Element link) {
        return link.attr(HREF);
    }

    public Set<String> process(final String url) {
        final Optional<Document> doc = getDocument(url);
        if (doc.isPresent()) {
            return getLinks(doc.get());
        } else {
            return Set.of();
        }
    }
}
