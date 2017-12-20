package com.flycamel.accountbookserver.api.dto;

import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Value
@Builder
public class UserInfo {

    private Long id;
    private String name;
    private Date dateLastLogin;
}
