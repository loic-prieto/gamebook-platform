package org.sephire.gamebook.core.domain.shared.specifications;

import io.vavr.Function1;

/**
 * Utility functions related to Specifications
 */
public class Utils {


    /**
     * Given a function to return the value of a subfield of that object,
     * tests whether the subfield of the tested object is not null.
     *
     * @param subfieldSelector the function that returns the value of a field of that object
     * @param <T>              the type of the object
     * @param <R>              The type of the subfield
     * @return A specification object that performs the test
     */
    public static <T, R> Specification<T> fieldNotNull(Function1<T, R> subfieldSelector) {
        return (obj) -> {
            return subfieldSelector.apply(obj) != null;
        };
    }


}
