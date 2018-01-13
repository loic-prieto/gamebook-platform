package org.sephire.gamebook.core.test.utils;

import io.vavr.control.Option;
import org.sephire.gamebook.core.domain.model.account.Alias;
import org.sephire.gamebook.core.domain.model.account.UserAccount;
import org.sephire.gamebook.core.domain.model.account.UserAccountRepository;

/**
 * This mimics a user account repository that fails for unknown reasons in every operation.
 */
public class RandomFailingUserAccountRepository implements UserAccountRepository {
    @Override
    public UserAccount storeUserAccount(UserAccount userAccount) {
        throw new RuntimeException();
    }

    @Override
    public Option<UserAccount> findUserAccount(Alias userAlias) {
        throw new RuntimeException();
    }

    @Override
    public void deleteUserAccount(UserAccount userAccount) {
        throw new RuntimeException();
    }
}
