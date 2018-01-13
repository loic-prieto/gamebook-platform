package org.sephire.gamebook.core.application.book;

import io.vavr.control.Option;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FindGamebooksCommand {
    @Getter
    private final Option<String> titleFilter;
}
