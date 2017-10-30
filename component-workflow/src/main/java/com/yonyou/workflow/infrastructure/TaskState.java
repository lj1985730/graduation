package com.yonyou.workflow.infrastructure;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 任务状态枚举
 * @author Liu Jun at 2017-09-28 16:29:01
 * @since v1.0.0
 */
public enum TaskState {

    CLAIM("claim"),
    TODO("todo"),       //待办
    FINISH("finish");   //结束

    private String state;

    TaskState(String state) {
        this.state = state;
    }

    @JsonCreator
    public static TaskState getStateEnum(String state){
        for(TaskState item : values()) {
            if(item.getState().equals(state)) {
                return item;
            }
        }
        return null;
    }

    @JsonValue
    public String getState() {
        return state;
    }
}
