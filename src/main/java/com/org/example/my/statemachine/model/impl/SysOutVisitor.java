package com.org.example.my.statemachine.model.impl;

import com.org.example.my.statemachine.model.State;
import com.org.example.my.statemachine.model.StateMachine;
import com.org.example.my.statemachine.model.Transition;
import com.org.example.my.statemachine.model.Visitor;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/9/12 5:25 下午
 * @description：状态机流程生成类
 */
public class SysOutVisitor implements Visitor {

    @Override
    public String visitOnEntry(StateMachine<?, ?, ?> stateMachine) {
        String entry = "-----StateMachine:" + stateMachine.getMachineId() + "-------";
        System.out.println(entry);
        return entry;
    }

    @Override
    public String visitOnExit(StateMachine<?, ?, ?> stateMachine) {
        String exit = "------------------------";
        System.out.println(exit);
        return exit;
    }

    @Override
    public String visitOnEntry(State<?, ?, ?> state) {
        StringBuilder sb = new StringBuilder();
        String stateStr = "State:" + state.getId();
        sb.append(stateStr).append(LF);
        System.out.println(stateStr);
        for (Transition transition : state.getTransitions()) {
            String transitionStr = "    Transition:" + transition;
            sb.append(transitionStr).append(LF);
            System.out.println(transitionStr);
        }
        return sb.toString();
    }

    @Override
    public String visitOnExit(State<?, ?, ?> visitable) {
        return "";
    }
}
