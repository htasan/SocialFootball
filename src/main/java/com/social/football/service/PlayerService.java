package com.social.football.service;

import com.social.football.exception.NotFoundException;
import com.social.football.model.Player;
import com.social.football.model.dto.PlayerRequestDto;
import com.social.football.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PlayerService {

    static final double MONTHLY_FEE = 100000;

    @Autowired
    private PlayerRepository playerRepository;

    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    public Player findById(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Player not found."));
    }

    @Transactional
    public Player create(PlayerRequestDto createPlayerRequestDto) {
        return playerRepository.save(new Player(createPlayerRequestDto.getName(), createPlayerRequestDto.getAge()));
    }

    @Transactional
    public Player update(Long id, PlayerRequestDto playerRequestDto) {
        Player player = findById(id);
        player.setAttributes(playerRequestDto.getName(), playerRequestDto.getAge());
        return playerRepository.save(player);
    }

    @Transactional
    public void delete(Long id) {
        try {
            playerRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Player not found.");
        }
    }

    public double calculateContractPrice(Player player) {
        return (MONTHLY_FEE * player.getExperienceInMonths() / player.getAge()) * 1.1;
    }
}
