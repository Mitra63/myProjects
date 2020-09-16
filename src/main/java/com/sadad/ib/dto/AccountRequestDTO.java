package com.sadad.ib.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class AccountRequestDTO {

    @NotNull
    @Size(min=1, max=1)
    private String accountType;
    @NotNull
    private Long balance;
    @NotNull
    @Pattern(regexp = "\\d{10}")
    private String nationalCode;
}
