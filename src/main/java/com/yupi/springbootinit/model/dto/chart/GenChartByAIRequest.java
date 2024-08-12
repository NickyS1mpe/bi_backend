package com.yupi.springbootinit.model.dto.chart;

import lombok.Data;

import java.io.Serializable;

/**
 * 文件上传请求
 *
 */
@Data
public class GenChartByAIRequest implements Serializable {

    private String name;

    private String goal;

    private String chartType;


    private static final long serialVersionUID = 1L;
}