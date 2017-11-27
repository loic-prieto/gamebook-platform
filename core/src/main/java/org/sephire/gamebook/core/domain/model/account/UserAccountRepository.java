package org.sephire.gamebook.core.domain.model.account;

public interface UserAccountRepository {
    public UserAccount storeUserAccount(UserAccount userAccount);

    public UserAccount findUserAccount(User user);

    public void deleteUserAccount(UserAccount userAccount);
}
