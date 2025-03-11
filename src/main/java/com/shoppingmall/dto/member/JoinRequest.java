package com.shoppingmall.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JoinRequest {
    private String name;
    private String email;
    private String password;
}
