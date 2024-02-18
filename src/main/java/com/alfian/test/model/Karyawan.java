package com.alfian.test.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "karyawan")
@ToString
@EqualsAndHashCode
public class Karyawan extends AbstractDate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nama", nullable = false, length = 45)
    private String nama;

    @Column(name = "dob")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyMMdd")
    private Date dob;

    @Column(name = "alamat", columnDefinition="TEXT")
    private String alamat;

    @Column(name = "status", length = 15)
    private String status;

    @OneToOne(mappedBy = "karyawan", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private DetailKaryawan detailKaryawan;

    @OneToMany(mappedBy = "karyawan")
    private List<KaryawanTraining> karyawanTrainings;

    @OneToMany(mappedBy = "karyawan", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Rekening> rekenings;

}