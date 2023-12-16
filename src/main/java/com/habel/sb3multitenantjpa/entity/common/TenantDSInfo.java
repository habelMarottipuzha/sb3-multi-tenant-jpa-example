package com.habel.sb3multitenantjpa.entity.common;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tenant_ds_info")
public class TenantDSInfo {

    @Id
    private String tenantId;
    private String jdbcUrl;
    private String username;
    private String password;
    private String schema;
    private Integer maxPoolSize;
    private Integer minIdle;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    @JsonManagedReference
    private Profile profile;
}
