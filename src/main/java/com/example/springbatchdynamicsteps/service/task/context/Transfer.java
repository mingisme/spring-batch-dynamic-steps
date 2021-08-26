package com.example.springbatchdynamicsteps.service.task.context;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Transfer {
    private String from;
    private String to;
    private BigDecimal amount;
}
