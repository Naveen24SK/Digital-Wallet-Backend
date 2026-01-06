package com.wallet.WalletApp.service;

import com.wallet.WalletApp.entity.User;
import com.wallet.WalletApp.entity.Wallet;
import com.wallet.WalletApp.repository.UserRepository;
import com.wallet.WalletApp.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class LowBalanceAlertService {

    private final WalletRepository walletRepo;
    private final EmailService emailService;

    public LowBalanceAlertService(WalletRepository walletRepo, EmailService emailService) {
        this.walletRepo = walletRepo;
        this.emailService = emailService;
    }

    @Scheduled(fixedRate = 12 * 60 * 60 * 1000) // every 12 hrs
    @Transactional
    public void checkLowBalance() {
        List<Wallet> wallets = walletRepo.findAll();

        for (Wallet wallet : wallets) {

            if (wallet.getMinBalance().compareTo(BigDecimal.ZERO) <= 0) continue;

            if (wallet.getBalance().compareTo(wallet.getMinBalance()) < 0 && !wallet.isAlertSent()) {
                emailService.sendLowBalanceAlert(wallet.getUser(), wallet);
                wallet.setAlertSent(true);
            }

            if (wallet.getBalance().compareTo(wallet.getMinBalance()) >= 0) {
                wallet.setAlertSent(false);
            }
        }
    }
}
