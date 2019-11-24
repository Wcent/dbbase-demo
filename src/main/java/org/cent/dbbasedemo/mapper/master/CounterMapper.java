package org.cent.dbbasedemo.mapper.master;

import org.apache.ibatis.annotations.Mapper;
import org.cent.dbbasedemo.model.Counter;

public interface CounterMapper {
    Counter getCounter(String name);
    int count(Counter counter);
    int recount(Counter counter);
}
