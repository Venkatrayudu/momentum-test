package com.momentum.momentumtest.model;

import jakarta.validation.constraints.NotBlank;

public class WithdrawalRequest {

    @NotBlank
    private int investorId;

    @NotBlank
    private String productType;

    @NotBlank
    private Long withdrawalAmount;

    public int getInvestorId() {
        return investorId;
    }

    public void setInvestorId(int investorId) {
        this.investorId = investorId;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Long getWithdrawalAmount() {
        return withdrawalAmount;
    }

    public void setWithdrawalAmount(Long withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
    }
}
