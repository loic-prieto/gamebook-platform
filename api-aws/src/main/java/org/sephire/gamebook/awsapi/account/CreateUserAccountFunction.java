package org.sephire.gamebook.awsapi.account;

import com.amazonaws.services.lambda.runtime.Context;
import io.vavr.collection.List;
import io.vavr.control.Either;
import org.sephire.gamebook.awsapi.account.dtos.CreateUserAccountRequest;
import org.sephire.gamebook.awsapi.configuration.dagger.DaggerApplicationComponent;
import org.sephire.gamebook.awsapi.infrastructure.*;
import org.sephire.gamebook.core.application.account.CreateUserAccountCommandHandler;
import org.sephire.gamebook.core.application.shared.commands.CommandError;
import org.sephire.gamebook.core.domain.model.account.UserAccount;

public class CreateUserAccountFunction extends ApiGatewayRequestHandler<CreateUserAccountRequest, UserAccount> {

	private CreateUserAccountCommandHandler commandHandler;

	public CreateUserAccountFunction(CreateUserAccountCommandHandler commandHandler) {
		this.commandHandler = commandHandler;
	}

	/**
	 * This is used by AWS Lambda
	 */
	public CreateUserAccountFunction() {
		//Service locator sucks
		this.commandHandler = DaggerApplicationComponent.builder().build()
				.getCreateUserAccountCommand();
	}

	@Override
    protected ApiGatewayHttpResponse<UserAccount> process(ApiGatewayHttpRequest<CreateUserAccountRequest> request, Context context) {

        ApiGatewayHttpResponse.Builder<UserAccount> responseBuilder = ApiGatewayHttpResponse.builder();


        if (request.getEntity().isDefined()) {
	        Either<List<CommandError>, UserAccount> result = commandHandler
                    .execute(request.getEntity().get().toCommand());

            if (result.isLeft()) {
                String errors = result.getLeft()
                        .map((error) -> error.getMessage())
                        .reduce((error1, error2) -> error1 + "\n" + error2);
                context.getLogger().log("Error while processing a create user account command: " + errors);
                throw new CommandErrors(result.getLeft());
            }
        } else {
            responseBuilder.setStatusCode(HttpStatusCode.BAD_REQUEST);
        }


        return responseBuilder.build();
    }
}
