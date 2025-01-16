package com.nick.springbootinit.model.dto.chart;

import com.nick.springbootinit.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询请求
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ChartQueryRequest extends PageRequest implements Serializable {

    private String name;


    /**
     * id
     */
    private Long id;

    /**
     * analysis goal
     */
    private String goal;


    /**
     * chart type
     */
    private String chartType;


    private Long userId;

    private static final long serialVersionUID = 1L;
}