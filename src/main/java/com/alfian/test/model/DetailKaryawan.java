package com.alfian.test.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name="id_karyawan", referencedColumnName = "id")
    @JsonBackReference  // This annotation is to omit the back part of the reference to avoid circular reference
    private Karyawan karyawan;
}
