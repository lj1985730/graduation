package com.yonyou.workflow.infrastructure;

/**
 * 工作流业务信息枚举
 * @author Liu Jun
 * @since v1.0.0
 */
public enum WorkflowBusiness {

    //*******审计******
    AUDIT_APPLY("audit_proj_apply", "AUDIT_PROJECT", "PK_PROJECT"), //审计项目申请流程
    AUDIT_SCHEME_APPLY("audit_scheme_apply", "AUDIT_SCHEME", "PK_SCHEME"),  //审计方案审批流程
    REFORM_NOTICE_AUDIT_APPLY("audit_reformNotice_send", "AUDIT_REFORM_NOTICE", "PK_NOTICE"),  //审计整改通知下发流程

    //*******监事会*****
    SUPER_EVENTS_APPLY("super_events_apply", "SUPER_ENTERPRISE_REPORT", "ENTERPRISE_REPORT_ID"),    //监事会三重一大审批流程
    SUPER_ENTERPRISE_CORR_PLAN_APPLY("super_enterpriseCorrPlan_apply", "SUPER_ENTERPRISE_REPORT", "ENTERPRISE_REPORT_ID"),    //监事会整改方案申请流程
    SUPER_CHECK_NOTICE_APPLY("super_checkNotice_apply", "SUPER_ENTERPRISE_REPORT", "ENTERPRISE_REPORT_ID"),    //监事会检查通知下发
    SUPER_CHECK_SCHEME_APPLY("super_checkScheme_apply", "SUPER_ENTERPRISE_REPORT", "ENTERPRISE_REPORT_ID"),    //监事会检查方案下发
    SUPER_CHECK_REPORT_APPLY("super_checkReport_apply", "SUPER_ENTERPRISE_REPORT", "ENTERPRISE_REPORT_ID"),    //监事会检查报告下发
    RECTIFY_APPROVE("su_checkRectify_apply", "super_enterprise_report", "ENTERPRISE_REPORT_ID"),    //监事会整改情况审批
    MEETING_APPLY("su_meeting_apply", "super_meeting_manage", "MEETING_ID"),    //监事会会议上报

    //*******计财******
    EW_REPORT_APPLY("ew_report_apply", " EW_REPORT", "ID"),	//计财风险预警报告下发
    EW_TARGET_AREA_APPROVE("ew_target_approve", "EW_PLAN", "ID"),	//财务预警阈值填报审批流程

    //*******投资规划******
    IP_PROJECT_APPLY("ip_proj_apply", "ip_project", "ID"),  //投资规划项目库申请流程
    IP_PROJECT_COMPLETION_APPLY("ip_project_completion_apply", "IP_PROJECT_COMPLETION", "ID"),  //投资规划模块--完成情况上报审批
    IP_PROJECT_EVALUATE_APPLY("ip_project_evaluate_apply", "ip_project_evaluate", "ID"),  //投资规划模块--项目后评价审批
    IP_ANNUAL_PLAN_APPLY("ip_annual_plan_apply", "IP_ANNUAL_PLAN", "ID"),  //投资规划模块--年度投资计划上报审批
    IP_PLAN_INDEX_APPLY("ip_plan_index", "IP_PLAN", "ID"),  //投资规划模块--规划指标维护审批
    IP_PLAN_COMP_APPLY("ip_plan_comp_apply", "ip_ent_plan", "ID");  //投资规划模块--企业完成值上报审批

    private String processDefinitionKey;    //流程定义KEY
    private String businessTable;           //业务表名
    private String businessColumn;          //业务表主键字段

    /**
     * 构造器
     * @param processDefinitionKey 流程定义KEY
     * @param businessTable 业务表名
     * @param businessColumn 业务表主键字段
     */
    WorkflowBusiness(String processDefinitionKey, String businessTable, String businessColumn) {
        this.processDefinitionKey = processDefinitionKey;
        this.businessTable = businessTable;
        this.businessColumn = businessColumn;
    }

    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public String getBusinessTable() {
        return businessTable;
    }

    public String getBusinessColumn() {
        return businessColumn;
    }
}
