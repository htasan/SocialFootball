package com.social.football.controller;

import com.social.football.model.dto.ContractRequestDto;
import com.social.football.model.dto.ContractResponseDto;
import com.social.football.service.ContractService;
import com.social.football.service.converter.ContractResponseDtoConverter;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contracts")
public class ContractController {

    @ApiOperation(value = "Create contract", response = ContractResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Contract successfully created"),
            @ApiResponse(code = 400, message = "Contract could not be created"),
            @ApiResponse(code = 404, message = "Player or team could not be found")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ContractResponseDto create(@RequestBody ContractRequestDto contractRequestDto) {
        return ContractResponseDtoConverter.convert(contractService.create(contractRequestDto));
    }

    @Autowired
    private ContractService contractService;
}
