package com.yupi.springbootinit.model.dto.file;

import java.io.Serializable;

import lombok.Data;

/**
 * File upload request
 */
@Data
public class UploadFileRequest implements Serializable {

    private String biz;

    private static final long serialVersionUID = 1L;
}