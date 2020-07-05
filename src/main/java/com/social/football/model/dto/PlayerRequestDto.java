package com.social.football.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerRequestDto {

    private String name;
    private Integer age;

}
