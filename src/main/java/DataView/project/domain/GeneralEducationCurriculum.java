package DataView.project.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "general_education_curriculum")
public class GeneralEducationCurriculum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int year;

    @Column(name = "course_code")
    private String courseCode;
}
