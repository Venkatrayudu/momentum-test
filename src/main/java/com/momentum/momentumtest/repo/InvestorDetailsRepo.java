package com.momentum.momentumtest.repo;

import com.momentum.momentumtest.model.InvestorDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestorDetailsRepo extends JpaRepository<InvestorDetails, Long> {
}
