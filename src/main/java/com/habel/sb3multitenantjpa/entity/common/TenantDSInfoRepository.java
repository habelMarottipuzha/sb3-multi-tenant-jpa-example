package com.habel.sb3multitenantjpa.entity.common;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantDSInfoRepository extends CrudRepository<TenantDSInfo, String> {

}
