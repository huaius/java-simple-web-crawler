package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public ArrayList<Element> getLinks(final Document doc) {
        return doc.select(LINK);
    }

    public String getLinkUrl(final Element link) {
        return link.attr(HREF);
    }

    public List<Element> process(final String url) {
        final Optional<Document> doc = getDocument(url);
        if (doc.isPresent()) {
            return getLinks(doc.get());
        } else {
            return List.of();
        }
    }
}
