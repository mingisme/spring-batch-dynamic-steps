package com.example.springbatchdynamicsteps.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
public class ResourceLock {

    @Id
    private String resourceId;
    private String correlatedId;
    private String correlatedType;
    private Date createdTime;

}
