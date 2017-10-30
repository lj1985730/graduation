package com.yonyou.workflow.application.controller;

import com.graduation.authentication.util.AuthenticationUtils;
import com.graduation.core.base.dto.JsonResult;
import com.graduation.core.base.dto.Page;
import com.yonyou.workflow.application.service.WorkflowProcessService;
import com.yonyou.workflow.application.service.WorkflowTaskService;
import com.yonyou.workflow.infrastructure.WorkflowVo;
import com.yonyou.workflow.model.WorkflowModel;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.CommentEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 流程个人任务相关Controller
 * @author Liu Jun
 * @version 2017-09-21 13:04:32
 */
@Controller
@RequestMapping(value = "/workflow")
public class WorkflowController {

	@Resource
	private WorkflowProcessService workflowProcessService;

	@Resource
	private WorkflowTaskService workflowTaskService;

	/**
	 * 转到任务中心
	 * @return 任务中心视图
	 */
	@RequestMapping(value = { "/taskCentre" })
	public String taskCentre() {
		return "workflow/taskCentre";
	}

	/**
	 * 获取当前登陆人的待办任务列表
	 * @return 待办任务列表
	 */
	@RequestMapping(value = { "/user/sessionUser/todoTasks" })
	@ResponseBody
	public Page<WorkflowModel> searchUserTodoTasks(HttpSession session, WorkflowVo vo) {
		String accountId = AuthenticationUtils.getAccountId();
		return workflowTaskService.searchTodoPager(vo, accountId);
	}

	/**
	 * 获取当前登陆人的已办任务列表
	 * @return 已办任务列表
	 */
	@RequestMapping(value = { "/user/sessionUser/historicTasks" })
	@ResponseBody
	public Page<WorkflowModel> searchUserHistoricTasks(HttpSession session, WorkflowVo vo) {
		String accountId = AuthenticationUtils.getAccountId();
		return workflowTaskService.searchHistoricTaskPager(vo, accountId);
	}

	/**
	 * 获取流程实例下的历史任务列表
	 * @param processInstanceId 流程实例ID
	 * @return 历史任务列表
	 */
	@RequestMapping(value = {"/processInstances/{processInstanceId}/historicTasks"})
	@ResponseBody
	public List<HistoricTaskInstance> searchHistoicInstanceTasks(@PathVariable String processInstanceId) {
		if(StringUtils.isBlank(processInstanceId)) {
			throw new NullPointerException("流程实例ID");
		}
		return workflowTaskService.searchHistoicTask(processInstanceId);
	}

	/**
	 * 获取任务标注
	 * @param taskId 任务ID
	 * @return 任务标注
	 */
	@RequestMapping(value = {"/tasks/{taskId}/comments"})
	@ResponseBody
	public List<CommentEntity> searchTaskComments(@PathVariable String taskId) {
		if(StringUtils.isBlank(taskId)) {
			throw new NullPointerException("流程实例ID");
		}
		return workflowTaskService.searchTaskComments(taskId);
	}

	/**
	 * 查询流程定义主键
	 * @param processInstanceId 流程实例ID
	 * @return 流程定义主键
	 */
	@RequestMapping(value = "/processInstances/{processInstanceId}/processDefinitionId")
	@ResponseBody
	public String searchInstanceProcessDefinitionId(@PathVariable("processInstanceId") String processInstanceId) {
		if(StringUtils.isBlank(processInstanceId)) {
			throw new NullPointerException("流程实例ID");
		}
		return workflowProcessService.searchProcessDefinitionId(processInstanceId);
	}

    /**
     * 获取流程表单
     * @param processInstanceId 流程实例ID
     * @param taskDefinitionKey 任务定义KEY
     */
    @RequestMapping(value = "/businessFormView")
    public String businessFormView(@RequestParam String processInstanceId, @RequestParam String taskDefinitionKey) {
        return "redirect:" + workflowTaskService.getBusinessFormUrl(processInstanceId, taskDefinitionKey);
    }

	/**
	 * 获取可编辑流程表单
	 * @param processInstanceId 流程实例ID
	 * @param taskDefinitionKey 任务定义KEY
	 */
	@RequestMapping(value = "/editableBusinessFormView")
	public String editableBusinessFormView(@RequestParam String processInstanceId, @RequestParam String taskDefinitionKey) {
		return "redirect:" + workflowTaskService.getEditableBusinessFormUrl(processInstanceId, taskDefinitionKey);
	}

    /**
     * 获取只读流程表单
     * @param processInstanceId 流程实例ID
     * @param taskDefinitionKey 任务定义KEY
     */
    @RequestMapping(value = "/readonlyBusinessFormView")
    public String readonlyBusinessFormView(@RequestParam String processInstanceId, @RequestParam String taskDefinitionKey) {
        return "redirect:" + workflowTaskService.getReadonlyBusinessFormUrl(processInstanceId, taskDefinitionKey);
    }

	/**
	 * 签收任务
	 * @param taskId 任务ID
	 */
	@RequestMapping(value = "/task/{taskId}/claim", method = RequestMethod.PUT)
	@ResponseBody
	public JsonResult claim(HttpSession session, @PathVariable("taskId") String taskId) {
		String accountId = AuthenticationUtils.getAccountId();
		workflowTaskService.claim(taskId, accountId);
		return new JsonResult(true, "签收任务成功！");
	}

	/**
	 * 完成任务
	 * @param vo 业务条件
	 */
	@RequestMapping(value = "/task/{taskId}/pass", method = RequestMethod.PUT)
	@ResponseBody
	public JsonResult passTask(HttpSession session, @PathVariable("taskId") String taskId, @RequestBody WorkflowVo vo) {
		String accountId = AuthenticationUtils.getAccountId();
		workflowTaskService.passTask(taskId, vo.getProcessInstanceId(), accountId, vo.getComment());
		return new JsonResult(true, "审核任务成功！");
	}

	/**
	 * 驳回任务
	 * @param vo 业务条件
	 */
	@RequestMapping(value = "/task/{taskId}/reject", method = RequestMethod.PUT)
	@ResponseBody
	public JsonResult rejectTask(HttpSession session, @PathVariable("taskId") String taskId, @RequestBody WorkflowVo vo) {
		String accountId = AuthenticationUtils.getAccountId();
		workflowTaskService.rejectTask(taskId, vo.getProcessInstanceId(), accountId, vo.getComment());
		return new JsonResult(true, "驳回任务成功！");
	}

	/**
	 * 删除流程实例
	 * @param processInstanceId 流程实例ID
	 * @param vo 参数集合
	 */
	@RequestMapping(value = "/processInstance/{processInstanceId}", method = RequestMethod.DELETE)
	@ResponseBody
	public JsonResult removeProcessInstance(@PathVariable("processInstanceId") String processInstanceId, @RequestBody WorkflowVo vo) {
		workflowProcessService.removeProcessInstance(processInstanceId, vo);
		return new JsonResult(true, JsonResult.DELETE_SUCCEED);
	}

//	/**
//	 * 读取流程实例的带跟踪图片
//	 * @param processInstanceId 流程实例ID
//	 */
//	@RequestMapping(value = "/processInstances/{processInstanceId}/traceImg")
//	public void searchInstanceTraceImg(@PathVariable String processInstanceId, HttpServletResponse response) {
//		if(StringUtils.isBlank(processInstanceId)) {
//			throw new NullArgumentException("流程实例ID");
//		}
//		InputStream imageStream = workflowProcessService.searchTraceImg(processInstanceId);
//		try {
//			IOUtils.copy(imageStream, response.getOutputStream());
//		} catch (IOException e) {
//			throw new IllegalArgumentException("导出图片出错！", e);
//		}
//	}

//	/**
//	 * 输出流程实例的跟踪流程信息
//	 * @param processInstanceId 流程实例ID
//	 * @return 跟踪流程信息
//	 */
//	@RequestMapping(value = "/processInstances/{processInstanceId}/traceInfo")
//	@ResponseBody
//	public List<Map<String, Object>> searchInstanceTraceInfos(@PathVariable("processInstanceId") String processInstanceId) {
//		if(StringUtils.isBlank(processInstanceId)) {
//			throw new NullArgumentException("流程实例ID");
//		}
//		return workflowProcessService.searchTraceInfos(processInstanceId);
//	}

}