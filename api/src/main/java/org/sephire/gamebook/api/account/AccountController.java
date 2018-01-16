package org.sephire.gamebook.api.account;

import io.vavr.collection.List;
import io.vavr.control.Either;
import io.vavr.control.Option;
import org.sephire.gamebook.api.infrastructure.CommandErrors;
import org.sephire.gamebook.core.application.account.CreateUserAccountCommandHandler;
import org.sephire.gamebook.core.application.account.GetUserAccountCommand;
import org.sephire.gamebook.core.application.account.GetUserAccountCommandHandler;
import org.sephire.gamebook.core.application.shared.commands.CommandError;
import org.sephire.gamebook.core.domain.model.account.Alias;
import org.sephire.gamebook.core.domain.model.account.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/account")
public class AccountController {

    private GetUserAccountCommandHandler getUserAccountCommandHandler;
    private CreateUserAccountCommandHandler createUserAccountCommandHandler;

    @Autowired
    public AccountController(GetUserAccountCommandHandler getUserAccountCommandHandler,
                             CreateUserAccountCommandHandler createUserAccountCommandHandler) {
        this.getUserAccountCommandHandler = getUserAccountCommandHandler;
        this.createUserAccountCommandHandler = createUserAccountCommandHandler;
    }

    @RequestMapping(method = GET, path = "/{userAlias}")
    public ResponseEntity<UserAccount> getUserAccount(@PathVariable String userAlias) {
        GetUserAccountCommand command = new GetUserAccountCommand(new Alias(userAlias));
        Either<List<CommandError>, Option<UserAccount>> result = getUserAccountCommandHandler.execute(command);

        return result
                .getOrElseThrow(errors -> new CommandErrors(errors))
                .map(userAccount -> ResponseEntity.ok().body(userAccount))
                .getOrElse(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(method = POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUserAccount(@RequestBody CreateUserAccountRequest request) {
        Either<List<CommandError>, UserAccount> result = createUserAccountCommandHandler.execute(request.toCommand());

        UserAccount account = result.getOrElseThrow(errors -> new CommandErrors(errors));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{userAlias}")
                .buildAndExpand(account.getUser().getAlias()).toUri();

        return ResponseEntity
                .created(location)
                .build();
    }
}
