package com.changeside.courseerpbackend.repository;

import com.changeside.courseerpbackend.models.mybatis.role.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface RoleRepository {
    Optional<Role> findByName(@Param("name") String name);
}
