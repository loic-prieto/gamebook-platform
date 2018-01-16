package org.sephire.gamebook.core.domain.model.account;

import lombok.NonNull;
import lombok.Value;

@Value
public class Alias {
    @NonNull
    private String value;

    @Override
    public String toString() {
        return value;
    }
}
