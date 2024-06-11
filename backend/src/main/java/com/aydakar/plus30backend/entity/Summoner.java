package com.aydakar.plus30backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Summoner {
    @Id
    private String puuid;

    @Column(unique = true)
    private int summonerId;

    private String gameName;

    private String tagLine;

    private int summonerLevel;

    private Boolean isBlacklisted;

    @OneToMany(mappedBy = "summoner")
    private List<OldTag> oldTags;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
