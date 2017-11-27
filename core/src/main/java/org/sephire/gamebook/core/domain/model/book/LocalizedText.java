package org.sephire.gamebook.core.domain.model.book;

import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Stream;
import io.vavr.control.Option;

import java.util.Locale;

/**
 * A localized text is a value object that holds text in different languages.
 * The language is identified by a Locale object.
 */
public class LocalizedText {

    private Map<Locale, String> localized_texts;

    public LocalizedText(Tuple2<Locale, String>... entries) {
        this.localized_texts = Stream.of(entries)
                .foldLeft(HashMap.empty(), (map, entry) -> map.put(entry));
    }

    public Option<String> getTextFor(Locale locale) {
        return this.localized_texts.get(locale);
    }

    public List<Tuple2<Locale, String>> getAllTitles() {
        return this.localized_texts.toList();
    }
}
