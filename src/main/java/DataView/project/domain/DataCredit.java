package DataView.project.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "data_credit")
public class DataCredit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "major_basic", nullable = false)
    private int majorBasic;

    @Column(name = "major_elective", nullable = false)
    private int majorElective;

    @Column(name = "major_compulsory", nullable = false)
    private int majorCompulsory;

    @Column(name = "general_elective",nullable = false)
    private int generalElective;

    @Column(name = "liberal_arts_compulsory",nullable = false)
    private int liberalArtsCompulsory;

    @Column(name = "liberal_arts_elective",nullable = false)
    private int liberalArtsElective;

    @Column(name = "total",nullable = false)
    private int total;
}
