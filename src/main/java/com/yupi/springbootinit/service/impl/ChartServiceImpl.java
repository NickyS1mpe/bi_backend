package com.yupi.springbootinit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.model.entity.Chart;
import com.yupi.springbootinit.service.ChartService;
import com.yupi.springbootinit.mapper.ChartMapper;
import org.springframework.stereotype.Service;

/**
* @author nicklee
* @description 针对表【charts(chart info)】的数据库操作Service实现
* @createDate 2024-06-26 17:27:22
*/
@Service
public class ChartServiceImpl extends ServiceImpl<ChartMapper, Chart>
    implements ChartService {

}




