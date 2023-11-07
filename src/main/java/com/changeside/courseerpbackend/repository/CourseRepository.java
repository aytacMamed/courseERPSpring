package com.changeside.courseerpbackend.repository;

import com.changeside.courseerpbackend.models.mybatis.course.Course;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CourseRepository {
    void insert(Course course);
}
