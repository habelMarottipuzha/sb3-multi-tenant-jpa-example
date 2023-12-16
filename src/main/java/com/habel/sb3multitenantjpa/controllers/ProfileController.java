package com.habel.sb3multitenantjpa.controllers;

import com.habel.sb3multitenantjpa.entity.common.Profile;
import com.habel.sb3multitenantjpa.entity.common.ProfileRepository;
import com.habel.sb3multitenantjpa.entity.common.TenantDSInfo;
import com.habel.sb3multitenantjpa.entity.common.TenantDSInfoRepository;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("profile")
@RequiredArgsConstructor

public class ProfileController {

    private final ProfileRepository profileRepository;
    private final TenantDSInfoRepository tenantDSInfoRepository;

    @Parameter(name = "X-Org", description = "Organization header", in = ParameterIn.HEADER, schema = @Schema(type = "string", allowableValues = {"org1", "org2"}))
    @GetMapping
    public Iterable<Profile> getAllUsers() {
        return profileRepository.findAll();
    }

    @Parameter(name = "X-Org", description = "Organization header", in = ParameterIn.HEADER, schema = @Schema(type = "string", allowableValues = {"org1", "org2"}))
    @GetMapping("tenant")
    public Iterable<TenantDSInfo> getAllTenantsWithProfile() {
        //test for checking db relations - success
        return tenantDSInfoRepository.findAll();
    }
}
