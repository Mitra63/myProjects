package com.sadad.ib.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class TransactionResponseDTO {

    private Long sourceAccountNumber;
    private Long destinationAccountNumber;
    private Long balance;
    private Date transactionDate;
    private String operationType;
    private String description;
}
