package org.sephire.gamebook.core.domain.model.account;

import lombok.Value;

@Value
public class User {
    private String email;
    private String name;
    private String alias;

}
