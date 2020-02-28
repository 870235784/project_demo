package com.tca.projectdemo.controller.req;

import lombok.Data;
import lombok.ToString;

/**
 * @author zhoua
 * @Date 2020/2/27
 */
@Data
@ToString
public class UploadPicReq {

    /**
     * 图片 base64
     */
    private String picBase64;
}
