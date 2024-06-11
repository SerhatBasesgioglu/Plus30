package com.aydakar.plus30backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OldTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String gameName;

    String tagLine;

    @ManyToOne()
    @JoinColumn(name = "puuid")
    private Summoner summoner;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public OldTag(String gameName, String tagLine, Summoner summoner) {
        this.gameName = gameName;
        this.tagLine = tagLine;
        this.summoner = summoner;
    }
}
