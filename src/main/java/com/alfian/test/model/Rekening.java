package com.alfian.test.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "rekening")
@Entity
@ToString
@EqualsAndHashCode
public class Rekening extends AbstractDate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nama", length = 45)
    private String nama;

    @Column(name = "jenis", length = 10)
    private String jenis;

    @Column(name = "rekening", length = 10)
    private String rekening;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_karyawan", referencedColumnName = "id")
    private Karyawan karyawan;
}
