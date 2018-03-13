package org.sephire.gamebook.awsapi.account;

import com.amazonaws.services.lambda.runtime.Context;
import io.vavr.collection.List;
import io.vavr.control.Either;
import io.vavr.control.Option;
import org.sephire.gamebook.awsapi.infrastructure.ApiGatewayHttpRequest;
import org.sephire.gamebook.awsapi.infrastructure.ApiGatewayHttpResponse;
import org.sephire.gamebook.awsapi.infrastructure.ApiGatewayRequestHandler;
import org.sephire.gamebook.awsapi.infrastructure.CommandErrors;
import org.sephire.gamebook.core.application.account.GetUserAccountCommand;
import org.sephire.gamebook.core.application.account.GetUserAccountCommandHandler;
import org.sephire.gamebook.core.application.account.UserNotFoundError;
import org.sephire.gamebook.core.application.shared.commands.CommandError;
import org.sephire.gamebook.core.domain.model.account.UserAccount;

public class GetUserAccountFunction extends ApiGatewayRequestHandler<GetUserAccountCommand, UserAccount, GetUserAccountCommandHandler> {

    public GetUserAccountFunction(GetUserAccountCommandHandler getUserAccountCommandHandler) {
        this.getUserAccountCommandHandler = getUserAccountCommandHandler;
    }

    public GetUserAccountFunction() {
        this(injector.getGetUserAccountCommand());
    }

    @Override
    public ApiGatewayHttpResponse<UserAccount> process(ApiGatewayHttpRequest<GetUserAccountCommand> command, Context context) {
        get
        Either<List<CommandError>, Option<UserAccount>> result = getUserAccountCommandHandler.execute(command);
        if (result.isLeft()) {
            String errors = result.getLeft()
                    .map((error) -> error.getMessage())
                    .reduce((error1, error2) -> error1 + "\n" + error2);
            context.getLogger().log("Error while getting a user account command: " + errors);
            throw new CommandErrors(result.getLeft());
        }

        Option<UserAccount> userAccount = result.get();
        return userAccount.getOrElseThrow(() -> new UserNotFoundError(command.getUserAlias()));
    }
}
