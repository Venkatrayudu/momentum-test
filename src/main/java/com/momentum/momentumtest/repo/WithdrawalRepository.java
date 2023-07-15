package com.momentum.momentumtest.repo;

import com.momentum.momentumtest.domain.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long> {
}
