package com.wallet.WalletApp.service;

import com.wallet.WalletApp.entity.User;
import com.wallet.WalletApp.entity.Wallet;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void sendLowBalanceAlert(User user, Wallet wallet) {
        // TEMP: console log (replace with JavaMailSender later)
        System.out.println(
                "⚠ LOW BALANCE ALERT → " +
                        user.getEmail() +
                        " | Balance: ₹" + wallet.getBalance()
        );
    }
}
