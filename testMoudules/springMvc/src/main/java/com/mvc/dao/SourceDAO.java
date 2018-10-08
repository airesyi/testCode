package com.mvc.dao;

import com.mvc.modules.Source;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SourceDAO {

    List<Source> testSource();
}
