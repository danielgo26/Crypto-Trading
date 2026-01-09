package com.trading212.cryptocurrencytrading.trader.controller;

import com.trading212.cryptocurrencytrading.trader.model.Trader;
import com.trading212.cryptocurrencytrading.trader.service.TraderService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/traders")
public class TraderController {

    private final TraderService traderService;

    public TraderController(TraderService traderService) {
        this.traderService = traderService;
    }

    @GetMapping
    public List<Trader> getAllTraders() {
        return traderService.getAllTraders();
    }

    @GetMapping("/{id}")
    public Trader getTraderById(@PathVariable Long id) {
        return traderService.getTraderById(id);
    }

    @PostMapping
    public Trader createTrader(@RequestBody Trader trader) {
        return traderService.createTrader(trader);
    }

    @PutMapping("/{id}")
    public Trader editTrader(@PathVariable Long id, @RequestBody Trader updatedTrader) {
        return traderService.updateTrader(id, updatedTrader);
    }

    @PostMapping("/{id}/reset")
    public Trader resetTraderAccount(@PathVariable Long id) {
        return traderService.resetTraderAccount(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTrader(@PathVariable Long id) {
        traderService.deleteTrader(id);
    }

}