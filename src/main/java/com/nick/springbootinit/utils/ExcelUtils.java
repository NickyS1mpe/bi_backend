package com.nick.springbootinit.utils;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Excel utils
@Slf4j
public class ExcelUtils {

//     https://easyexcel.opensource.alibaba.com/

    public static String excelToCSV(MultipartFile multipartFile) {
//        for test only
//        File file = null;
//        try {
//            file = ResourceUtils.getFile("classpath:web_data.xlsx");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        List<Map<Integer, String>> list = null;
        try {
            list = EasyExcel.read(multipartFile.getInputStream())
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet()
                    .headRowNumber(0)
                    .doReadSync();
        } catch (IOException e) {
            log.error("excel chart convert error", e);
            throw new RuntimeException(e);
        }
        if (CollUtil.isEmpty(list))
            return "";

//         convert to csv
        StringBuilder sb = new StringBuilder();
//         reading chart head
        LinkedHashMap<Integer, String> headMap = (LinkedHashMap<Integer, String>) list.get(0);
        List<String> headerList = headMap.values().stream().filter(ObjectUtils::isNotEmpty).collect(Collectors.toList());
        sb.append(StringUtils.join(headerList, ",")).append("\n");

//        System.out.println(StringUtils.join(headerList, ","));
//         reading data
        for (int i = 0; i < list.size(); i++) {
            LinkedHashMap<Integer, String> dataMap = (LinkedHashMap<Integer, String>) list.get(i);
            List<String> dataList = dataMap.values().stream().filter(ObjectUtils::isNotEmpty).collect(Collectors.toList());
            sb.append(StringUtils.join(dataList, ",")).append("\n");

//            System.out.println(StringUtils.join(dataList, ","));
        }

        System.out.println(list);
        return sb.toString();
    }

    public static void main(String[] args) {
        excelToCSV(null);
    }
}
