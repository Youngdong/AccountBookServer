package com.flycamel.accountbookserver.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dateLastLogin;

    @Version
    private long version;
}
