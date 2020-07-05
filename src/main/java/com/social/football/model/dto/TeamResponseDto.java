package com.social.football.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TeamResponseDto {

    private Long id;
    private String name;
    private String currencyCode;
}
