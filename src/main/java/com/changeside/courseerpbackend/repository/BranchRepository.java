package com.changeside.courseerpbackend.repository;

import com.changeside.courseerpbackend.models.mybatis.branch.Branch;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BranchRepository {
    void insert(Branch branch);
}
