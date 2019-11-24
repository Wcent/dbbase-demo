package org.cent.dbbasedemo.mapper.slave;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cent.dbbasedemo.model.Order;

public interface OrderMapper {

    Order getOrder(String oid);
    int addOrder(Order order);
    int updateOrder(@Param("order") Order order, @Param("orderTemp")Order orderTemp);
}
