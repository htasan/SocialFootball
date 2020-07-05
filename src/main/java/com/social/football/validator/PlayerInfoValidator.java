package com.social.football.validator;

import com.social.football.exception.BadRequestException;
import org.apache.commons.lang.StringUtils;

public class PlayerInfoValidator {

    private PlayerInfoValidator() {}

    public static void validateForEmptyInfo(String name, Integer age) {
        if(StringUtils.isBlank(name)) {
            throw new BadRequestException("Name cannot be empty.");
        }
        if(age == null) {
            throw new BadRequestException("Age cannot be empty.");
        }
    }
}
