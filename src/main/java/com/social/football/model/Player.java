package com.social.football.model;

import com.social.football.validator.PlayerInfoValidator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Player {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Integer age;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "player")
    @OrderBy("startDate DESC")
    private final List<Contract> contractList = new ArrayList<>();

    public Player(String name, Integer age) {
        PlayerInfoValidator.validateForEmptyInfo(name, age);
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getExperienceInMonths() {
        return contractList.stream().mapToLong(Contract::getExperienceInMonths).sum();
    }

    public Integer getAge() {
        return age;
    }

    public List<Contract> getContractList() {
        return contractList;
    }

    public void setAttributes(String name, Integer age) {
        PlayerInfoValidator.validateForEmptyInfo(name, age);
        this.name = name;
        this.age = age;
    }

    public void addContract(Contract contract) {
        contractList.add(contract);
    }
}