package DataView.project.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "year")
    private int year;

    @Column(name = "semester")
    private String semester;

    @Column(name = "curriculum_type")
    private String curriculumType;

    @OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE)
    private List<Subject> subjects = new ArrayList<>();
}
