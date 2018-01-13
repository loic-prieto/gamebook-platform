package org.sephire.gamebook.api.account;

import io.vavr.collection.List;
import io.vavr.control.Either;
import io.vavr.control.Option;
import org.sephire.gamebook.api.infrastructure.CommandErrors;
import org.sephire.gamebook.core.application.account.GetUserAccountCommandHandler;
import org.sephire.gamebook.core.application.shared.commands.CommandError;
import org.sephire.gamebook.core.domain.model.account.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/account")
public class AccountController {

    private GetUserAccountCommandHandler getUserAccountCommandHandler;

    @Autowired
    public AccountController(GetUserAccountCommandHandler handler) {
        this.getUserAccountCommandHandler = handler;
    }

    @RequestMapping(method = GET)
    public ResponseEntity<UserAccount> getUserAccounts(GetUserAccountRequest request) {
        Either<List<CommandError>, Option<UserAccount>> result = getUserAccountCommandHandler.execute(request.toCommand());

        return result
                .getOrElseThrow(errors -> new CommandErrors(errors))
                .map(userAccount -> ResponseEntity.ok().body(userAccount))
                .getOrElse(() -> ResponseEntity.notFound().build());
    }
}
