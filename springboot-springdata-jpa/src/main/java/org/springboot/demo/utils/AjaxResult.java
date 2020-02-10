package org.springboot.demo.utils;

import java.util.Map;

/**
 *
 *
 * @author wangwenjie
 * @date 2020-01-31
 */
public class AjaxResult {
    private long status;
    private String statusText;
    private Object data;
    private Map<String, Object> datas;

    public AjaxResult() {
    }

    public long getStatus() {
        return this.status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public String getStatusText() {
        return this.statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Map<String, Object> getDatas() {
        return this.datas;
    }

    public void setDatas(Map<String, Object> datas) {
        this.datas = datas;
    }
}
