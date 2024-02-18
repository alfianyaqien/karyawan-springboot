package com.alfian.test.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "detail_karyawan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "karyawan")
@EqualsAndHashCode(exclude = "karyawan")
public class DetailKaryawan extends AbstractDate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nik", length = 45)
    private String nik;

    @Column(name = "npwp", length = 10)
    private String npwp;

    @OneToOne
    @JoinColumn(name="id_karyawan", referencedColumnName = "id")
    private Karyawan karyawan;
}
