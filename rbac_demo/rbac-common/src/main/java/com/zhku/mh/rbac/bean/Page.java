package com.zhku.mh.rbac.bean;

import java.util.List;

/**
 * ClassName：
 * Time：2019/12/5 15:34
 * Description：
 * Author： mh
 */
public class Page<T> {
    private List<T> datas;
    private Integer pageno;
    private Integer totalno;
    private Integer totalsize;

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public Integer getPageno() {
        return pageno;
    }

    public void setPageno(Integer pageno) {
        this.pageno = pageno;
    }

    public Integer getTotalno() {
        return totalno;
    }

    public void setTotalno(Integer totalno) {
        this.totalno = totalno;
    }

    public Integer getTotalsize() {
        return totalsize;
    }

    public void setTotalsize(Integer totalsize) {
        this.totalsize = totalsize;
    }
}
