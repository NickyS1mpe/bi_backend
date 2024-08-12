package com.yupi.springbootinit.model.dto.chart;

import lombok.Data;

import java.io.Serializable;

/**
 * 更新请求
 *
 */
@Data
public class ChartUpdateRequest implements Serializable {

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
     * chart data
     */
    private String chartData;

    /**
     * chart type
     */
    private String chartType;


    private static final long serialVersionUID = 1L;
}