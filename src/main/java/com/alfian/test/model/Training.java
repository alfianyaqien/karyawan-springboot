package com.alfian.test.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "training")
@Entity
@ToString
@EqualsAndHashCode
@Where(clause = "deleted_date is null")
public class Training extends AbstractDate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tema", length = 90)
    private String tema;

    @Column(name = "nama_pengajar", length = 45)
    private String namaPengajar;

    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<KaryawanTraining> karyawanTrainingS;
}
