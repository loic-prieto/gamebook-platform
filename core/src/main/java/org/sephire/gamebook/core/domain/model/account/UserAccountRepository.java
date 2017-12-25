package org.sephire.gamebook.core.domain.model.account;

import io.vavr.control.Option;

public interface UserAccountRepository {
    public UserAccount storeUserAccount(UserAccount userAccount);

    public Option<UserAccount> findUserAccount(Alias userAlias);

    public void deleteUserAccount(UserAccount userAccount);
}
