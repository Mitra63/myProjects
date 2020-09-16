package com.sadad.ib.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Data
public class TransferMoneyRequestDTO {
    @NotNull
    private Long sourceAccountNumber;
    @NotNull
    private Long destinationAccountNumber;
    @NotNull
    private Long amount;

    private String description;

}
