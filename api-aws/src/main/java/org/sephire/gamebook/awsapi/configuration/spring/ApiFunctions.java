package org.sephire.gamebook.awsapi.configuration.spring;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.sephire.gamebook.awsapi.account.CreateUserAccountFunction;
import org.sephire.gamebook.awsapi.account.GetUserAccountFunction;
import org.sephire.gamebook.core.application.account.CreateUserAccountCommand;
import org.sephire.gamebook.core.application.account.CreateUserAccountCommandHandler;
import org.sephire.gamebook.core.application.account.GetUserAccountCommand;
import org.sephire.gamebook.core.application.account.GetUserAccountCommandHandler;
import org.sephire.gamebook.core.domain.model.account.UserAccount;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(GamebookCommands.class)
public class ApiFunctions {

    @Bean
    public RequestHandler<CreateUserAccountCommand, UserAccount> createUserAccountFunction(CreateUserAccountCommandHandler handler) {
        return new CreateUserAccountFunction(handler);
    }

    @Bean
    public RequestHandler<GetUserAccountCommand, UserAccount> getUserAccountFunction(GetUserAccountCommandHandler handler) {
        return new GetUserAccountFunction(handler);
    }
}
