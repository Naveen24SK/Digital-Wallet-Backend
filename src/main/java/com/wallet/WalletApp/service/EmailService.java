package com.wallet.WalletApp.service;

import com.wallet.WalletApp.entity.User;
import com.wallet.WalletApp.entity.Wallet;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendLowBalanceAlert(User user, Wallet wallet) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("⚠️ Low Wallet Balance Alert");
        message.setText(
                "Hello " + user.getUsername() + ",\n\n" +
                        "Your wallet balance is low.\n\n" +
                        "Current Balance: ₹" + wallet.getBalance() + "\n" +
                        "Minimum Balance Set: ₹" + wallet.getMinBalance() + "\n\n" +
                        "Please add money to avoid transaction failure.\n\n" +
                        "- Wallet App Team"
        );

        mailSender.send(message);
    }
}
