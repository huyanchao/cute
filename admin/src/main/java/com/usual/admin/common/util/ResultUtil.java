package com.usual.admin.common.util;

import com.usual.admin.common.bean.ResultMessage;

/**
 * @author huyanchao
 */
public class ResultUtil {

    public static ResultMessage SUCCESS(Object data) {
        return new ResultMessage(1, "", data);
    }

    public static ResultMessage Error(Object data, String message) {
        return new ResultMessage(-1, message, data);
    }
}
