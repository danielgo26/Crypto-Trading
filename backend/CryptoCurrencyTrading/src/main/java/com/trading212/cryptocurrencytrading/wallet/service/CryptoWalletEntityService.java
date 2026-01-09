package com.trading212.cryptocurrencytrading.wallet.service;

import com.trading212.cryptocurrencytrading.wallet.model.CryptoWalletEntity;
import com.trading212.cryptocurrencytrading.wallet.repository.CryptoWalletEntitiesRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CryptoWalletEntityService {

    private final CryptoWalletEntitiesRepository cryptoWalletEntitiesRepository;

    public CryptoWalletEntityService(CryptoWalletEntitiesRepository cryptoWalletEntitiesRepository) {
        this.cryptoWalletEntitiesRepository = cryptoWalletEntitiesRepository;
    }

    public Long getCryptoWalletEntityId(CryptoWalletEntity cryptoWalletEntity) {
        return cryptoWalletEntitiesRepository.getCryptoWalletEntityId(cryptoWalletEntity);
    }

    public Map<String, CryptoWalletEntity> getAllCryptoWalletEntities(Long traderId) {
        return cryptoWalletEntitiesRepository.getAllCryptoWalletEntities(traderId);
    }

    public CryptoWalletEntity createCryptoWalletEntity(CryptoWalletEntity cryptoWalletEntity) {
        return cryptoWalletEntitiesRepository.save(cryptoWalletEntity);
    }

    public CryptoWalletEntity updateCryptoWalletEntity(CryptoWalletEntity cryptoWalletEntity) {
        return cryptoWalletEntitiesRepository.update(cryptoWalletEntity);
    }

    public void deleteCryptoWalletEntity(CryptoWalletEntity cryptoWalletEntity) {
        cryptoWalletEntitiesRepository.delete(cryptoWalletEntity);
    }

}