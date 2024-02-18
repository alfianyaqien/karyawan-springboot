package com.alfian.test.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class DetailKaryawanDto {
    private Long id;
    private String nik;
    private String npwp;
    private Date created_date;
    private Date updated_date;
    private Date deleted_date;
}
