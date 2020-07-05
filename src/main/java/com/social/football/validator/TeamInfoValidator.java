package com.social.football.validator;

import com.social.football.exception.BadRequestException;
import org.apache.commons.lang.StringUtils;


import java.util.Currency;
import java.util.stream.Collectors;

public class TeamInfoValidator {

    public static final String CURRENCY_CODE_LIST = "Available currency codes: " +
            StringUtils.join(Currency.getAvailableCurrencies().stream()
                    .map(Currency::getCurrencyCode).collect(Collectors.toSet()), ",");

    private TeamInfoValidator() {}

    public static void validateForEmptyInfo(String name) {
        if(StringUtils.isBlank(name)) {
            throw new BadRequestException("Name cannot be empty.");
        }
    }

    public static void validateCurrencyCode(String currencyCode) {
        try {
            Currency.getInstance(currencyCode);
        } catch (NullPointerException e) {
            throw new BadRequestException("Currency code is required. " + CURRENCY_CODE_LIST);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Currency code is not valid. " + CURRENCY_CODE_LIST);
        }
    }
}
