package com.sadad.ib.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountResponseDTO {
    private Long accountNumber;
    private Long balance;
    private String accountType;
    private String username;
}
