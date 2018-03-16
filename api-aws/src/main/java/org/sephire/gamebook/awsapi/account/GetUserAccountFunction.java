package org.sephire.gamebook.awsapi.account;

import com.amazonaws.services.lambda.runtime.Context;
import org.sephire.gamebook.awsapi.configuration.dagger.DaggerApplicationComponent;
import org.sephire.gamebook.awsapi.infrastructure.ApiGatewayHttpRequest;
import org.sephire.gamebook.awsapi.infrastructure.ApiGatewayHttpResponse;
import org.sephire.gamebook.awsapi.infrastructure.ApiGatewayRequestHandler;
import org.sephire.gamebook.core.application.account.GetUserAccountCommand;
import org.sephire.gamebook.core.application.account.GetUserAccountCommandHandler;
import org.sephire.gamebook.core.domain.model.account.UserAccount;

public class GetUserAccountFunction extends ApiGatewayRequestHandler<GetUserAccountCommand, UserAccount> {

	private GetUserAccountCommandHandler commandHandler;

	public GetUserAccountFunction(GetUserAccountCommandHandler commandHandler) {
		this.commandHandler = commandHandler;
	}

	public GetUserAccountFunction() {
		this(DaggerApplicationComponent.builder().build()
				.getGetUserAccountCommand());
	}

	@Override
    public ApiGatewayHttpResponse<UserAccount> process(ApiGatewayHttpRequest<GetUserAccountCommand> command, Context context) {
        return null;
    }
}
