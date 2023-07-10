package com.momentum.momentumtest.service;

import com.momentum.momentumtest.exception.InvestorNotFoundException;
import com.momentum.momentumtest.model.InvestorDetails;
import com.momentum.momentumtest.model.InvestorProduct;
import com.momentum.momentumtest.model.WithdrawalRequest;

import java.util.List;

public interface InvestorService {

    InvestorDetails getInvestorDetails(String id) throws InvestorNotFoundException;
    List<InvestorProduct> getInvestorProducts(String id);
    String processInvestorWithdrawal(WithdrawalRequest withdrawalRequest) throws InvestorNotFoundException;
}
