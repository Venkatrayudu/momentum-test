package com.momentum.momentumtest.service.impl;

import com.momentum.momentumtest.exception.InvestorNotFoundException;
import com.momentum.momentumtest.model.InvestorProduct;
import com.momentum.momentumtest.model.WithdrawalRequest;
import com.momentum.momentumtest.repo.InvestorDetailsRepo;
import com.momentum.momentumtest.service.InvestorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class InverstorServiceImplTest {

    @Autowired
    InvestorService investorService;

    @Autowired
    InvestorDetailsRepo investorDetailsRepo;

    @Test
    public void testGetInvestorDetails() {
        try {
            Assertions.assertEquals(111, investorService.getInvestorDetails("111").getId());
        } catch (InvestorNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetInvestorProducts() {
        Assertions.assertEquals(2, investorService.getInvestorProducts("111").size());
    }

    @Test
    /*
      This method checks the balance of the investment product after withdrawal if the withdrawal has been done correctly.
      This method has to pass all the validations to run successfully.
     */
    public void testProcessInvestorWithdrawal() {
        WithdrawalRequest withdrawalRequest = new WithdrawalRequest();
        withdrawalRequest.setInvestorId(111);
        withdrawalRequest.setProductType("RETIREMENT");
        withdrawalRequest.setWithdrawalAmount(5000L);

        InvestorProduct investorProductBeforeWithdraw = getInvestmentProductByInvestorIdAndProductType(withdrawalRequest.getInvestorId(), withdrawalRequest.getProductType());
        try {
            investorService.processInvestorWithdrawal(withdrawalRequest);
        } catch (InvestorNotFoundException e) {
            e.printStackTrace();
        }

        InvestorProduct investorProductAfterWithdraw = getInvestmentProductByInvestorIdAndProductType(withdrawalRequest.getInvestorId(), withdrawalRequest.getProductType());
        Assertions.assertEquals(investorProductBeforeWithdraw.getCurrentBalance() - withdrawalRequest.getWithdrawalAmount(), investorProductAfterWithdraw.getCurrentBalance());
    }

    private InvestorProduct getInvestmentProductByInvestorIdAndProductType(int investorId, String productType) {
        return investorService.getInvestorProducts(String.valueOf(investorId))
                .stream()
                .filter(x -> x.getProductType().equalsIgnoreCase(productType))
                .toList()
                .get(0);
    }
}
