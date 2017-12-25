package org.sephire.gamebook.core.test.utils;

import io.vavr.control.Option;
import org.sephire.gamebook.core.domain.model.account.Alias;
import org.sephire.gamebook.core.domain.model.account.UserAccount;
import org.sephire.gamebook.core.domain.model.account.UserAccountRepository;

/**
 * This is a user account repository that mimicks an empty user account
 * repository that will store nothing and do nothing.
 */
public class NoopUserAccountRepository implements UserAccountRepository {
    @Override
    public UserAccount storeUserAccount(UserAccount userAccount) {
        return userAccount;
    }

    @Override
    public Option<UserAccount> findUserAccount(Alias userAlias) {
        return Option.none();
    }

    @Override
    public void deleteUserAccount(UserAccount userAccount) {
    }
}
