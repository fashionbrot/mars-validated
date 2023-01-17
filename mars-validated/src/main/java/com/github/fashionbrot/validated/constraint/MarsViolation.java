package com.github.fashionbrot.validated.constraint;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MarsViolation {
    /**
     * 验证字段
     */
    private String fieldName;
    /**
     * 错误信息
     */
    private String msg;
    /**
     * 注解名
     */
    private String annotationName;
    /**
     * value
     */
    private Object value;
    /**
     * Array or List
     * value index
     */
    private Integer valueIndex;
}
