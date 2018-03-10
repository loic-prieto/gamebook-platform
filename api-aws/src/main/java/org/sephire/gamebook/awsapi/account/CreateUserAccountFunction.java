package org.sephire.gamebook.awsapi.account;

import com.amazonaws.services.lambda.runtime.Context;
import io.vavr.collection.List;
import io.vavr.control.Either;
import org.sephire.gamebook.awsapi.account.dtos.CreateUserAccountRequest;
import org.sephire.gamebook.awsapi.infrastructure.BaseHandler;
import org.sephire.gamebook.awsapi.infrastructure.CommandErrors;
import org.sephire.gamebook.core.application.account.CreateUserAccountCommandHandler;
import org.sephire.gamebook.core.application.shared.commands.CommandError;
import org.sephire.gamebook.core.domain.model.account.UserAccount;

import javax.inject.Inject;

public class CreateUserAccountFunction extends BaseHandler<CreateUserAccountRequest, UserAccount> {

    @Inject
    CreateUserAccountCommandHandler createUserAccountCommandHandler;

    public CreateUserAccountFunction(CreateUserAccountCommandHandler createUserAccountCommandHandler) {
        this.createUserAccountCommandHandler = createUserAccountCommandHandler;
    }

    public CreateUserAccountFunction() {
        this(injector.getCreateUserAccountCommand());
    }

    @Override
    protected UserAccount process(CreateUserAccountRequest request, Context context) {
        Either<List<CommandError>, UserAccount> result = createUserAccountCommandHandler.execute(request.toCommand());
        if (result.isLeft()) {
            String errors = result.getLeft()
                    .map((error) -> error.getMessage())
                    .reduce((error1, error2) -> error1 + "\n" + error2);
            context.getLogger().log("Error while processing a create user account command: " + errors);
            throw new CommandErrors(result.getLeft());
        }

        return result.get();
    }

}
