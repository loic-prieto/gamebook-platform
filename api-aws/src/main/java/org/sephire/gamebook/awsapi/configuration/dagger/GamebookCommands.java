package org.sephire.gamebook.awsapi.configuration.dagger;

import dagger.Module;
import dagger.Provides;
import org.sephire.gamebook.core.application.account.CreateUserAccountCommandHandler;
import org.sephire.gamebook.core.application.account.GetUserAccountCommandHandler;
import org.sephire.gamebook.core.domain.model.account.UserAccountRepository;
import org.sephire.gamebook.core.domain.shared.events.EventEmitter;

@Module(
        includes = { GamebookCoreServices.class }
)
public class GamebookCommands {

    @Provides
    public static GetUserAccountCommandHandler getUserAccountCommandHandler(UserAccountRepository userAccountRepository, EventEmitter eventEmitter) {
        return new GetUserAccountCommandHandler(userAccountRepository, eventEmitter);
    }

    @Provides
    public static CreateUserAccountCommandHandler getCreateUserAccountCommandHandler(UserAccountRepository userAccountRepository, EventEmitter eventEmitter) {
        return new CreateUserAccountCommandHandler(userAccountRepository, eventEmitter);
    }
}
