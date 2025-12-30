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
    private final UserRepository userRepo;

    public LowBalanceAlertService(
            WalletRepository walletRepo,
            EmailService emailService,
            UserRepository userRepo
    ) {
        this.walletRepo = walletRepo;
        this.emailService = emailService;
        this.userRepo = userRepo;
    }

    @Value("${app.wallet.min-balance:100.0}")
    private BigDecimal minBalance;

    @Scheduled(fixedRate = 3600000)
    @Transactional
    public void checkLowBalances() {

        List<Wallet> wallets =
                walletRepo.findAllByBalanceLessThan(minBalance);

        for (Wallet wallet : wallets) {
            User user = wallet.getUser();

            if (!user.isAlertSent()) {
                emailService.sendLowBalanceAlert(user, wallet);
                user.setAlertSent(true);
                userRepo.save(user);
            }
        }
    }
}
