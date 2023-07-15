package com.momentum.momentumtest.config;

import com.momentum.momentumtest.domain.WithdrawEvent;
import com.momentum.momentumtest.domain.WithdrawState;
import com.momentum.momentumtest.repo.WithdrawalRepository;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@EnableStateMachineFactory
public class StateMachineConfig extends StateMachineConfigurerAdapter<WithdrawState, WithdrawEvent> {

    @Override
    public void configure(StateMachineStateConfigurer<WithdrawState, WithdrawEvent> states) throws Exception {
        states.withStates()
                .initial(WithdrawState.STARTED)
                .state(WithdrawState.EXECUTING)
                .end(WithdrawState.DONE);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<WithdrawState, WithdrawEvent> transitions) throws Exception {
        transitions.withExternal()
                .source(WithdrawState.STARTED).target(WithdrawState.EXECUTING).event(WithdrawEvent.EXECUTE).and()
                .withExternal()
                .source(WithdrawState.EXECUTING).target(WithdrawState.DONE).event(WithdrawEvent.WITHDRAW);
    }
}
