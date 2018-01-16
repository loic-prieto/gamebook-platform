package org.sephire.gamebook.api.configuration.spring.shared;

import org.sephire.gamebook.core.application.account.CreateUserAccountCommandHandler;
import org.sephire.gamebook.core.application.account.GetUserAccountCommandHandler;
import org.sephire.gamebook.core.domain.model.account.UserAccountRepository;
import org.sephire.gamebook.core.domain.shared.events.EventEmitter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GamebookCommands {

    @Bean
    public GetUserAccountCommandHandler getUserAccountCommandHandler(UserAccountRepository userAccountRepository, EventEmitter eventEmitter) {
        return new GetUserAccountCommandHandler(userAccountRepository, eventEmitter);
    }

    @Bean
    public CreateUserAccountCommandHandler getCreateUserAccountCommandHandler(UserAccountRepository userAccountRepository, EventEmitter eventEmitter) {
        return new CreateUserAccountCommandHandler(userAccountRepository, eventEmitter);
    }
}
