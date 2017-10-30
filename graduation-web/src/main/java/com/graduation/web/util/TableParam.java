package com.graduation.web.util;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * 表格查询对象
 */
public class TableParam implements Serializable {

    private Integer offset;  //查询数据起始位置
    private Integer limit;   //查询数据个数
    private String search;  //搜索内容
    private String sort;    //排序列
    private String order;   //正排or倒排

    /**
     * default 构造器
     */
    public TableParam() {}

    /**
     * 构造器，根据request获取参数
     * @param request 请求体
     */
    public TableParam(HttpServletRequest request) {
        //获取分页信息
        String offset = request.getParameter("offset");
        String limit = request.getParameter("limit");
        if(offset != null && limit != null) {
            this.offset = Integer.parseInt(offset);
            this.limit = Integer.parseInt(limit);
        }

        //处理排序
        this.sort = request.getParameter("sort");
        this.order = request.getParameter("order");

        //搜索文本
        this.search = request.getParameter("search");
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
