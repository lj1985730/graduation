package com.yonyou.workflow.application.service;

public interface WorkflowService {

    /**
     * 开启工作流提交审批
     * @param businessId 业务主键
     * @param applicantId	申请人ID
     * @param applicantName	申请人姓名
     */
    void startSubmit(String businessId, String applicantId, String applicantName);

    /**
     * 再次提交
     * @param businessId 业务数据ID
     * @param comment 批注
     */
    void retrySubmit(String businessId, String comment);
}
