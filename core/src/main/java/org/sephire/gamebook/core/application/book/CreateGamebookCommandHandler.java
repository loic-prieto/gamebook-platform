package org.sephire.gamebook.core.application.book;


import io.vavr.collection.List;
import io.vavr.control.Either;
import org.sephire.gamebook.core.application.shared.commands.CommandError;
import org.sephire.gamebook.core.application.shared.commands.CommandHandler;
import org.sephire.gamebook.core.application.shared.commands.ExceptionalError;
import org.sephire.gamebook.core.application.shared.commands.RepositoryError;
import org.sephire.gamebook.core.domain.model.book.Gamebook;
import org.sephire.gamebook.core.domain.model.book.GamebookCreatedEvent;
import org.sephire.gamebook.core.domain.model.book.GamebookRepository;
import org.sephire.gamebook.core.domain.model.book.NewGamebookSpecification;
import org.sephire.gamebook.core.domain.shared.events.DomainException;
import org.sephire.gamebook.core.domain.shared.events.EventEmitter;
import org.sephire.gamebook.core.domain.shared.repositories.RepositoryException;

/**
 * Covers the create sephire.org.gamebook use case.
 */
public class CreateGamebookCommandHandler implements CommandHandler<CreateGamebookCommand, Gamebook> {

    private GamebookRepository gamebookRepository;
    private EventEmitter eventEmitter;

    public CreateGamebookCommandHandler(GamebookRepository gamebookRepository, EventEmitter eventEmitter) {
        this.gamebookRepository = gamebookRepository;
        this.eventEmitter = eventEmitter;
    }

    @Override
    public Either<List<CommandError>, Gamebook> execute(CreateGamebookCommand command) {

        List<CommandError> errors = List.empty();
        Gamebook gamebook = null;

        try {
            gamebook = Gamebook.minimalBook(command.getIdentifier(), command.getTitle(), command.getAuthor());
            NewGamebookSpecification bookSpecification = new NewGamebookSpecification(gamebookRepository);
            if (bookSpecification.isSatisfiedBy(gamebook)) {
                gamebookRepository.storeGamebook(gamebook);
                eventEmitter.fireEvent(new GamebookCreatedEvent(gamebook));
            } else {
                errors = errors.append(new InvalidGamebookError());
            }
        } catch (RepositoryException e) {
            errors = errors.append(new RepositoryError());
            eventEmitter.fireEvent(new DomainException(e));
        } catch (Throwable t) {
            errors = errors.append(new ExceptionalError(t));
            eventEmitter.fireEvent(new DomainException(t));
        }

        return errors.isEmpty() ?
                Either.right(gamebook) :
                Either.left(errors);
    }
}