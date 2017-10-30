package com.yonyou.workflow.infrastructure;

import com.graduation.web.util.TableParam;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WorkflowVo extends TableParam implements Serializable {

    private String processDefinitionId; 			// 流程定义ID
    private String processDefinitionKey; 			// 流程定义Key（流程定义标识）

    private String processInstanceId; 				// 流程实例ID
    private String processInstanceName; 			// 流程实例名称

    private String taskDefinitionKey; 				// 任务定义Key（任务环节标识）
    private String taskId; 							// 任务ID
    private String taskName; 						// 任务名称

    private String businessTable;					// 业务绑定Table
    private String businessId;						// 业务绑定ID
    private String businessColumn; 					// 业务绑定字段
    private String businessFormKey; 				// 业务表单URL

    private String title; 		                    // 任务标题
    private TaskState state; 		                // 任务状态

    private String applicantId; 	                //流程申请人ID
    private String applicantName;	                //流程申请人姓名

    private String assigneeId; 		                // 任务执行人ID
    private String assigneeName; 	                // 任务执行人名称

    private String comment; 		                // 任务意见
    private String flag; 			                // 意见状态

    private String beginDateStr;			        // 开始查询日期字符串
    private String endDateStr;			            // 结束查询日期字符串

    private Date beginDate;			                // 开始查询日期
    private Date endDate;			                // 结束查询日期

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessInstanceName() {
        return processInstanceName;
    }

    public void setProcessInstanceName(String processInstanceName) {
        this.processInstanceName = processInstanceName;
    }

    public String getTaskDefinitionKey() {
        return taskDefinitionKey;
    }

    public void setTaskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getBusinessTable() {
        return businessTable;
    }

    public void setBusinessTable(String businessTable) {
        this.businessTable = businessTable;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getBusinessColumn() {
        return businessColumn;
    }

    public void setBusinessColumn(String businessColumn) {
        this.businessColumn = businessColumn;
    }

    public String getBusinessFormKey() {
        return businessFormKey;
    }

    public void setBusinessFormKey(String businessFormKey) {
        this.businessFormKey = businessFormKey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(String assigneeId) {
        this.assigneeId = assigneeId;
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Date getBeginDate() {
        if(this.beginDate != null) {
            return this.beginDate;
        } else if(StringUtils.isNotBlank(this.beginDateStr)) {
            try {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(this.beginDateStr);
            } catch (ParseException e) {
                throw new RuntimeException("日期转化出错！");
            }
        }
        return null;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        if(this.endDate != null) {
            return this.endDate;
        } else if(StringUtils.isNotBlank(this.endDateStr)) {
            try {
                return new SimpleDateFormat("yyyy-MM-dd 23:59:59").parse(this.endDateStr);
            } catch (ParseException e) {
                throw new RuntimeException("日期转化出错！");
            }
        }
        return null;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getBeginDateStr() {
        if(StringUtils.isNotBlank(this.beginDateStr)) {
            return this.beginDateStr;
        } else if(this.beginDate != null) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.beginDate);
        }
        return null;
    }

    public void setBeginDateStr(String beginDateStr) {
        this.beginDateStr = beginDateStr;
    }

    public String getEndDateStr() {
        if(StringUtils.isNotBlank(this.endDateStr)) {
            return this.endDateStr;
        } else if(this.endDate != null) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.endDate);
        }
        return null;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }
}
