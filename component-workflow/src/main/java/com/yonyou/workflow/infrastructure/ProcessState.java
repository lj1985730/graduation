package com.yonyou.workflow.infrastructure;

/**
 * 流程状态枚举
 * @author Liu Jun at 2017-09-28 16:29:01
 * @since v1.0.0
 */
public enum ProcessState {
    NOT_BOUND,    //未绑定流程
    START,       //开始
    PENDING,    //审核中
    WAITING,     //等待定时触发
    SUSPENDED,  //挂起
    REJECTED,    //被驳回
    RETRY,       //再次提交
    FINISHED,   //结束
    UNKNOWN     //未知状态
}
