package com.momentum.momentumtest.controller;

import com.momentum.momentumtest.model.InvestorDetails;
import com.momentum.momentumtest.model.InvestorProduct;
import com.momentum.momentumtest.model.WithdrawalRequest;
import com.momentum.momentumtest.service.InvestorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import com.momentum.momentumtest.exception.InvestorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/investor")
public class InvestorController {

    private final Logger logger = LoggerFactory.getLogger(InvestorController.class);

    @Autowired
    private InvestorService investorService;

    @Operation(summary = "Get an investor's details by his/her id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Investor's details found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InvestorDetails.class)) }),
            @ApiResponse(responseCode = "404", description = "Investor's details not found",
                    content = @Content) })
    @GetMapping(path = "/details/{id}")
    public ResponseEntity<InvestorDetails> getInvestorDetails(@PathVariable("id") String investorId) {
        try {
            return ResponseEntity.ok(investorService.getInvestorDetails(investorId));
        } catch (InvestorNotFoundException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Get an investor's investment products by his/her id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Investor's investment products found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InvestorDetails.class)) }) })
    @GetMapping(path = "/products/{id}")
    public ResponseEntity<List<InvestorProduct>> getInvestorProducts(@PathVariable("id") String investorId) {
        return ResponseEntity.ok(investorService.getInvestorProducts(investorId));
    }

    @Operation(summary = "Perform a Withdrawal request for the given amount from the given Investment product of the Investor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Withdrawal request placed successfully.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InvestorDetails.class)) }),
            @ApiResponse(responseCode = "404", description = "Investor's details not found",
                    content = @Content) })
    @PostMapping("/withdraw")
    public ResponseEntity<String> investorWithdrawal(@RequestBody WithdrawalRequest withdrawalRequest) {
        String response;
        try {
            response = investorService.processInvestorWithdrawal(withdrawalRequest);
        } catch (InvestorNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (response.equals("success"))
            return ResponseEntity.ok(response);
        else
            return ResponseEntity.badRequest().body(response);
    }
}
