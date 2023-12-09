package com.habel.sb3multitenantjpa.entity.tenant;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "`user`")
public class User {
    @Id
    private Long id;
    private String name;
}
