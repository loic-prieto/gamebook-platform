package org.sephire.gamebook.core.application.book;

import io.vavr.control.Option;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sephire.gamebook.core.application.shared.commands.Command;

@RequiredArgsConstructor
public class FindGamebooksCommand implements Command {
    @Getter
    private final Option<String> titleFilter;
}
