package com.habel.sb3multitenantjpa.config;

import com.habel.sb3multitenantjpa.utils.TenantContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MultitenantDataSource extends AbstractRoutingDataSource {
    private static final String DEFAULT_SCHEMA = "public";

    /**
     * {@inheritDoc}
     */
    @Override
    protected String determineCurrentLookupKey() {
        return StringUtils.defaultIfBlank(TenantContext.getCurrentTenant(), DEFAULT_SCHEMA);
    }


}