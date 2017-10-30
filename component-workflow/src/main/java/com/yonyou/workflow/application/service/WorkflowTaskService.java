package com.yonyou.workflow.application.service;

import com.graduation.authentication.entity.Account;
import com.graduation.authentication.service.AccountService;
import com.graduation.core.base.dto.Page;
import com.yonyou.workflow.infrastructure.ProcessState;
import com.yonyou.workflow.infrastructure.TaskState;
import com.yonyou.workflow.infrastructure.WorkflowBusiness;
import com.yonyou.workflow.infrastructure.WorkflowVo;
import com.yonyou.workflow.model.WorkflowModel;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.CommentEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional
public class WorkflowTaskService {

//    @Resource
//    private ProcessEngine processEngine;

    @Resource
    private TaskService taskService;

    @Resource
    private FormService formService;

    @Resource
    private HistoryService historyService;

//    @Resource
//    private RuntimeService runtimeService;

    @Resource
    private AccountService accountService;

    @Resource
    private WorkflowProcessService workflowProcessService;

    /**
     * 获取人员待办分页列表
     * @param vo 		查询条件
     * @param userId 	人员ID
     * @return 待办分页列表
     */
    public Page<WorkflowModel> searchTodoPager(WorkflowVo vo, String userId) {

        if(StringUtils.isBlank(userId) || vo == null) {
            throw new NullPointerException("人员ID或者查询vo不可为空！");
        }

        List<WorkflowModel> actList = this.searchTodoTasks(vo, userId);	//全部数据

        Page<WorkflowModel> result = new Page<>(vo.getOffset(), vo.getLimit());
        result.setRows(actList);
        result.setTotal(actList.size());
        return result;
    }

    /**
     * 获取待办列表
     * @param vo        查询条件值对象
     * @param userId    人员ID
     * @return 待办列表
     */
    private List<WorkflowModel> searchTodoTasks(WorkflowVo vo, String userId) {

        List<WorkflowModel> assignedTasks = this.searchAssignedTasks(vo, userId);	//已签收

        List<WorkflowModel> newTasks = this.searchNewTasks(vo, userId);	//待签收

        List<WorkflowModel> todoList = new ArrayList<>();
        todoList.addAll(assignedTasks);
        todoList.addAll(newTasks);

        this.setBusiness(todoList); //设置业务信息

        return todoList;
    }

    /**
     * 查询已签收的任务
     * @param vo        查询条件值对象
     * @param userId    人员ID
     * @return 已签收的任务
     */
    private List<WorkflowModel> searchAssignedTasks(WorkflowVo vo, String userId) {
        //搜索任务
        TaskQuery taskQuery = taskService.createTaskQuery()
                .taskAssignee(userId)	//签收人
                .active()				//有效
                .includeProcessVariables()	//包含流程变量
                .orderByTaskCreateTime().desc();	//排序

        // 设置查询条件
        if (StringUtils.isNotBlank(vo.getProcessDefinitionKey())) {	//流程定义
            taskQuery.processDefinitionKey(vo.getProcessDefinitionKey());
        }
        if (StringUtils.isNotBlank(vo.getProcessInstanceId())) {	    //流程实例
            taskQuery.processInstanceId(vo.getProcessInstanceId());
        }
        if (StringUtils.isNotBlank(vo.getBeginDateStr())) {         //开始时间
            taskQuery.taskCreatedAfter(vo.getBeginDate());
        }
        if (StringUtils.isNotBlank(vo.getEndDateStr())) {           //截至时间
            taskQuery.taskCreatedBefore(vo.getEndDate());
        }

        List<Task> taskList = taskQuery.list();	// 执行查询

        List<WorkflowModel> result = new ArrayList<>();	//结果集

        //task转模型
        WorkflowModel model;
        for (Task task : taskList) {
            model = new WorkflowModel(task);
            model.setState(TaskState.TODO);
            result.add(model);
        }

        return result;
    }

    /**
     * 查询未被签收的任务
     * @param vo    查询条件值对象
     * @param userId 人员ID
     * @return 未被签收的任务
     */
    private List<WorkflowModel> searchNewTasks(WorkflowVo vo, String userId) {

        TaskQuery taskQuery = taskService.createTaskQuery()
                .taskCandidateUser(userId)	//候选人
                .active()					//生效
                .includeProcessVariables()	//流程变量
                .orderByTaskCreateTime().desc();	//排序

        // 设置查询条件
        if (StringUtils.isNotBlank(vo.getProcessDefinitionKey())) {	//流程定义
            taskQuery.processDefinitionKey(vo.getProcessDefinitionKey());
        }
        if (StringUtils.isNotBlank(vo.getProcessInstanceId())) {	    //流程实例
            taskQuery.processInstanceId(vo.getProcessInstanceId());
        }
        if (StringUtils.isNotBlank(vo.getBeginDateStr())) {         //开始时间
            taskQuery.taskCreatedAfter(vo.getBeginDate());
        }
        if (StringUtils.isNotBlank(vo.getEndDateStr())) {           //截至时间
            taskQuery.taskCreatedBefore(vo.getEndDate());
        }

        List<Task> taskList = taskQuery.list();	// 执行查询

        List<WorkflowModel> result = new ArrayList<>();	//结果集

        //task转模型
        WorkflowModel model;
        for (Task task : taskList) {
            model = new WorkflowModel(task);
            model.setState(TaskState.CLAIM);
            result.add(model);
        }

        return result;
    }

    /**
     * 获取人员已办分页列表
     * @param vo 		查询条件
     * @param userId 	人员ID
     * @return 已办分页列表
     */
    public Page<WorkflowModel> searchHistoricTaskPager(WorkflowVo vo, String userId) {

        if(StringUtils.isBlank(userId) || vo == null) {
            throw new NullPointerException("人员ID或者查询vo不可为空！");
        }

        HistoricTaskInstanceQuery histTaskQuery = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId)
                .finished()
                .includeProcessVariables()		//流程变量
                .includeTaskLocalVariables()	//任务变量
                .orderByHistoricTaskInstanceEndTime().desc();

        // 设置查询条件
        if (StringUtils.isNotBlank(vo.getProcessInstanceId())) {
            histTaskQuery.processInstanceId(vo.getProcessInstanceId());
        }
        if (StringUtils.isNotBlank(vo.getBeginDateStr())) {         //开始时间
            histTaskQuery.taskCompletedAfter(vo.getBeginDate());
        }
        if (StringUtils.isNotBlank(vo.getEndDateStr())) {           //截至时间
            histTaskQuery.taskCompletedBefore(vo.getEndDate());
        }

        Page<WorkflowModel> result = new Page<>(vo.getOffset(), vo.getLimit());

        // 查询总数
        result.setTotal(Long.valueOf(histTaskQuery.count()).intValue());

        // 分页列表
        List<HistoricTaskInstance> pageList = histTaskQuery.listPage(vo.getOffset(), vo.getLimit());

        List<WorkflowModel> models = new ArrayList<>();	//结果集

        //task转模型
        WorkflowModel model;
        for (HistoricTaskInstance task : pageList) {
            model = new WorkflowModel(task);
            model.setState(TaskState.FINISH);
            models.add(model);
        }

        result.setRows(models);
        return result;
    }

    /**
     * 获取流程下历史任务列表
     * @param processInstanceId 流程实例ID
     */
    public List<HistoricTaskInstance> searchHistoicTask(String processInstanceId) {

        if(StringUtils.isBlank(processInstanceId)) {
            throw new NullPointerException("流程实例ID不可为空！");
        }

        List<HistoricTaskInstance> taskInstanceList = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByHistoricTaskInstanceEndTime().asc()
                .list();

        if(taskInstanceList == null || taskInstanceList.isEmpty()) {
            return null;
        }

        //设置用户姓名
        Account account;
        for(HistoricTaskInstance taskInstance : taskInstanceList) {
            account = accountService.searchById(taskInstance.getAssignee());
            if(account != null) {
                taskInstance.setLocalizedName(account.getName());
            }
        }

        return taskInstanceList;
    }

    /**
     * 获取流程任务批注
     * @param taskId 流程任务ID
     */
    public List<CommentEntity> searchTaskComments(String taskId) {

        if(StringUtils.isBlank(taskId)) {
            throw new NullPointerException("任务ID不可为空！");
        }

        List<Comment> comments = taskService.getTaskComments(taskId);   //查询批注

        if(comments == null || comments.isEmpty()) {    //无数据直接返回
            return Collections.emptyList();
        }

        List<CommentEntity> resList = new ArrayList<>();

//        TbIgpUser user;
        CommentEntity commentEntity;
        for(Comment comment : comments) {   //转换批注接口为实体对象，主要为了处理人名

            commentEntity = new CommentEntity();

            try {
                BeanUtils.copyProperties(commentEntity, comment);   //属性拷贝
            } catch (Exception e) {
                throw new RuntimeException("数据转换出错！", e);
            }

            Account account = accountService.searchById(comment.getUserId());  //批注人处理
            commentEntity.setMessage(account.getName());
            resList.add(commentEntity);
        }

        return resList;
    }

    /**
     * 添加任务批注
     * @param taskId 任务ID
     * @param processInstanceId 流程实例ID
     * @param comment 批注
     * @param userId 操作人ID
     */
    private void addTaskComment(String taskId, String processInstanceId, String comment, String userId) {
        if(StringUtils.isBlank(taskId)) {
            throw new NullPointerException("任务ID不可为空！");
        }
        if(StringUtils.isBlank(processInstanceId)) {
            throw new NullPointerException("流程实例ID不可为空！");
        }
        if(StringUtils.isBlank(comment)) {
            throw new NullPointerException("批注不可为空！");
        }
        if(StringUtils.isNotBlank(userId)) {
            Authentication.setAuthenticatedUserId(userId);  // 批注人的名称
        }
        taskService.addComment(taskId, processInstanceId, comment);
    }

//    /**
//     * 获取当前节点信息
//     * @param processInstance 流程实例
//     * @return 当前节点信息
//     */
//    Task getCurrentTaskInfo(ProcessInstance processInstance) {
//        String activitiId = processInstance.getActivityId();
//        return taskService.createTaskQuery().processInstanceId(processInstance.getId())
//                .taskDefinitionKey(activitiId).singleResult();
//    }

    /**
     * 获取流程表单URL
     * @param processInstanceId 	流程实例ID
     * @param taskDefinitionKey	    流程任务定义KEY
     * @return 流程表单可编辑URL
     */
    public String getBusinessFormUrl(String processInstanceId, String taskDefinitionKey) {
        if(StringUtils.isBlank(processInstanceId)) {
            throw new NullPointerException("流程实例ID不可为空！");
        }
        if(StringUtils.isBlank(taskDefinitionKey)) {
            throw new NullPointerException("流程任务定义KEY不可为空！");
        }
        return this.getBusinessFormUrl(processInstanceId, taskDefinitionKey, null);
    }

    /**
     * 获取流程表单可编辑URL
     * @param processInstanceId 	流程实例ID
     * @param taskDefinitionKey	    流程任务定义KEY
     * @return 流程表单只读URL
     */
    public String getEditableBusinessFormUrl(String processInstanceId, String taskDefinitionKey) {
        if(StringUtils.isBlank(processInstanceId)) {
            throw new NullPointerException("流程实例ID不可为空！");
        }
        if(StringUtils.isBlank(taskDefinitionKey)) {
            throw new NullPointerException("流程任务定义KEY不可为空！");
        }
        return this.getBusinessFormUrl(processInstanceId, taskDefinitionKey, true);
    }

    /**
     * 获取流程表单只读URL
     * @param processInstanceId 	流程实例ID
     * @param taskDefinitionKey	    流程任务定义KEY
     * @return 流程表单只读URL
     */
    public String getReadonlyBusinessFormUrl(String processInstanceId, String taskDefinitionKey) {
        if(StringUtils.isBlank(processInstanceId)) {
            throw new NullPointerException("流程实例ID不可为空！");
        }
        if(StringUtils.isBlank(taskDefinitionKey)) {
            throw new NullPointerException("流程任务定义KEY不可为空！");
        }
        return this.getBusinessFormUrl(processInstanceId, taskDefinitionKey, false);
    }

    /**
     * 获取流程表单URL
     * @param processInstanceId 	流程实例ID
     * @param taskDefinitionKey	    流程任务定义KEY
     * @param editable 	            表单是否可编辑
     * @return 流程表单URL
     */
    private String getBusinessFormUrl(String processInstanceId, String taskDefinitionKey, Boolean editable) {

        WorkflowModel model = new WorkflowModel();
        ProcessInstance processInstance = workflowProcessService.searchProcessInstance(processInstanceId);
        if(processInstance != null) {
            model.setProcessInstance(processInstance);
        } else {
            HistoricProcessInstance historicProcessInstance = workflowProcessService.searchHistoricProcessInstance(processInstanceId);
            model.setHistoricProcessInstance(historicProcessInstance);
        }
        // 获取流程定义上的表单KEY
        String formKey = this.getBusinessFormKey(model.getProcessDefinitionId(), taskDefinitionKey);

        StringBuilder formUrl = new StringBuilder(formKey);

        if(editable != null) {
            if(editable) {
                formUrl.append("_edit");
            } else {
                formUrl.append("_view");
            }
        }

        formUrl.append("?id=").append(model.getBusinessId() != null ? model.getBusinessId() : "");
        return formUrl.toString();
    }

    /**
     * 获取流程表单（首先获取任务节点表单KEY，如果没有则取流程开始节点表单KEY）
     * @param processDefinitionId   流程定义ID
     * @param taskDefinitionKey     任务定义KEY
     * @return 流程表单
     */
    private String getBusinessFormKey(String processDefinitionId, String taskDefinitionKey) {
        if (StringUtils.isBlank(processDefinitionId)) {
            throw new NullPointerException("流程定义ID不可为空！");
        }

        String formKey = "";
        if (StringUtils.isNotBlank(taskDefinitionKey)) {
            try {
                formKey = formService.getTaskFormKey(processDefinitionId, taskDefinitionKey);
            } catch (Exception e) {
                formKey = "";
            }
        }
        if (StringUtils.isBlank(formKey)) {
            formKey = formService.getStartFormKey(processDefinitionId);
        }
        return formKey;
    }

    /**
     * 签收任务
     * @param taskId 任务ID
     * @param userId 签收用户ID（用户登录名）
     */
    public void claim(String taskId, String userId) {
        if(StringUtils.isBlank(taskId)) {
            throw new NullPointerException("任务ID不可为空！");
        }
        if(StringUtils.isBlank(userId)) {
            throw new NullPointerException("签收用户ID不可为空！");
        }
        taskService.claim(taskId, userId);
    }

    /**
     * 提交任务, 并保存意见
     * @param taskId 任务ID
     * @param processInstanceId 流程实例ID
     * @param userId 操作人账户ID
     * @param comment 任务提交意见的内容
     * @param variables 任务变量
     */
    public void complete(String taskId, String processInstanceId, String userId, String comment, Map<String, Object> variables) {
        if(StringUtils.isBlank(taskId)) {
            throw new NullPointerException("任务ID不可为空！");
        }
        if(StringUtils.isBlank(processInstanceId)) {
            throw new NullPointerException("流程实例ID不可为空！");
        }
        // 添加意见
        if (StringUtils.isNotBlank(comment)) {
            this.addTaskComment(taskId, processInstanceId, comment, userId); // 批注人的名称,一定要写，不然查看的时候不知道人物信息
        }

        // 设置流程变量
        if (variables == null) {
            variables = new HashMap<>();
        }

        // 提交任务
        taskService.complete(taskId, variables);
    }

    /**
     * 通过任务
     * @param taskId 任务ID
     * @param processInstanceId 流程实例ID
     * @param comment 任务意见内容
     * @param userId 操作人账户ID
     */
    public void passTask(String taskId, String processInstanceId, String userId, String comment) {
        if(StringUtils.isBlank(taskId)) {
            throw new NullPointerException("任务ID不可为空！");
        }
        if(StringUtils.isBlank(processInstanceId)) {
            throw new NullPointerException("流程实例ID不可为空！");
        }
        if(StringUtils.isBlank(userId)) {
            throw new NullPointerException("操作人ID不可为空！");
        }
        Map<String, Object> variables = new HashMap<>();
        variables.put("pass", 0);

        variables.put(WorkflowProcessService.VAR_STATE_KEY, ProcessState.PENDING); //保存状态为驳回

        this.complete(taskId, processInstanceId, userId, comment, variables);   // 提交任务
    }

    /**
     * 驳回任务, 并保存意见
     * @param taskId 任务ID
     * @param processInstanceId 流程实例ID
     * @param userId 操作人账户ID
     * @param comment 任务意见内容
     */
    public void rejectTask(String taskId, String processInstanceId, String userId, String comment) {
        if(StringUtils.isBlank(taskId)) {
            throw new NullPointerException("任务ID不可为空！");
        }
        if(StringUtils.isBlank(processInstanceId)) {
            throw new NullPointerException("流程实例ID不可为空！");
        }
        if(StringUtils.isBlank(userId)) {
            throw new NullPointerException("操作人ID不可为空！");
        }
        Map<String, Object> variables = new HashMap<>();
        variables.put("pass", 1);

        variables.put(WorkflowProcessService.VAR_STATE_KEY, ProcessState.REJECTED); //保存状态为驳回

        this.complete(taskId, processInstanceId, userId, comment, variables);        // 提交任务
    }

    /**
     * 设置模型业务信息
     * @param modelList  模型集合
     */
    private void setBusiness(List<WorkflowModel> modelList) {
        if(modelList == null || modelList.isEmpty()) {
            return;
        }
        String procInstanceId;
        ProcessInstance processInstance;
        String businessTable;
        for(WorkflowModel model : modelList) {
            procInstanceId = model.getProcessInstanceId();
            processInstance = workflowProcessService.searchProcessInstance(procInstanceId);
            businessTable = StringUtils.substringBefore(processInstance.getBusinessKey(), ":");
            model.setBusinessId(StringUtils.substringAfterLast(processInstance.getBusinessKey(), ":"));
            //循环输出 值
            for (WorkflowBusiness business: WorkflowBusiness.values()) {
                if(business.getBusinessTable().equalsIgnoreCase(businessTable)) {
                    model.setBusinessTable(business.getBusinessTable());
                    model.setBusinessColumn(business.getBusinessColumn());
                }
            }
        }
    }
//
//    /**
//     * 任务前进一步
//     * @param processInstanceId 流程实例ID
//     * @param variables 任务变量集合
//     */
//    public void taskForward(String processInstanceId, Map<String, Object> variables) {
//        this.taskForward(this.searchCurrentTask(processInstanceId), variables);
//    }

//    /**
//     * 任务前进一步
//     * @param currentTaskEntity 任务
//     * @param variables 变量集合
//     */
//    void taskForward(TaskEntity currentTaskEntity, Map<String, Object> variables) {
//        ActivityImpl activity = (ActivityImpl) ProcessDefUtils.getActivity(processEngine,
//                currentTaskEntity.getProcessDefinitionId(), currentTaskEntity.getTaskDefinitionKey())
//                .getOutgoingTransitions().searchById(0).getDestination();
//
//        this.taskJump(currentTaskEntity, activity, variables);
//    }

//    /**
//     * 任务后退一步
//     * @param processInstanceId 流程实例ID
//     * @param variables 任务变量集合
//     */
//    public void taskBack(String processInstanceId, Map<String, Object> variables) {
//        this.taskBack(this.searchCurrentTask(processInstanceId), variables);
//    }

//    /**
//     * 任务后退一步
//     * @param currentTaskEntity 任务
//     * @param variables 变量集合
//     */
//    void taskBack(TaskEntity currentTaskEntity, Map<String, Object> variables) {
//        ActivityImpl activity = (ActivityImpl) ProcessDefUtils.getActivity(processEngine,
//                currentTaskEntity.getProcessDefinitionId(), currentTaskEntity.getTaskDefinitionKey())
//                .getIncomingTransitions().searchById(0).getSource();
//        this.taskJump(currentTaskEntity, activity, variables);
//    }

//    /**
//     * 跳转（包括回退和向前）至指定活动节点
//     * @param processInstanceId 流程实例ID
//     * @param variables 任务变量集合
//     */
//    public void currentTaskJump(String processInstanceId, String targetTaskDefinitionKey, Map<String, Object> variables) {
//        this.taskJump(this.searchCurrentTask(processInstanceId), targetTaskDefinitionKey, variables);
//    }

//    /**
//     * 跳转（包括回退和向前）至指定活动节点
//     * @param currentTaskId 当前任务ID
//     * @param targetTaskDefinitionKey 目标任务节点（在模型定义里面的节点名称）
//     * @param variables 变量集合
//     */
//    public void taskJump(String currentTaskId, String targetTaskDefinitionKey, Map<String, Object> variables) {
//        this.taskJump(this.searchTaskEntity(currentTaskId), targetTaskDefinitionKey, variables);
//    }

//    /**
//     * 跳转（包括回退和向前）至指定活动节点
//     * @param currentTaskEntity 当前任务节点
//     * @param targetTaskDefinitionKey 目标任务节点（在模型定义里面的节点名称）
//     */
//    private void taskJump(TaskEntity currentTaskEntity, String targetTaskDefinitionKey, Map<String, Object> variables) {
//        ActivityImpl activity = ProcessDefUtils.getActivity(processEngine, currentTaskEntity.getProcessDefinitionId(),
//                targetTaskDefinitionKey);
//        this.taskJump(currentTaskEntity, activity, variables);
//    }

//    /**
//     * 跳转（包括回退和向前）至指定活动节点
//     * @param currentTaskEntity 当前任务节点
//     * @param targetActivity 目标任务节点（在模型定义里面的节点名称）
//     * @param variables     变量集合
//     */
//    private void taskJump(TaskEntity currentTaskEntity, ActivityImpl targetActivity, Map<String, Object> variables) {
//        CommandExecutor commandExecutor = ((RuntimeServiceImpl) runtimeService).getCommandExecutor();
//        commandExecutor.execute(new JumpTaskCmd(currentTaskEntity, targetActivity, variables));
//    }

//    /**
//     * 根据ID搜索任务
//     * @param taskId 任务ID
//     * @return 任务
//     */
//    private Task searchTask(String taskId) {
//        return taskService.createTaskQuery().taskId(taskId).singleResult();
//    }

//    /**
//     * 根据ID搜索任务实体
//     * @param taskId 任务ID
//     * @return 任务实体
//     */
//    private TaskEntity searchTaskEntity(String taskId) {
//        return (TaskEntity) this.searchTask(taskId);
//    }

//     /**
//     * 委派任务
//     * @param taskId 任务ID
//     * @param userId 被委派人
//     */
//     public void delegateTask(String taskId, String userId){
//        taskService.delegateTask(taskId, userId);
//     }

//     /**
//     * 被委派人完成任务
//     * @param taskId 任务ID
//     */
//     public void resolveTask(String taskId){
//        taskService.resolveTask(taskId);
//     }

//    /**
//     * 删除任务
//     * @param taskId 任务ID
//     * @param deleteReason 删除原因
//     */
//    public void deleteTask(String taskId, String deleteReason) {
//        taskService.deleteTask(taskId, deleteReason);
//    }

//    /**
//     * 设置任务的办理人
//     * @param task  任务
//     * @param userId  办理人ID
//     */
//    public void setTaskAssignee(String userId, Task task) {
//        task.setAssignee(userId);
//        taskService.saveTask(task);
//    }

//    /**
//     * 添加任务候选人
//     * @param taskId    任务
//     * @param userId    候选人ID
//     */
//    public void addCandidateUser(String taskId, String userId) {
//        taskService.addCandidateUser(taskId, userId);
//    }

//    /**
//     * 后加签
//     * @param processDefinitionId       流程定义ID
//     * @param processInstanceId         流程实例ID
//     * @param targetTaskDefinitionKey   增加任务定义KEY
//     * @param variables 变量集合
//     * @param assignees 审批人
//     */
//     @SuppressWarnings("unchecked")
//     public ActivityImpl[] appendTask(String processDefinitionId, String processInstanceId,
//                                      String targetTaskDefinitionKey, Map<String, Object> variables, String... assignees) {
//
//         List<String> assigneeList = new ArrayList<>();
//         assigneeList.add(Authentication.getAuthenticatedUserId());
//         assigneeList.addAll(CollectionUtils.arrayToList(assignees));
//         String[] newAssignees = assigneeList.toArray(new String[0]);
//
//         ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(processDefinitionId);
//         ActivityImpl prototypeActivity = workflowProcessService.getActivity(processDefinition.getId(), targetTaskDefinitionKey);
//         return this.cloneAndMakeChain(processDefinition, processInstanceId, targetTaskDefinitionKey,
//         prototypeActivity.getOutgoingTransitions().searchById(0).getDestination().getId(), variables, newAssignees);
//     }

//    /**
//     * 前加签
//     * @param processDefinitionId       流程定义ID
//     * @param processInstanceId         流程实例ID
//     * @param targetTaskDefinitionKey   增加任务定义KEY
//     * @param variables 变量集合
//     * @param assignees 审批人
//     */
//     public ActivityImpl[] insertTask(String processDefinitionId, String processInstanceId,
//                                      String targetTaskDefinitionKey, Map<String, Object> variables, String... assignees) {
//         ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(processDefinitionId);
//         return this.cloneAndMakeChain(processDefinition, processInstanceId, targetTaskDefinitionKey,
//         targetTaskDefinitionKey, variables, assignees);
//     }

//    /**
//     * 分裂某节点为多实例节点
//     * @param processDefinitionId       流程定义ID
//     * @param processInstanceId         流程实例ID
//     * @param targetTaskDefinitionKey   增加任务定义KEY
//     * @param variables 变量集合
//     * @param assignees 审批人
//     */
//     public ActivityImpl splitTask(String processDefinitionId, String processInstanceId,
//                                   String targetTaskDefinitionKey, Map<String, Object> variables, String... assignees) {
//        return splitTask(processDefinitionId, processInstanceId, targetTaskDefinitionKey, variables, true, assignees);
//     }

//    /**
//     * 分裂某节点为多实例节点
//     */
//    @SuppressWarnings("unchecked")
//    public ActivityImpl splitTask(String processDefinitionId, String processInstanceId,
//                                   String targetTaskDefinitionKey, Map<String, Object> variables,
//                                   boolean isSequential, String... assignees) {
//
//        SimpleRuntimeActivityDefinitionEntity info = new SimpleRuntimeActivityDefinitionEntity();
//        info.setProcessDefinitionId(processDefinitionId);
//        info.setProcessInstanceId(processInstanceId);
//
//        RuntimeActivityDefinitionEntityIntepreter intepreter = new RuntimeActivityDefinitionEntityIntepreter(info);
//
//        intepreter.setPrototypeActivityId(targetTaskDefinitionKey);
//        intepreter.setAssignees(CollectionUtils.arrayToList(assignees));
//        intepreter.setSequential(isSequential);
//
//        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(processDefinitionId);
//        ActivityImpl clone = new MultiInstanceActivityCreator().createActivities(processEngine, processDefinition, info)[0];
//
//        TaskEntity currentTaskEntity = this.searchCurrentTask(processInstanceId);
//
//        CommandExecutor commandExecutor = ((RuntimeServiceImpl)runtimeService).getCommandExecutor();
//        commandExecutor.execute(new CreateAndTakeTransitionCmd(currentTaskEntity, clone, variables));
//
//        // recordActivitiesCreation(info);
//        return clone;
//    }

//    @SuppressWarnings("unchecked")
//    private ActivityImpl[] cloneAndMakeChain(ProcessDefinitionEntity processDefinition, String processInstanceId,
//                                             String prototypeActivityId, String nextActivityId,
//                                             Map<String, Object> variables, String... assignees) {
//        SimpleRuntimeActivityDefinitionEntity info = new SimpleRuntimeActivityDefinitionEntity();
//        info.setProcessDefinitionId(processDefinition.getId());
//        info.setProcessInstanceId(processInstanceId);
//
//        RuntimeActivityDefinitionEntityIntepreter intepreter = new RuntimeActivityDefinitionEntityIntepreter(info);
//        intepreter.setPrototypeActivityId(prototypeActivityId);
//        intepreter.setAssignees(CollectionUtils.arrayToList(assignees));
//        intepreter.setNextActivityId(nextActivityId);
//
//        ActivityImpl[] activities = new ChainedActivitiesCreator().createActivities(processEngine, processDefinition, info);
//
//        this.taskJump(processInstanceId, activities[0].getId(), variables);
////         recordActivitiesCreation(info);
//
//        return activities;
//    }

}
