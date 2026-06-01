package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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

    public Set<Element> getLinks(final Document doc) {
        return new HashSet<>(doc.select(LINK));
    }

    public String getLinkUrl(final Element link) {
        return link.attr(HREF);
    }

    public Set<Element> process(final String url) {
        final Optional<Document> doc = getDocument(url);
        if (doc.isPresent()) {
            return getLinks(doc.get());
        } else {
            return Set.of();
        }
    }
}
