package com.social.football.service;

import com.social.football.exception.NotFoundException;
import com.social.football.model.Contract;
import com.social.football.model.Player;
import com.social.football.model.Team;
import com.social.football.model.dto.PlayerRequestDto;
import com.social.football.model.dto.TeamRequestDto;
import com.social.football.repository.TeamRepository;
import com.social.football.validator.TeamInfoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerService playerService;

    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    public Team findById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Team not found."));
    }

    @Transactional
    public Team create(TeamRequestDto teamRequestDto) {
        return teamRepository.save(new Team(teamRequestDto.getName(), teamRequestDto.getCurrencyCode()));
    }

    @Transactional
    public Team update(Long id, TeamRequestDto teamRequestDto) {
        Team team = findById(id);
        team.setAttributes(teamRequestDto.getName(), teamRequestDto.getCurrencyCode());
        return teamRepository.save(team);
    }

    public List<Contract> findContractsByIdAndYear(Long id, Integer year) {
        return findById(id).getContractList().stream()
                .filter(contract -> contract.getStartDate().getYear() <= year && contract.getEndDate().minusMinutes(1).getYear() >= year)
                .collect(Collectors.toList());
    }

    public String formatPlayerPriceForTeam(Player player, Team team) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
        currencyFormatter.setCurrency(Currency.getInstance(team.getCurrencyCode()));
        return currencyFormatter.format(playerService.calculateContractPrice(player));
    }

    @Transactional
    public void delete(Long id) {
        try {
            teamRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Team not found.");
        }
    }
}
