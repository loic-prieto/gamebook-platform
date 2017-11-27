package org.sephire.gamebook.core.domain.model.account;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class Price {
    private BigDecimal value;
    private String curreny;
}
