package com.alfian.test.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"created_at", "updated_at", "deleted_at"},
        allowGetters = true
)
public abstract class AbstractDate implements Serializable {
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "Asia/Jakarta")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = true, updatable = false)

    @CreationTimestamp
    private Date created_date; @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "Asia/Jakarta")

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date", nullable = true)

    @UpdateTimestamp
    private Date updated_date;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "Asia/Jakarta")

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_date", nullable = true) private Date deleted_date;
    public void setDeleted_date(Date deleted_date) {
        this.deleted_date = deleted_date;
    }
}