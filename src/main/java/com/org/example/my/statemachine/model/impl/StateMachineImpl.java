package com.org.example.my.statemachine.model.impl;

import com.org.example.my.statemachine.Debugger;
import com.org.example.my.statemachine.StateMachineException;
import com.org.example.my.statemachine.model.State;
import com.org.example.my.statemachine.model.StateMachine;
import com.org.example.my.statemachine.model.Transition;
import com.org.example.my.statemachine.model.Visitor;

import java.util.Map;
import java.util.Optional;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/9/12 5:58 下午
 * @description：状态机实现类
 */
public class StateMachineImpl<S, E, C> implements StateMachine<S, E, C> {

    private String machineId;

    private final Map<S, State<S,E,C>> stateMap;

    private boolean ready;

    public StateMachineImpl(Map<S, State<S, E, C>> stateMap) {
        this.stateMap = stateMap;
    }

    @Override
    public S fireEvent(S sourceStateId, E event, C ctx) {
        isReady();
        State sourceState = getState(sourceStateId);
        return doTransition(sourceState, event, ctx).getId();
    }

    private State getState(S currentStateId) {
        State state = StateHelper.getState(stateMap, currentStateId);
        if (null == state) {
            throw new StateMachineException(currentStateId + " is not found, please check state machine");
        }
        return state;
    }

    private State<S, E, C> doTransition(State sourceState, E event, C ctx) {
        Optional<Transition<S, E, C>> transition = sourceState.getTransition(event);
        if (transition.isPresent()) {
            return transition.get().transit(ctx);
        }
        Debugger.debug("There is no Transition for " + event);
        return sourceState;
    }

    @Override
    public String getMachineId() {
        return machineId;
    }

    @Override
    public void showStateMachine() {
        SysOutVisitor sysOutVisitor = new SysOutVisitor();
        accept(sysOutVisitor);
    }

    @Override
    public String generatePlantUML(){
        PlantUMLVisitor plantUMLVisitor = new PlantUMLVisitor();
        return accept(plantUMLVisitor);
    }

    @Override
    public String accept(Visitor visitor) {
        StringBuilder sb = new StringBuilder();
        sb.append(visitor.visitOnEntry(this));
        for(State state: stateMap.values()){
            sb.append(state.accept(visitor));
        }
        sb.append(visitor.visitOnExit(this));
        return sb.toString();
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    private void isReady() {
        if(!ready){
            throw new StateMachineException("State machine is not built yet, can not work");
        }
    }
}
