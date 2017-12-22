package org.sephire.gamebook.core.domain.model.account;

import io.vavr.control.Option;

public interface UserAccountRepository {
    public UserAccount storeUserAccount(UserAccount userAccount);

    public Option<UserAccount> findUserAccount(User user);

    public void deleteUserAccount(UserAccount userAccount);
}
