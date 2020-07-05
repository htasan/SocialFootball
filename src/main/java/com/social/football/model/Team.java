package com.social.football.model;

import com.social.football.validator.TeamInfoValidator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Team {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String currencyCode;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "team")
    @OrderBy("startDate DESC")
    private final List<Contract> contractList = new ArrayList<>();

    public Team(String name, String currencyCode) {
        TeamInfoValidator.validateForEmptyInfo(name);
        TeamInfoValidator.validateCurrencyCode(currencyCode);
        this.name = name;
        this.currencyCode = currencyCode;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setAttributes(String name, String currencyCode) {
        TeamInfoValidator.validateForEmptyInfo(name);
        TeamInfoValidator.validateCurrencyCode(currencyCode);
        this.name = name;
        this.currencyCode = currencyCode;
    }

    public List<Contract> getContractList() {
        return contractList;
    }

    public void addContract(Contract contract) {
        contractList.add(contract);
    }
}