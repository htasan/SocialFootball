package com.social.football.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PlayerResponseDto {

    private Long id;
    private String name;
    private Integer age;
}
