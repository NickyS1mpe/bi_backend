package com.yupi.springbootinit.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * chart info
 *
 * @TableName charts
 */
@TableName(value = "charts")
@Data
public class Chart implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
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

    /**
     * generated chart
     */
    private String genChart;

    /**
     * generated result
     */
    private String genResult;

    /**
     * create time
     */
    private Date createTime;

    private Long userId;

    /**
     * update time
     */
    private Date updateTime;

    /**
     * if deleted
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}