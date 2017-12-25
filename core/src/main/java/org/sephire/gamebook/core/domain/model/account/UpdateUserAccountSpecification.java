package org.sephire.gamebook.core.domain.model.account;

import org.sephire.gamebook.core.domain.shared.specifications.NotNullSpecification;
import org.sephire.gamebook.core.domain.shared.specifications.Specification;

import static org.sephire.gamebook.core.domain.shared.specifications.Utils.fieldNotNull;

public class UpdateUserAccountSpecification implements Specification<UserAccount> {
    private UserAccountRepository userAccountRepository;

    public UpdateUserAccountSpecification(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public boolean isSatisfiedBy(UserAccount userAccount) {

        Specification<UserAccount> userExists = (ua) -> {
            return userAccountRepository
                    .findUserAccount(ua.getUser().getAlias().getValue())
                    .isDefined();
        };

        return new NotNullSpecification<UserAccount>()
                .and(fieldNotNull((account) -> account.getUser()))
                .and(fieldNotNull((account) -> account.getUser().getAlias()))
                .and(fieldNotNull((account) -> account.getUser().getEmail()))
                .and(fieldNotNull((account) -> account.getAccountType()))
                .and(userExists)
                .isSatisfiedBy(userAccount);
    }
}
