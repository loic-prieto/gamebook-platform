package org.sephire.gamebook.core.domain.model.account;

import org.sephire.gamebook.core.domain.shared.specifications.NotNullSpecification;
import org.sephire.gamebook.core.domain.shared.specifications.Specification;

public class NewUserAccountSpecification implements Specification<UserAccount> {

    private UserAccountRepository userAccountRepository;

    public NewUserAccountSpecification(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public boolean isSatisfiedBy(UserAccount userAccount) {
        Specification<UserAccount> userIsNotAlreadyRegistered = (ua) -> {
            return userAccountRepository.findUserAccount(ua.getUser().getAlias()).isEmpty();
        };

        return new NotNullSpecification<UserAccount>()
                .and(userIsNotAlreadyRegistered)
                .isSatisfiedBy(userAccount);
    }
}
