package org.sephire.gamebook.core.domain.shared.specifications;

/**
 * A specification that tests whether an object is null
 *
 * @param <T>
 */
public class NotNullSpecification<T> implements Specification<T> {
    @Override
    public boolean isSatisfiedBy(T t) {
        return t != null;
    }
}
