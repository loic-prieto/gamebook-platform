package org.sephire.gamebook.awsapi.configuration.dagger;

import dagger.Module;
import dagger.Provides;
import org.sephire.gamebook.core.domain.model.account.UserAccountRepository;
import org.sephire.gamebook.core.domain.model.book.GamebookRepository;
import org.sephire.gamebook.core.domain.shared.events.EventEmitter;
import org.sephire.gamebook.core.infrastructure.account.UserAccountRepositoryInMemoryImpl;
import org.sephire.gamebook.core.infrastructure.book.GamebookRepositoryInMemoryImpl;
import org.sephire.gamebook.core.infrastructure.events.EventEmitterInMemoryImpl;

@Module
public class GamebookCoreServices {

    @Provides
    public static EventEmitter eventEmitter() {
        return new EventEmitterInMemoryImpl();
    }

    @Provides
    public static UserAccountRepository userAccountRepository() {
        return new UserAccountRepositoryInMemoryImpl();
    }

    @Provides
    public static GamebookRepository gamebookRepository() {
        return new GamebookRepositoryInMemoryImpl();
    }
}
