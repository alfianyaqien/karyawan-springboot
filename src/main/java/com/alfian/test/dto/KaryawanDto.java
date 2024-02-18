package com.alfian.test.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class KaryawanDto {
    private Long id;
    private String nama;
    private Date dob;
    private String alamat;
    private String status;
    private DetailKaryawanDto detailKaryawan;
    private Date created_date;
    private Date updated_date;
    private Date deleted_date;
}
