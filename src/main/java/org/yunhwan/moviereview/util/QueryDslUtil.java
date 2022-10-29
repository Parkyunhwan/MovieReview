package org.yunhwan.moviereview.util;


import com.querydsl.core.types.*;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.dsl.PathBuilder;

public class QueryDslUtil {
    public static OrderSpecifier<?> getOrderSpecifier(Order order, EntityPath<?> entity, String field) {
        PathBuilder<?> orderByExpression = new PathBuilder<>(entity.getType(), entity.getMetadata());
        return new OrderSpecifier(order, orderByExpression.get(field));
    }
}
