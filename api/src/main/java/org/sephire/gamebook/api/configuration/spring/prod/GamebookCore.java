package org.sephire.gamebook.api.configuration.spring.prod;

import org.sephire.gamebook.core.domain.model.account.UserAccountRepository;
import org.sephire.gamebook.core.domain.model.book.GamebookRepository;
import org.sephire.gamebook.core.domain.shared.events.EventEmitter;
import org.sephire.gamebook.core.infrastructure.account.UserAccountRepositoryInMemoryImpl;
import org.sephire.gamebook.core.infrastructure.book.GamebookRepositoryInMemoryImpl;
import org.sephire.gamebook.core.infrastructure.events.EventEmitterInMemoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
public class GamebookCore {

    @Bean
    public EventEmitter eventEmitter() {
        return new EventEmitterInMemoryImpl();
    }

    @Bean
    public UserAccountRepository userAccountRepository() {
        return new UserAccountRepositoryInMemoryImpl();
    }

    @Bean
    public GamebookRepository gamebookRepository() {
        return new GamebookRepositoryInMemoryImpl();
    }
}
