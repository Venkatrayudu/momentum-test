package com.momentum.momentumtest.controller;

import com.momentum.momentumtest.model.InvestorDetails;
import com.momentum.momentumtest.model.InvestorProduct;
import com.momentum.momentumtest.model.WithdrawalRequest;
import com.momentum.momentumtest.service.InvestorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/investor")
public class InvestorController {

    @Autowired
    private InvestorService investorService;

    @GetMapping(path = "/details/{id}")
    public ResponseEntity<InvestorDetails> getInvestorDetails(@PathVariable("id") String investorId) {
        return ResponseEntity.ok(investorService.getInvestorDetails(investorId));
    }

    @GetMapping(path = "/products/{id}")
    public ResponseEntity<List<InvestorProduct>> getInvestorProducts(@PathVariable("id") String investorId) {
        return ResponseEntity.ok(investorService.getInvestorProducts(investorId));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> investorWithdrawal(@RequestBody WithdrawalRequest withdrawalRequest) {
        String response = investorService.processInvestorWithdrawal(withdrawalRequest);
        if (response.equals("success"))
            return ResponseEntity.ok(response);
        else
            return ResponseEntity.badRequest().body(response);
    }
}
