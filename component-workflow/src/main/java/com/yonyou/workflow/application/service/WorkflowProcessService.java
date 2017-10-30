package com.yonyou.workflow.application.service;

import com.yonyou.workflow.infrastructure.ProcessState;
import com.yonyou.workflow.infrastructure.WorkflowBusiness;
import com.yonyou.workflow.infrastructure.WorkflowVo;
import com.yonyou.workflow.model.WorkflowRepository;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class WorkflowProcessService {

    /**
     * 流程变量中的状态KEY
     */
    public static final String VAR_STATE_KEY = "state";

    @Resource
    private WorkflowTaskService workflowTaskService;

//    @Resource
//    private ProcessEngine processEngine;
//
//    @Resource
//    private ProcessEngineFactoryBean processEngineFactory;

//    @Resource
//    private RepositoryService repositoryService;

    @Resource
    private HistoryService historyService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private IdentityService identityService;

    @Resource
    private TaskService taskService;

    @Resource
    private WorkflowRepository workflowRepository;

    /**
     * 查询流程定义主键
     * @param processInstanceId 流程实例ID
     * @return 流程定义主键
     */
    public String searchProcessDefinitionId(String processInstanceId) {
        if(StringUtils.isBlank(processInstanceId)) {
            throw new NullPointerException("流程实例ID不可为空！");
        }
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()   //流程实例
                .processInstanceId(processInstanceId).singleResult();

        String processDefinitionId;
        if(processInstance != null) {   //如果流程正在进行
            processDefinitionId = processInstance.getProcessDefinitionId(); //获取流程定义
        } else {    //如果流程已完成，到历史记录中检索流程
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(processInstanceId).singleResult();
            processDefinitionId = historicProcessInstance.getProcessDefinitionId();
        }

        return processDefinitionId;
    }

    /**
     * 获取流程实例对象
     * @param processInstanceId 流程实例ID
     * @return 流程实例对象
     */
    ProcessInstance searchProcessInstance(String processInstanceId) {
        if(StringUtils.isBlank(processInstanceId)) {
            throw new NullPointerException("流程实例ID不可为空！");
        }
        return runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
    }

    /**
     * 获取流程实例历史对象
     * @param processInstanceId 流程实例ID
     * @return 流程实例历史对象
     */
    HistoricProcessInstance searchHistoricProcessInstance(String processInstanceId) {
        if(StringUtils.isBlank(processInstanceId)) {
            throw new NullPointerException("流程实例ID不可为空！");
        }
        return historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
    }

//    /**
//     * 查询流程列表
//     * @param vo 流程实例ID
//     * @return 流程实例历史对象
//     */
//    public BootstrapTablePaginationResult<Object[]> searchProcess(WorkflowVo vo, String category) {
//        /*
//		 * 查询两个对象，一个是ProcessDefinition（流程定义），一个是Deployment（流程部署）
//		 */
//        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().latestVersion()
//                .active().orderByProcessDefinitionKey().asc();
//
//        if (StringUtil.isNotEmpty(category)) {
//            processDefinitionQuery.processDefinitionCategory(category);
//        }
//
//        BootstrapTablePaginationResult<Object[]> result = new BootstrapTablePaginationResult<>();
//
//        result.setTotal(Long.valueOf(processDefinitionQuery.count()).intValue());   //总数
//
//        List<ProcessDefinition> processDefinitionList = processDefinitionQuery.listPage(vo.getOffset(), vo.getLimit());
//
//        List<Object[]> list = new ArrayList<>();
//
//        for (ProcessDefinition processDefinition : processDefinitionList) {
//            String deploymentId = processDefinition.getDeploymentId();
//            Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
//            list.add(new Object[] { processDefinition, deployment });
//        }
//
//        result.setRows(list);
//        return result;
//    }

    /**
     * 获取业务数据的流程实例ID
     * @param workflowBusiness  业务类型
     * @param businessId        业务数据ID
     * @return 流程实例ID
     */
    public String searchProcessInstanceId(WorkflowBusiness workflowBusiness, String businessId) {
        if(workflowBusiness == null || StringUtils.isBlank(businessId)) {
            throw new NullPointerException("业务类型或业务数据ID不可为空！");
        }
        return workflowRepository.getProcessInstanceId(workflowBusiness, businessId);
    }

    /**
     * 取得流程实例当前task对象
     * @param processInstanceId 流程实例ID
     * @return 当前task对象
     */
    private TaskEntity searchCurrentTask(String processInstanceId) {
        if(StringUtils.isBlank(processInstanceId)) {
            throw new NullPointerException("流程实例ID不可为空！");
        }
        return (TaskEntity) taskService.createTaskQuery().processInstanceId(processInstanceId).active().singleResult();
    }

    /**
     * 启动流程
     * @param business          业务类型枚举
     * @param businessId        业务数据主键
     * @param applicantId       申请人编号
     * @param applicantName     申请人姓名
     * @param title             流程标题，(可能支持)显示在待办任务标题
     * @param variables         流程变量
     * @return 流程实例ID
     */
    public String startProcess(WorkflowBusiness business, String businessId, String applicantId, String applicantName, String title, Map<String, Object> variables) {
        if(business == null) {
            throw new NullPointerException("未指定业务类型！");
        }
        if(StringUtils.isBlank(businessId)) {
            throw new NullPointerException("业务数据主键不可为空！");
        }

        // 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
        identityService.setAuthenticatedUserId(applicantId);

        // 设置流程变量
        if (variables == null) {
            variables = new HashMap<>();
        }

        // 设置流程标题
        if (StringUtils.isNotBlank(title)) {
            variables.put("title", title);
        }
        // 设置流程启动人
        if (StringUtils.isNoneBlank(applicantId)) {
            variables.put("applicantId", applicantId);
        }
        // 设置流程启动人名称
        if (StringUtils.isNoneBlank(applicantName)) {
            variables.put("applicantName", applicantName);
        }

        variables.put(VAR_STATE_KEY, ProcessState.START); //保存状态

        // 启动流程
        ProcessInstance processInstance =
                runtimeService.startProcessInstanceByKey(business.getProcessDefinitionKey(), business.getBusinessTable() + ":" + businessId, variables);

        // 更新业务表流程实例ID
        workflowRepository.setInstanceIdIntoBusinessData(business, processInstance.getId(), businessId);
        return processInstance.getId();
    }

    /**
     * 完成流程实例当前活动的任务
     * @param processInstanceId 流程实例ID
     * @param comment   批注
     * @param variables 变量集合
     */
    public void completeCurrentTask(String processInstanceId, ProcessState state, String comment, Map<String, Object> variables) {
        if(StringUtils.isBlank(processInstanceId)) {
            throw new NullPointerException("流程实例ID不可为空！");
        }
        Task task = this.searchCurrentTask(processInstanceId);

        if(variables == null) {
            variables = new HashMap<>();
        }

        variables.put(VAR_STATE_KEY, state);	//记录状态

        if (task != null) {
            workflowTaskService.complete(task.getId(), processInstanceId, null, comment, variables);
        }
    }

    /**
     * 删除流程实例
     * @param processInstanceId 流程实例ID
     * @param vo 业务参数集合
     */
    public void removeProcessInstance(String processInstanceId, WorkflowVo vo) {
        if(StringUtils.isBlank(processInstanceId)) {
            throw new NullPointerException("流程实例ID不可为空！");
        }
        runtimeService.deleteProcessInstance(processInstanceId, "删除流程");
        //删除业务表的流程实例ID
        workflowRepository.clearInstanceIdFromBusinessData(vo.getBusinessTable(), vo.getBusinessColumn(), vo.getBusinessId());
    }

    public <T> void setDataState(List<T> dataList, Class<T> cla, String setterMethodName) {
        if(dataList == null || dataList.isEmpty()) {
            return;
        }
        Method setterMethod;
        Method getterMethod;
        Object processInstanceId;
        try {
            setterMethod = cla.getDeclaredMethod(setterMethodName, String.class);
            getterMethod = cla.getDeclaredMethod("getProcInsId");
            for(T data : dataList) {
                processInstanceId = getterMethod.invoke(data);
                if(processInstanceId == null) {
                    setterMethod.invoke(data, ProcessState.NOT_BOUND.toString());
                } else {
                    setterMethod.invoke(data, this.checkProcessInstanceState(processInstanceId.toString()).toString());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("获取流程实例属性出错", e);
        }
    }

    /**
     * 判断流程状态
     * @param processInstanceId 流程实例ID
     */
    private ProcessState checkProcessInstanceState(String processInstanceId) {
        if(StringUtils.isBlank(processInstanceId)) {    //无流程实例ID视为未绑定
            return ProcessState.NOT_BOUND;
        }
        if(runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).suspended().singleResult() != null) {
            return ProcessState.SUSPENDED;
        }
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();
        if(processInstance == null) { //未找到激活流程即为已完成
            return ProcessState.FINISHED;
        }
        Task task = this.searchCurrentTask(processInstanceId);

        Object state = taskService.getVariable(task.getId(), VAR_STATE_KEY);
        if(state == null) {
            return ProcessState.UNKNOWN;
        }
        return (ProcessState)state;
    }

//    /**
//     * 设置任务组
//     * @param candidateGroupIdExpressions	代理人组
//     * @param variables 变量集合
//     */
//    private void setTaskGroup(Set<Expression> candidateGroupIdExpressions, Map<String, Object> variables) {
//        StringBuilder roles = new StringBuilder();
//        for (Expression expression : candidateGroupIdExpressions) {
//            String expressionText = expression.getExpressionText();
//            String roleName = identityService.createGroupQuery().groupId(expressionText).singleResult().getName();
//            roles.append(roleName);
//        }
//        variables.put("任务所属角色", roles.toString());
//    }

//    /**
//     * 设置当前处理人信息
//     * @param currentTask	当前节点
//     * @param variables	变量集合
//     */
//    private void setCurrentTaskAssignee(Task currentTask, Map<String, Object> variables) {
//        String assignee = currentTask.getAssignee();
//        if (assignee != null) {
//            org.activiti.engine.identity.User assigneeUser = identityService.createUserQuery().userId(assignee)
//                    .singleResult();
//            String userInfo = assigneeUser.getFirstName() + " " + assigneeUser.getLastName();
//            variables.put("当前处理人", userInfo);
//        }
//    }

//    /**
//     * 读取带跟踪的图片
//     * @param processInstanceId 流程实例ID
//     * @return 封装了各种节点信息
//     */
//    public InputStream searchTraceImg(String processInstanceId) {
//
//        String processDefinitionId = this.searchProcessDefinitionId(processInstanceId);
//
//        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
//
//        List<HistoricActivityInstance> highLightedActivitiList = historyService.createHistoricActivityInstanceQuery()
//                .processInstanceId(processInstanceId).list();
//        //高亮环节id集合
//        List<String> highLightedActivities = new ArrayList<>();
//        //高亮线路id集合
//        ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(processDefinitionId);
//        List<String> highLightedFlows = getHighLightedFlows(definitionEntity, highLightedActivitiList);
//
//        for(HistoricActivityInstance tempActivity : highLightedActivitiList){
//            String activityId = tempActivity.getActivityId();
//            highLightedActivities.add(activityId);
//        }
//
//        Context.setProcessEngineConfiguration(processEngineFactory.getProcessEngineConfiguration());
//
//        return processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator()
//                .generateDiagram(bpmnModel, "png", highLightedActivities, highLightedFlows, "宋体", "宋体", "宋体", null, 1.0);
//    }

//    /**
//     * 获取需要高亮的线
//     * @param processDefinitionEntity   流程定义实体
//     * @param historicActivityInstances 流程历史记录
//     * @return 需要高亮的线
//     */
//    private List<String> getHighLightedFlows(
//            ProcessDefinitionEntity processDefinitionEntity,
//            List<HistoricActivityInstance> historicActivityInstances) {
//        List<String> highFlows = new ArrayList<>(); // 用以保存高亮的线flowId
//        for (int i = 0; i < historicActivityInstances.size() - 1; i++) {    // 对历史流程节点进行遍历
//            ActivityImpl activityImpl = processDefinitionEntity.findActivity(historicActivityInstances.searchById(i).getActivityId());// 得到节点定义的详细信息
//            List<ActivityImpl> sameStartTimeNodes = new ArrayList<>();  // 用以保存后需开始时间相同的节点
//            ActivityImpl sameActivityImpl1 = processDefinitionEntity.findActivity(historicActivityInstances.searchById(i + 1).getActivityId());
//            // 将后面第一个节点放在时间相同节点的集合里
//            sameStartTimeNodes.add(sameActivityImpl1);
//            for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
//                HistoricActivityInstance activityImpl1 = historicActivityInstances.searchById(j);// 后续第一个节点
//                HistoricActivityInstance activityImpl2 = historicActivityInstances.searchById(j + 1);// 后续第二个节点
//                if (activityImpl1.getStartTime().equals( activityImpl2.getStartTime() )) {
//                    // 如果第一个节点和第二个节点开始时间相同保存
//                    ActivityImpl sameActivityImpl2 = processDefinitionEntity.findActivity(activityImpl2.getActivityId());
//                    sameStartTimeNodes.add(sameActivityImpl2);
//                } else {
//                    // 有不相同跳出循环
//                    break;
//                }
//            }
//            List<PvmTransition> pvmTransitions = activityImpl.getOutgoingTransitions();// 取出节点的所有出去的线
//            for (PvmTransition pvmTransition : pvmTransitions) {
//                // 对所有的线进行遍历
//                ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition.getDestination();
//                // 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
//                if (sameStartTimeNodes.contains(pvmActivityImpl)) {
//                    highFlows.add(pvmTransition.getId());
//                }
//            }
//        }
//        return highFlows;
//    }

//    /**
//     * 流程跟踪图信息
//     * @param processInstanceId 流程实例ID
//     * @return 封装了各种节点信息
//     */
//    public List<Map<String, Object>> searchTraceInfos(String processInstanceId) {
//        Execution execution = runtimeService.createExecutionQuery().executionId(processInstanceId).singleResult();// 流程执行
//
//        String activityId = execution.getActivityId() == null ? "" : execution.getActivityId();
//
//        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
//                .processInstanceId(processInstanceId).singleResult();
//
//        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
//                .getDeployedProcessDefinition(processInstance.getProcessDefinitionId());
//
//        List<ActivityImpl> activitiList = processDefinition.getActivities();    // 获得当前任务定义的所有节点
//
//        List<Map<String, Object>> activityInfos = new ArrayList<>();
//        for (ActivityImpl activity : activitiList) {
//
//            String id = activity.getId();
//            boolean currentActiviti = false;
//
//            // 当前节点
//            if (id.equals(activityId)) {
//                currentActiviti = true;
//            }
//
//            Map<String, Object> activityImageInfo = this.packageSingleActivitiInfo(activity, processInstance, currentActiviti);
//
//            activityInfos.add(activityImageInfo);
//        }
//
//        return activityInfos;
//    }

//    /**
//     * 封装输出信息，包括：当前节点的X、Y坐标、变量信息、任务类型、任务描述
//     * @param activity	流程节点
//     * @param processInstance	流程实例
//     * @param currentActiviti	是否当前节点
//     * @return 输出信息
//     */
//    private Map<String, Object> packageSingleActivitiInfo(ActivityImpl activity, ProcessInstance processInstance,
//                                                          boolean currentActiviti) {
//        Map<String, Object> activityInfo = new HashMap<>();
//        activityInfo.put("currentActiviti", currentActiviti);
//        this.setPosition(activity, activityInfo);
//        this.setWidthAndHeight(activity, activityInfo);
//
//        Map<String, Object> vars = new HashMap<>();
//        Map<String, Object> properties = activity.getProperties();
//        vars.put("节点名称", properties.searchById("name"));
//        vars.put("任务类型", ActUtils.parseToZhType(properties.searchById("type").toString()));
//        vars.put("节点说明", properties.searchById("documentation"));
//        vars.put("描述", activity.getProcessDefinition().getDescription());
//
//        ActivityBehavior activityBehavior = activity.getActivityBehavior(); //判断节点类型
//        if (activityBehavior instanceof UserTaskActivityBehavior) { //用户节点
//
//            Task currentTask = null;
//
//            // 当前节点的task
//            if (currentActiviti) {
//                currentTask = workflowTaskService.getCurrentTaskInfo(processInstance);
//            }
//
//            // 当前任务的分配角色
//            UserTaskActivityBehavior userTaskActivityBehavior = (UserTaskActivityBehavior) activityBehavior;
//            TaskDefinition taskDefinition = userTaskActivityBehavior.getTaskDefinition();
//            Set<Expression> candidateGroupIdExpressions = taskDefinition.getCandidateGroupIdExpressions();
//            if (!candidateGroupIdExpressions.isEmpty()) {
//
//                setTaskGroup(vars, candidateGroupIdExpressions);    // 任务的处理角色
//
//                // 当前处理人
//                if (currentTask != null) {
//                    this.setCurrentTaskAssignee(vars, currentTask);
//                }
//            }
//        }
//
//        activityInfo.put("vars", vars);
//        return activityInfo;
//    }

//    /**
//     * 流程进度前进一步
//     * @param processInstanceId 任务所在流程实例ID
//     * @param variables 变量集合
//     */
//    public void instanceForward(String processInstanceId, Map<String, Object> variables) {
//        if(StringUtils.isBlank(processInstanceId)) {
//            throw new NullArgumentException("流程实例ID不可为空！");
//        }
//        workflowTaskService.taskForward(this.searchCurrentTask(processInstanceId), variables);
//    }

//    /**
//     * 流程进度后退一步
//     * @param processInstanceId 任务所在流程实例ID
//     * @param variables 变量集合
//     */
//    public void instanceBack(String processInstanceId, Map<String, Object> variables) {
//        if(StringUtils.isBlank(processInstanceId)) {
//            throw new NullArgumentException("流程实例ID不可为空！");
//        }
//        workflowTaskService.taskBack(this.searchCurrentTask(processInstanceId), variables);
//    }

//    /**
//     * 获取流程组件
//     * @param processDefinitionId   流程定义ID
//     * @param activityId            组件ID
//     * @return 流程组件
//     */
//    public ActivityImpl getActivity(String processDefinitionId, String activityId) {
//        if(StringUtils.isBlank(processDefinitionId)) {
//            throw new NullArgumentException("流程定义ID不可为空！");
//        }
//        if(StringUtils.isBlank(activityId)) {
//            throw new NullArgumentException("组件ID不可为空！");
//        }
//        return this.searchProcessDefinition(processDefinitionId).findActivity(activityId);
//    }

//    /**
//     * 获取流程定义实体
//     * @param processDefinitionId 流程定义ID
//     * @return 流程定义实体
//     */
//    private ProcessDefinitionEntity searchProcessDefinition(String processDefinitionId) {
//        return (ProcessDefinitionEntity) ((RepositoryServiceImpl)repositoryService).getDeployedProcessDefinition(processDefinitionId);
//    }

//    /**
//     * 设置宽度、高度属性
//     * @param activity  节点
//     * @param activityInfo 节点信息集合
//     */
//    private void setWidthAndHeight(ActivityImpl activity, Map<String, Object> activityInfo) {
//        activityInfo.put("width", activity.getWidth());
//        activityInfo.put("height", activity.getHeight());
//    }

//    /**
//     * 设置坐标位置
//     * @param activity  节点
//     * @param activityInfo  节点信息集合
//     */
//    private void setPosition(ActivityImpl activity, Map<String, Object> activityInfo) {
//        activityInfo.put("x", activity.getX());
//        activityInfo.put("y", activity.getY());
//    }
}
