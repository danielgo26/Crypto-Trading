package com.trading212.cryptocurrencytrading.trader.service;

import com.trading212.cryptocurrencytrading.exception.NotExistingTraderException;
import com.trading212.cryptocurrencytrading.wallet.model.CryptoWalletEntity;
import com.trading212.cryptocurrencytrading.trader.model.Trader;
import com.trading212.cryptocurrencytrading.transaction.model.Transaction;
import com.trading212.cryptocurrencytrading.wallet.repository.CryptoWalletEntitiesRepository;
import com.trading212.cryptocurrencytrading.trader.repository.TraderRepository;
import com.trading212.cryptocurrencytrading.transaction.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.trading212.cryptocurrencytrading.trader.model.Trader.INITIAL_BALANCE;

@Service
public class TraderService {

    private final TraderRepository traderRepository;
    private final TransactionRepository transactionRepository;
    private final CryptoWalletEntitiesRepository cryptoWalletEntitiesRepository;

    public TraderService(TraderRepository traderRepository,
                         TransactionRepository transactionRepository,
                         CryptoWalletEntitiesRepository cryptoWalletEntitiesRepository) {
        this.traderRepository = traderRepository;
        this.transactionRepository = transactionRepository;
        this.cryptoWalletEntitiesRepository = cryptoWalletEntitiesRepository;
    }

    public Trader getTraderById(Long id) {
        Trader trader = traderRepository.getTraderById(id);
        List<Transaction> transactions = transactionRepository.getAllTransactionsByTraderId(id);

        Map<String, CryptoWalletEntity> cryptoWalletMap =
            cryptoWalletEntitiesRepository.getAllCryptoWalletEntities(trader.getId());

        //load trader extra properties
        trader.setTransactions(transactions);
        trader.setCryptoWallet(cryptoWalletMap);

        return trader;
    }

    public List<Trader> getAllTraders() {
        return traderRepository.getAllTraders()
            .stream()
            .map(tr -> getTraderById(tr.getId()))
            .collect(Collectors.toList());
    }

    public Trader createTrader(Trader trader) {
        return traderRepository.save(trader);
    }

    public Trader updateTrader(Long id, Trader trader) {
        Trader existingTrader = getTraderById(id);
        if (existingTrader == null) {
            throw new NotExistingTraderException("Trader not found! Not existing id: " + id);
        }

        existingTrader.setFirstName(trader.getFirstName());
        existingTrader.setLastName(trader.getLastName());
        existingTrader.setEmail(trader.getEmail());
        existingTrader.setBalance(trader.getBalance());
        existingTrader.setTransactions(trader.getTransactions());

        return traderRepository.update(existingTrader);
    }

    public Trader resetTraderAccount(Long id) {
        Trader existingTrader = getTraderById(id);
        if (existingTrader == null) {
            throw new NotExistingTraderException("Trader not found! Not existing id: " + id);
        }

        existingTrader.setBalance(INITIAL_BALANCE);
        existingTrader.setTransactions(new ArrayList<>());
        existingTrader.setCryptoWallet(new LinkedHashMap<>());

        transactionRepository.deleteAllTraderTransactions(id);
        cryptoWalletEntitiesRepository.deleteAllTraderCryptoWalletEntities(id);

        return updateTrader(id, existingTrader);
    }

    public void deleteTrader(Long id) {
        if (getTraderById(id) == null) {
            throw new NotExistingTraderException("Trader not found! Not existing id: " + id);
        }

        traderRepository.deleteById(id);
    }

}