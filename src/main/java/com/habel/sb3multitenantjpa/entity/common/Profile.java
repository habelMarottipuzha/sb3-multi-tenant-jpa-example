package com.habel.sb3multitenantjpa.entity.common;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table
public class Profile {
    @Id
    private String id;
    private String schema;
}
