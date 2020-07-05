package com.social.football.validator;

import com.social.football.exception.BadRequestException;

public class ContractInfoValidator {

    private ContractInfoValidator() {}

    public static void validateContractStartEndYear(Integer startYear, Integer endYear) {
        if(startYear == null || endYear == null) {
            throw new BadRequestException("Start and end year should be specified.");
        }
        if(startYear >= endYear) {
            throw new BadRequestException("Contract end year should be after start year.");
        }
    }
}
