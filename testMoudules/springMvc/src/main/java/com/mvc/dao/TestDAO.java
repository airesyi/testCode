package com.mvc.dao;

import com.mvc.modules.Project;
import com.mvc.modules.Source;
import com.mvc.modules.Student;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestDAO {
    List<Student> getStudent();

    void testBigint();

    List<Student> read();

    int write();

    List<Project> testInclude();

    int testKeyId(Source source);
}
