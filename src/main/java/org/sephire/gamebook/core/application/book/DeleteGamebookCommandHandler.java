package org.sephire.gamebook.core.application.book;

import org.sephire.gamebook.core.application.shared.commands.CommandHandler;
import org.sephire.gamebook.core.application.shared.commands.CommandResult;
import org.sephire.gamebook.core.domain.model.book.Gamebook;
import org.sephire.gamebook.core.domain.model.book.GamebookDeletedEvent;
import org.sephire.gamebook.core.domain.model.book.GamebookRepository;
import org.sephire.gamebook.core.domain.shared.events.EventEmitter;

public class DeleteGamebookCommandHandler implements CommandHandler<DeleteGamebookCommand, Void> {

    private GamebookRepository gamebookRepository;
    private EventEmitter eventEmitter;

    public DeleteGamebookCommandHandler(GamebookRepository gamebookRepository, EventEmitter eventEmitter) {
        this.gamebookRepository = gamebookRepository;
        this.eventEmitter = eventEmitter;
    }

    @Override
    public CommandResult<Void> execute(DeleteGamebookCommand command) {

        Gamebook gamebook = gamebookRepository.findGamebook(command.getIdentifier())
                .getOrElseThrow(() -> new RuntimeException("Book " + command.getIdentifier() + " was not found"));

        gamebookRepository.deleteGamebook(gamebook);
        eventEmitter.fireEvent(new GamebookDeletedEvent(gamebook));

        return () -> null;
    }
}
