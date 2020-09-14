package com.org.example.my.statemachine.model.impl;

import com.org.example.my.statemachine.StateMachineException;
import com.org.example.my.statemachine.model.StateMachine;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/9/12 6:01 下午
 * @description：状态机工厂类
 */
public class StateMachineFactory {

    static Map<String, StateMachine> stateMachineMap = new ConcurrentHashMap<>();

    public static <S, E, C> void register(StateMachine<S, E, C> stateMachine) {
        String machineId = stateMachine.getMachineId();
        if (null != stateMachineMap.get(machineId)) {

            throw new StateMachineException("The state machine with id [" + machineId + "] is already built, no need to build again");
        }
        stateMachineMap.put(stateMachine.getMachineId(), stateMachine);
    }

    public static <S, E, C> StateMachine<S, E, C> get(String machineId) {
        StateMachine stateMachine = stateMachineMap.get(machineId);
        if (stateMachine == null) {
            throw new StateMachineException("There is no stateMachine instance for " + machineId + ", please build it first");
        }
        return stateMachine;
    }
}
