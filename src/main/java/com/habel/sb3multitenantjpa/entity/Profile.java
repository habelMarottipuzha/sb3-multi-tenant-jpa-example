package com.habel.sb3multitenantjpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table
public class Profile {
    @Id
    private Long id;
    private String schema;
}
