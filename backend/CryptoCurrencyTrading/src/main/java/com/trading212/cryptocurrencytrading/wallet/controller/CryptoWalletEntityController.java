package com.trading212.cryptocurrencytrading.wallet.controller;

import com.trading212.cryptocurrencytrading.wallet.model.CryptoWalletEntity;
import com.trading212.cryptocurrencytrading.wallet.service.CryptoWalletEntityService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/traders/{id}/crypto-wallet")
public class CryptoWalletEntityController {

    private final CryptoWalletEntityService cryptoWalletEntityService;

    public CryptoWalletEntityController(CryptoWalletEntityService cryptoWalletEntityService) {
        this.cryptoWalletEntityService = cryptoWalletEntityService;
    }

    @GetMapping
    public Map<String, CryptoWalletEntity> getAllTraderCryptoWalletEntities(@PathVariable Long id) {
        return cryptoWalletEntityService.getAllCryptoWalletEntities(id);
    }

}