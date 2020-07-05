package com.social.football.service;

import com.social.football.model.Contract;
import com.social.football.model.dto.ContractRequestDto;
import com.social.football.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ContractService {

    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private TeamService teamService;

    @Transactional
    public Contract create(ContractRequestDto contractRequestDto) {
        return contractRepository.save(new Contract(
                playerService.findById(contractRequestDto.getPlayerId()),
                teamService.findById(contractRequestDto.getTeamId()),
                contractRequestDto.getStartYear(), contractRequestDto.getEndYear()));
    }
}
