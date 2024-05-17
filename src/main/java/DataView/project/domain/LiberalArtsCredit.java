package DataView.project.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "liberal_arts_credit")
public class LiberalArtsCredit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "global_communication",nullable = false)
    private int globalCommunication;

    @Column(name = "maritime_culture",nullable = false)
    private int maritimeCulture;

    @Column(name = "maritime_sports_arts",nullable = false)
    private int maritimeSportsArts;

    @Column(name = "history_culture",nullable = false)
    private int historyCulture;

    @Column(name = "society_economy",nullable = false)
    private int societyEconomy;

    @Column(name = "personal_development",nullable = false)
    private int personalDevelopment;

    @Column(name = "total",nullable = false)
    private int total;
}
