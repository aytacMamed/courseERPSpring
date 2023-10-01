package com.changeside.courseerpbackend.services.role;

import com.changeside.courseerpbackend.exception.BaseException;
import com.changeside.courseerpbackend.models.mybatis.role.Role;
import com.changeside.courseerpbackend.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService{
    private final static String OWNER="OWNER";
    private final RoleRepository roleRepository;
    @Override
    public Role getDefaultRole() {
        return roleRepository.findByName(OWNER).orElseThrow(
                BaseException::unexpected
        );
    }
}
