package com.org.example.my.statemachine.test;

import com.org.example.my.statemachine.StateMachineException;
import com.org.example.my.statemachine.builder.StateMachineBuilder;
import com.org.example.my.statemachine.builder.StateMachineBuilderFactory;
import com.org.example.my.statemachine.model.Action;
import com.org.example.my.statemachine.model.Condition;
import com.org.example.my.statemachine.model.StateMachine;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/9/14 11:58 上午
 * @description：状态机异常情况单元测试
 */
public class StateMachineUnNormalTest {

    @Test
    public void testConditionNotMeet(){
        StateMachineBuilder<StateMachineTest.States, StateMachineTest.Events, StateMachineTest.Context> builder = StateMachineBuilderFactory.create();
        builder.externalTransition()
                .from(StateMachineTest.States.STATE1)
                .to(StateMachineTest.States.STATE2)
                .on(StateMachineTest.Events.EVENT1)
                .when(checkConditionFalse())
                .perform(doAction());

        StateMachine<StateMachineTest.States, StateMachineTest.Events, StateMachineTest.Context> stateMachine = builder.build("NotMeetConditionMachine");
        StateMachineTest.States target = stateMachine.fireEvent(StateMachineTest.States.STATE1, StateMachineTest.Events.EVENT1, new StateMachineTest.Context());
        Assert.assertEquals(StateMachineTest.States.STATE1,target);
    }


    @Test(expected = StateMachineException.class)
    public void testDuplicatedTransition(){
        StateMachineBuilder<StateMachineTest.States, StateMachineTest.Events, StateMachineTest.Context> builder = StateMachineBuilderFactory.create();
        builder.externalTransition()
                .from(StateMachineTest.States.STATE1)
                .to(StateMachineTest.States.STATE2)
                .on(StateMachineTest.Events.EVENT1)
                .when(checkCondition())
                .perform(doAction());

        builder.externalTransition()
                .from(StateMachineTest.States.STATE1)
                .to(StateMachineTest.States.STATE2)
                .on(StateMachineTest.Events.EVENT1)
                .when(checkCondition())
                .perform(doAction());
    }

    @Test(expected = StateMachineException.class)
    public void testDuplicateMachine(){
        StateMachineBuilder<StateMachineTest.States, StateMachineTest.Events, StateMachineTest.Context> builder = StateMachineBuilderFactory.create();
        builder.externalTransition()
                .from(StateMachineTest.States.STATE1)
                .to(StateMachineTest.States.STATE2)
                .on(StateMachineTest.Events.EVENT1)
                .when(checkCondition())
                .perform(doAction());

        builder.build("DuplicatedMachine");
        builder.build("DuplicatedMachine");
    }

    private Condition<StateMachineTest.Context> checkCondition() {
        return (ctx) -> {return true;};
    }

    private Condition<StateMachineTest.Context> checkConditionFalse() {
        return (ctx) -> {return false;};
    }

    private Action<StateMachineTest.States, StateMachineTest.Events, StateMachineTest.Context> doAction() {
        return (from, to, event, ctx)->{
            System.out.println(ctx.operator+" is operating "+ctx.entityId+"from:"+from+" to:"+to+" on:"+event);
        };
    }
}
