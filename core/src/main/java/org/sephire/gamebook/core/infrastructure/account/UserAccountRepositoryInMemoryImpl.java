package org.sephire.gamebook.core.infrastructure.account;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.sephire.gamebook.core.domain.model.account.Alias;
import org.sephire.gamebook.core.domain.model.account.UserAccount;
import org.sephire.gamebook.core.domain.model.account.UserAccountRepository;

/**
 * Simple UserAccountRepository in-memory implementation for development purporses.
 */
public class UserAccountRepositoryInMemoryImpl implements UserAccountRepository {

    private Map<Alias, UserAccount> userAccounts;

    public UserAccountRepositoryInMemoryImpl() {
        userAccounts = HashMap.empty();
    }

    @Override
    public UserAccount storeUserAccount(UserAccount userAccount) {
        userAccounts = userAccounts.put(userAccount.getUser().getAlias(), userAccount);
        return userAccount;
    }

    @Override
    public Option<UserAccount> findUserAccount(Alias userAlias) {
        return userAccounts.get(userAlias);
    }

    @Override
    public void deleteUserAccount(UserAccount userAccount) {
        userAccounts = userAccounts.remove(userAccount.getUser().getAlias());
    }
}
