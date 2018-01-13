package org.sephire.gamebook.core.test.utils;

import io.vavr.control.Option;
import org.sephire.gamebook.core.domain.model.account.Alias;
import org.sephire.gamebook.core.domain.model.account.UserAccount;
import org.sephire.gamebook.core.domain.model.account.UserAccountRepository;
import org.sephire.gamebook.core.domain.shared.repositories.RepositoryException;

/**
 * This mimics a user account repository that has a failing datasource backend in
 * every operation.
 */
public class BackendFailingUserAccountRepository implements UserAccountRepository {
    @Override
    public UserAccount storeUserAccount(UserAccount userAccount) {
        throw new RepositoryException();
    }

    @Override
    public Option<UserAccount> findUserAccount(Alias userAlias) {
        throw new RepositoryException();
    }

    @Override
    public void deleteUserAccount(UserAccount userAccount) {
        throw new RepositoryException();
    }
}
