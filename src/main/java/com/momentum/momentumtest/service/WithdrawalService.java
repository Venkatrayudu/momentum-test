package com.momentum.momentumtest.service;

import com.momentum.momentumtest.domain.WithdrawEvent;
import com.momentum.momentumtest.domain.WithdrawState;
import com.momentum.momentumtest.domain.Withdrawal;
import org.springframework.statemachine.StateMachine;

public interface WithdrawalService {

    Withdrawal execute(Withdrawal withdrawal);
    StateMachine<WithdrawState, WithdrawEvent> withdraw(Long id);
}
