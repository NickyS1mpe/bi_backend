package com.nick.springbootinit.model.dto.chart;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 *
 */
@Data
public class ChartAddRequest implements Serializable {
    private String name;


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