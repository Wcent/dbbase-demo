package org.cent.dbbasedemo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.cent.dbbasedemo.model.Counter;

@Mapper
public interface CounterMapper {
    Counter getCounter(String name);
    int count(Counter counter);
    int recount(Counter counter);
}
