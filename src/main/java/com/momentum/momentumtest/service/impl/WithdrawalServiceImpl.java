package com.momentum.momentumtest.service.impl;

import com.momentum.momentumtest.domain.WithdrawEvent;
import com.momentum.momentumtest.domain.WithdrawState;
import com.momentum.momentumtest.domain.Withdrawal;
import com.momentum.momentumtest.repo.WithdrawalRepository;
import com.momentum.momentumtest.service.WithdrawalService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WithdrawalServiceImpl implements WithdrawalService {

    private final WithdrawalRepository repository;
    private final StateMachineFactory<WithdrawState, WithdrawEvent> stateMachineFactory;

    @Override
    @Transactional
    public Withdrawal execute(Withdrawal withdrawal) {
        withdrawal.setState(WithdrawState.EXECUTING);
        return repository.save(withdrawal);
    }

    @Override
    @Transactional
    public StateMachine<WithdrawState, WithdrawEvent> withdraw(Long id) {
        StateMachine<WithdrawState, WithdrawEvent> sm = build(id);
        sendEvent(id, sm, WithdrawEvent.WITHDRAW);

        return sm;
    }

    private StateMachine<WithdrawState, WithdrawEvent> build(Long id) {
        Withdrawal withdrawal = repository.getOne(id);

        StateMachine<WithdrawState, WithdrawEvent> sm = stateMachineFactory.getStateMachine(String.valueOf(withdrawal.getId()));

        sm.start();

        return sm;
    }

    private void sendEvent(Long id, StateMachine<WithdrawState, WithdrawEvent> sm, WithdrawEvent event) {
        Message msg = MessageBuilder.withPayload(event)
                .setHeader("withdraw_id", id)
                .build();

        sm.sendEvent(msg);
    }
}
