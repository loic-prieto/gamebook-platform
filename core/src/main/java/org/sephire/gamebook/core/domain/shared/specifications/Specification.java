package org.sephire.gamebook.core.domain.shared.specifications;

/**
 * Specification interface.
 * <p/>
 * Use {@link AbstractSpecification} as base for creating specifications, and
 * only the method {@link #isSatisfiedBy(Object)} must be implemented.
 */
public interface Specification<T> {

    /**
     * Check if {@code t} is satisfied by the specification.
     *
     * @param t Object to test.
     * @return {@code true} if {@code t} satisfies the specification.
     */
    boolean isSatisfiedBy(T t);

    /**
     * Create a new specification that is the AND operation of {@code this} specification and another specification.
     *
     * @param specification Specification to AND.
     * @return A new specification.
     */
    default Specification<T> and(Specification<T> specification) {
        return new AndSpecification<T>(this, specification);
    }

    /**
     * Create a new specification that is the OR operation of {@code this} specification and another specification.
     *
     * @param specification Specification to OR.
     * @return A new specification.
     */
    default Specification<T> or(Specification<T> specification) {
        return new OrSpecification<T>(this, specification);
    }

    /**
     * Create a new specification that is the NOT operation of {@code this} specification.
     *
     * @return A new specification.
     */
    default Specification<T> not() {
        return new NotSpecification<T>(this);
    }
}
