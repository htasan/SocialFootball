package com.social.football.service.converter;

import com.social.football.model.Player;
import com.social.football.model.dto.PlayerResponseDto;

public class PlayerResponseDtoConverter {

    private PlayerResponseDtoConverter() {};

    public static PlayerResponseDto convert(Player player) {
        return PlayerResponseDto.builder().id(player.getId()).name(player.getName()).age(player.getAge()).build();
    }
}
