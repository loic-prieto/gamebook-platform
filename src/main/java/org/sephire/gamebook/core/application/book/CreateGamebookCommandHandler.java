package org.sephire.gamebook.core.application.book;


import io.vavr.control.Option;
import org.sephire.gamebook.core.application.shared.commands.CommandHandler;
import org.sephire.gamebook.core.application.shared.commands.CommandResult;
import org.sephire.gamebook.core.domain.model.book.Gamebook;
import org.sephire.gamebook.core.domain.model.book.GamebookCreatedEvent;
import org.sephire.gamebook.core.domain.model.book.GamebookRepository;
import org.sephire.gamebook.core.domain.model.book.NewGamebookSpecification;
import org.sephire.gamebook.core.domain.shared.events.EventEmitter;

/**
 * Covers the create gamebook use case.
 */
public class CreateGamebookCommandHandler implements CommandHandler<CreateGamebookCommand, Gamebook> {

    private GamebookRepository gamebookRepository;
    private EventEmitter eventEmitter;

    public CreateGamebookCommandHandler(GamebookRepository gamebookRepository) {
        this.gamebookRepository = gamebookRepository;
    }

    @Override
    public CommandResult<Gamebook> execute(CreateGamebookCommand command) {
        Gamebook gamebook = Gamebook.minimalBook(command.getIdentifier(), command.getTitle(), command.getAuthor());

        boolean isValid = new NewGamebookSpecification(gamebookRepository).isSatisfiedBy(gamebook);

        Option<Gamebook> result = isValid ? Option.of(gamebook) : Option.none();
        if (isValid) {
            gamebookRepository.storeGamebook(gamebook);
        }

        eventEmitter.fireEvent(new GamebookCreatedEvent(gamebook));

        return () -> result;
    }
}
