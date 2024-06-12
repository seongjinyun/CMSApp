package com.sds.cmsapp.settings.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sds.cmsapp.domain.Authority;
import com.sds.cmsapp.domain.Role;
import com.sds.cmsapp.domain.RoleAuthority;
import com.sds.cmsapp.model.authority.AuthorityService;
import com.sds.cmsapp.model.relationship.RoleAuthorityService;
import com.sds.cmsapp.model.role.RoleService;

@RestController
public class RestRoleAuthorityController {

    private static final Logger logger = LoggerFactory.getLogger(RestRoleAuthorityController.class);

    @Autowired
    private RoleAuthorityService roleAuthorityService;
    
    @Autowired
    private RoleService roleService;
    
    @Autowired
    private AuthorityService authorityService;
    
    @PostMapping("/settings/roleauth/add")
    public String saveRoles(@RequestBody Map<String, Object> payload) {
        String roleName = (String) payload.get("roleName");
        List<String> authorityCodes = (List<String>) payload.get("authorities");

        // 역할명과 선택된 체크박스 값들을 출력합니다.
        System.out.println("Role Name: " + roleName);
        System.out.println("authorities: " + authorityCodes);

        // 새 역할 생성
        Role role = new Role();
        role.setRoleName(roleName);
        role.setRoleCode(roleService.getMaxRoleCode()+100);
        roleService.insertRole(role);
        
        // 역할-권한 매핑
        for(String authorityCode : authorityCodes) {
        	RoleAuthority roleAuthority = new RoleAuthority();
            roleAuthority.setRole(role);
        	roleAuthority.setAuthority(authorityService.selectByAuthorityCode(Integer.parseInt(authorityCode)));
        	roleAuthorityService.insertAuthorityIntoRole(roleAuthority);
        }

        return "/settings/role";
    } 

    @PostMapping("/settings/roleauth/update")
    public ResponseEntity<?> updateRoleAuth(@RequestBody Map<String, Object> params) {
        try {
            logger.info("Received parameters: {}", params);
            List<String> roleCodes = (List<String>) params.get("roleCodes");
            Map<String, List<String>> authorities = (Map<String, List<String>>) params.get("authorities");

            for (String roleCodeStr : roleCodes) {
                int roleCode = Integer.parseInt(roleCodeStr);
                List<String> authorityCodes = authorities.get(roleCodeStr);
                if (authorityCodes != null) {
                    logger.info("Updating role {} with authorities: {}", roleCode, authorityCodes);

                    // 기존 권한 삭제
                    roleAuthorityService.deleteAuthoritiesByRoleCode(roleCode);
                    logger.info("Deleted existing authorities for role {}", roleCode);

                    // 새로운 권한 추가
                    for (String authorityCodeStr : authorityCodes) {
                        int authorityCode = Integer.parseInt(authorityCodeStr);
                        RoleAuthority roleAuthority = new RoleAuthority();
                        Role role = new Role();
                        role.setRoleCode(roleCode);
                        Authority authority = new Authority();
                        authority.setAuthorityCode(authorityCode);

                        roleAuthority.setRole(role);
                        roleAuthority.setAuthority(authority);

                        roleAuthorityService.insertAuthorityIntoRole(roleAuthority);
                        logger.info("Inserted authority {} for role {}", authorityCode, roleCode);
                    }
                }
            }

            return ResponseEntity.ok(Collections.singletonMap("success", true));
        } catch (Exception e) {
            logger.error("Error updating role authorities", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("success", false));
        }
    }
}
