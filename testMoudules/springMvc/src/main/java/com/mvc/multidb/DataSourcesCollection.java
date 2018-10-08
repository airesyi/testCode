package com.mvc.multidb;

import javax.sql.DataSource;
import java.util.List;

public class DataSourcesCollection {
    private List<DataSource> sourceList;

    public List<DataSource> getSourceList() {
        return sourceList;
    }

    public void setSourceList(List<DataSource> sourceList) {
        this.sourceList = sourceList;
    }
}
