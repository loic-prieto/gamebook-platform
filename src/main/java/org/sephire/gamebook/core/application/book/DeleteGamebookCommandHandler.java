package org.sephire.gamebook.core.application.book;

import io.vavr.collection.List;
import io.vavr.control.Either;
import org.sephire.gamebook.core.application.shared.commands.CommandError;
import org.sephire.gamebook.core.application.shared.commands.CommandHandler;
import org.sephire.gamebook.core.application.shared.commands.ExceptionalError;
import org.sephire.gamebook.core.application.shared.commands.RepositoryError;
import org.sephire.gamebook.core.domain.model.book.GamebookDeletedEvent;
import org.sephire.gamebook.core.domain.model.book.GamebookRepository;
import org.sephire.gamebook.core.domain.shared.events.DomainException;
import org.sephire.gamebook.core.domain.shared.events.EventEmitter;
import org.sephire.gamebook.core.domain.shared.repositories.RepositoryException;

public class DeleteGamebookCommandHandler implements CommandHandler<DeleteGamebookCommand, Void> {

    private GamebookRepository gamebookRepository;
    private EventEmitter eventEmitter;

    public DeleteGamebookCommandHandler(GamebookRepository gamebookRepository, EventEmitter eventEmitter) {
        this.gamebookRepository = gamebookRepository;
        this.eventEmitter = eventEmitter;
    }


    @Override
    public Either<List<CommandError>, Void> execute(DeleteGamebookCommand command) {

        List<CommandError> errors = List.empty();

        try {
            gamebookRepository.findGamebook(command.getIdentifier())
                    .peek((gamebook) -> {
                        gamebookRepository.deleteGamebook(gamebook);
                        eventEmitter.fireEvent(new GamebookDeletedEvent(gamebook));
                    });
        } catch (RepositoryException e) {
            errors = errors.append(new RepositoryError());
            eventEmitter.fireEvent(new DomainException(e));
        } catch (Throwable t) {
            errors = errors.append(new ExceptionalError(t));
            eventEmitter.fireEvent(new DomainException(t));
        }

        return errors.isEmpty() ?
                Either.right(null) :
                Either.left(errors);
    }
}
