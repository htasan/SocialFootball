package com.social.football.model;

import com.social.football.validator.ContractInfoValidator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Contract {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;
    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    public Contract(Player player, Team team, Integer startYear, Integer endYear) {
        ContractInfoValidator.validateContractStartEndYear(startYear, endYear);
        this.player = player;
        this.team = team;
        startDate = LocalDateTime.of(startYear, 1,1,0,0);
        endDate = LocalDateTime.of(endYear, 1,1,0,0);
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Player getPlayer() {
        return player;
    }

    public Team getTeam() {
        return team;
    }

    public Long getExperienceInMonths() {
        return ChronoUnit.MONTHS.between(
                YearMonth.from(startDate),
                YearMonth.from(endDate));
    }

    public String getYearInfo() {
        StringBuilder yearInfo = new StringBuilder().append(startDate.getYear());
        if(startDate.getYear() < endDate.minusMinutes(1).getYear()) {
            yearInfo.append("-").append(endDate.minusMinutes(1).getYear());
        }
        return yearInfo.toString();
    }
}
