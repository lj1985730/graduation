package com.yonyou.workflow.infrastructure;

/**
 * 组件名称
 * @author Liu Jun
 * @since v1.0.0
 */
public enum ActivityTranslate {

    USER_TASK("userTask", "用户任务"),
    SERVICE_TASK("serviceTask", "系统任务"),
    START("startEvent", "开始节点"),
    END("endEvent", "结束节点"),
    EXCLUSIVE("exclusiveGateway", "条件判断节点(系统自动根据条件处理)"),
    INCLUSIVE("inclusiveGateway", "并行处理任务"),
    CALL("callActivity", "子流程");

    private String name;
    private String description;

    ActivityTranslate(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
