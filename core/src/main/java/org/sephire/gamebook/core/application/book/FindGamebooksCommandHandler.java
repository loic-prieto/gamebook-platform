package org.sephire.gamebook.core.application.book;

import io.vavr.collection.List;
import io.vavr.collection.Set;
import io.vavr.control.Either;
import org.sephire.gamebook.core.application.shared.commands.CommandError;
import org.sephire.gamebook.core.application.shared.commands.CommandHandler;
import org.sephire.gamebook.core.application.shared.commands.ExceptionalError;
import org.sephire.gamebook.core.application.shared.commands.RepositoryError;
import org.sephire.gamebook.core.domain.model.book.Gamebook;
import org.sephire.gamebook.core.domain.model.book.GamebookRepository;
import org.sephire.gamebook.core.domain.shared.events.DomainException;
import org.sephire.gamebook.core.domain.shared.events.EventEmitter;
import org.sephire.gamebook.core.domain.shared.repositories.RepositoryException;

public class FindGamebooksCommandHandler implements CommandHandler<FindGamebooksCommand, Set<Gamebook>> {

    private GamebookRepository gamebookRepository;
    private EventEmitter eventEmitter;

    public FindGamebooksCommandHandler(GamebookRepository gamebookRepository, EventEmitter eventEmitter) {
        this.gamebookRepository = gamebookRepository;
        this.eventEmitter = eventEmitter;
    }

    @Override
    public Either<List<CommandError>, Set<Gamebook>> execute(FindGamebooksCommand command) {
        List<CommandError> errors = List.empty();
        Set<Gamebook> gamebooks = null;

        try {
            gamebooks = gamebookRepository.findGamebooks(command.getTitleFilter());
        } catch (RepositoryException e) {
            errors = errors.append(new RepositoryError());
            eventEmitter.fireEvent(new DomainException(e));
        } catch (Throwable t) {
            errors = errors.append(new ExceptionalError(t));
            eventEmitter.fireEvent(new DomainException(t));
        }

        return errors.isEmpty() ?
                Either.right(gamebooks) :
                Either.left(errors);
    }
}
