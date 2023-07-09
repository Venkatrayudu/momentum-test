package com.momentum.momentumtest.service.impl;

import com.momentum.momentumtest.model.InvestorDetails;
import com.momentum.momentumtest.model.InvestorProduct;
import com.momentum.momentumtest.model.WithdrawalRequest;
import com.momentum.momentumtest.repo.InvestorDetailsRepo;
import com.momentum.momentumtest.repo.InvestorProductsRepo;
import com.momentum.momentumtest.service.InvestorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class InvestorServiceImpl implements InvestorService {

    @Autowired
    private InvestorDetailsRepo detailsRepo;

    @Autowired
    private InvestorProductsRepo productsRepo;

    @Override
    public InvestorDetails getInvestorDetails(String id) {
        return detailsRepo.findById(Long.valueOf(id)).get();
    }

    @Override
    public List<InvestorProduct> getInvestorProducts(String investorId) {
        return productsRepo.findProductsByInvestorId(Long.valueOf(investorId));
    }

    @Override
    public String processInvestorWithdrawal(WithdrawalRequest withdrawalRequest) {
        InvestorProduct investorProduct = productsRepo.findProductsByInvestorId(Long.valueOf(withdrawalRequest.getInvestorId()))
                .stream()
                .filter(product -> product.getProductType().equalsIgnoreCase(withdrawalRequest.getProductType()))
                .collect(Collectors.toList()).get(0);
        Long withdrawalAmount = withdrawalRequest.getWithdrawalAmount();
        Long currentBalance = investorProduct.getCurrentBalance();
        String investorId = investorProduct.getInvestorId().toString();
        long ageDiffInYears = 0;
        try {
            Date dob = new SimpleDateFormat("dd-MM-yyyy").parse(getInvestorDetails(investorId).getDateOfBirth());
            long ageDiffInMillies = Math.abs(System.currentTimeMillis() - dob.getTime());
            ageDiffInYears = TimeUnit.DAYS.convert(ageDiffInMillies, TimeUnit.MILLISECONDS) / 365;
        } catch (ParseException e) {
            System.err.println("Error in parsing the date of birth: " + e.getMessage());
            return "Error in getting the date of birth of Investor.";
        }

        if (withdrawalAmount > currentBalance)
            return "Withdrawal Amount cannot be greater than Available balance.";
        if (withdrawalAmount > (currentBalance * 0.9))
            return "You cannot withdraw more than 90% of the Available balance.";
        if (withdrawalRequest.getProductType().equalsIgnoreCase("RETIREMENT") && ageDiffInYears <= 65)
            return "Your age must be more than 65 to withdraw from your RETIREMENT funds.";

        investorProduct.setCurrentBalance(currentBalance - withdrawalAmount);
        productsRepo.save(investorProduct);
        return "success";
    }
}
