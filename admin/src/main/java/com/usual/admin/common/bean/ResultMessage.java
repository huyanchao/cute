package com.usual.admin.common.bean;

import lombok.Data;
import lombok.NonNull;

@Data
public class ResultMessage {

    @NonNull
    private Integer code;

    @NonNull
    private String msg;

    @NonNull
    private Object data;


}
