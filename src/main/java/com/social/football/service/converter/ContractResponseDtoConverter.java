package com.social.football.service.converter;

import com.social.football.model.Contract;
import com.social.football.model.dto.ContractResponseDto;

public class ContractResponseDtoConverter {

    private ContractResponseDtoConverter() {}

    public static ContractResponseDto convert(Contract contract) {
        return ContractResponseDto.builder().id(contract.getId())
                .playerName(contract.getPlayer().getName())
                .teamName(contract.getTeam().getName())
                .startDate(contract.getStartDate()).endDate(contract.getEndDate()).build();
    }
}
