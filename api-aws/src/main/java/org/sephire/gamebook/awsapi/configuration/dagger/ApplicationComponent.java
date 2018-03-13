package org.sephire.gamebook.awsapi.configuration.dagger;

import dagger.Component;
import org.sephire.gamebook.awsapi.infrastructure.ApiGatewayRequestHandler;
import org.sephire.gamebook.core.application.account.CreateUserAccountCommandHandler;
import org.sephire.gamebook.core.application.account.GetUserAccountCommandHandler;

@Component(
        modules = { ApplicationConfiguration.class }
)
public interface ApplicationComponent {
    void inject(ApiGatewayRequestHandler baseHandler);
    CreateUserAccountCommandHandler getCreateUserAccountCommand();
    GetUserAccountCommandHandler getGetUserAccountCommand();
}
