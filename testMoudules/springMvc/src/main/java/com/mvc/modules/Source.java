package com.mvc.modules;

import java.util.List;

public class Source {
    private String sourceNum;
    private String sourceId;
    private String sourceName;
    private String parentId;

    private List<Source> sourceList;

    public String getSourceNum() {
        return sourceNum;
    }

    public void setSourceNum(String sourceNum) {
        this.sourceNum = sourceNum;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<Source> getSourceList() {
        return sourceList;
    }

    public void setSourceList(List<Source> sourceList) {
        this.sourceList = sourceList;
    }
}
