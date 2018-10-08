package com.mvc.multidb;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class RouteDataSource extends AbstractRoutingDataSource {
    /**
     * dbKey线程安全容器
     */
    private static ThreadLocal<String> holder = new ThreadLocal<>();

    @Override
    protected Object determineCurrentLookupKey() {
        return holder.get();
    }

    /**
     * 设置dbKey
     *
     * @param dbKey
     */
    public static void setDbKey(RouteDataSourceKeyEnum dbKey) {
        holder.set(dbKey.name());
    }
}
