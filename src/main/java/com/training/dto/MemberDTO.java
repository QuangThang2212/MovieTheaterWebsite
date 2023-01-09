package com.training.dto;

import com.training.entities.Account;
import lombok.Data;

@Data
public class MemberDTO {
    private String memberID;
    private double score;
    private Account accountMember;
}
