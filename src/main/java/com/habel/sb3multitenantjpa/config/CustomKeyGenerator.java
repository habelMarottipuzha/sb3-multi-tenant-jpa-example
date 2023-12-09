package com.habel.sb3multitenantjpa.config;

import com.habel.sb3multitenantjpa.utils.TenantContext;
import org.springframework.cache.interceptor.SimpleKeyGenerator;

import java.lang.reflect.Method;

/**
 * class to ensure that the cache kes won't have conflict with multiple tenants. helpful if using hibernate L2 cache
 * Note. Just added as a reference, not used anywhere
 */
public class CustomKeyGenerator extends SimpleKeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {

        return TenantContext.getCurrentTenant() + "_" + generateKey(params);
    }
}