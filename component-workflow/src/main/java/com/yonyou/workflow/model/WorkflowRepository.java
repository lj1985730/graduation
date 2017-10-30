package com.yonyou.workflow.model;

import com.yonyou.workflow.infrastructure.WorkflowBusiness;
import org.apache.ibatis.annotations.Param;

/**
 * 工作流自定义仓储
 * @author Liu Jun at 2017-10-09 09:17:57
 * @since v1.0.0
 */
public interface WorkflowRepository {

    /**
     * 设置业务数据的流程实例ID
     * @param workflowBusiness 业务类型枚举
     * @param processInstanceId 流程实例ID
     * @param businessId 业务数据ID
     */
    void setInstanceIdIntoBusinessData(@Param("workflowBusiness") WorkflowBusiness workflowBusiness,
                                       @Param("processInstanceId") String processInstanceId,
                                       @Param("businessId") String businessId);

    /**
     * 删除业务数据的流程实例ID
     * @param businessTable 业务表名
     * @param businessColumn 业务主键列名
     * @param businessId 业务数据ID
     */
    void clearInstanceIdFromBusinessData(@Param("businessTable") String businessTable,
                                         @Param("businessColumn") String businessColumn,
                                         @Param("businessId") String businessId);

    /**
     * 获取业务数据的流程实例ID
     * @param workflowBusiness  业务类型枚举
     * @param businessId    业务数据ID
     * @return 流程实例ID
     */
    String getProcessInstanceId(@Param("workflowBusiness") WorkflowBusiness workflowBusiness,
                                @Param("businessId") String businessId);
}
