package DataView.project.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Schedules {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String startDay;
    private String endDay;
    private String title;
    private String color;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
