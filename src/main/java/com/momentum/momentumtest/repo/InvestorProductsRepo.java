package com.momentum.momentumtest.repo;

import com.momentum.momentumtest.model.InvestorProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestorProductsRepo extends JpaRepository<InvestorProduct, Long> {

    @Query(value = "SELECT * FROM investor_products ip WHERE ip.investor_id = ?1", nativeQuery = true)
    List<InvestorProduct> findProductsByInvestorId(Long investorId);
}
