package com.wallet.WalletApp.service;

import com.wallet.WalletApp.dto.AddMoneyRequest;
import com.wallet.WalletApp.dto.SendMoneyRequest;
import com.wallet.WalletApp.entity.Account;
import com.wallet.WalletApp.entity.Transaction;
import com.wallet.WalletApp.entity.User;
import com.wallet.WalletApp.entity.Wallet;
import com.wallet.WalletApp.repository.AccountRepository;
import com.wallet.WalletApp.repository.TransactionRepository;
import com.wallet.WalletApp.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
//import java.util.UUID;

@Service
public class WalletService {

    private final WalletRepository walletRepo;
    private final AccountRepository accountRepo;
    private final TransactionRepository txRepo;

    public WalletService(WalletRepository w, AccountRepository a, TransactionRepository t) {
        this.walletRepo = w;
        this.accountRepo = a;
        this.txRepo = t;
    }

    public Wallet createWallet(User user) {
        Wallet w = new Wallet();
        w.setUser(user);
        w.setBalance(BigDecimal.ZERO);
        return walletRepo.save(w);
    }

    @Transactional
    public Wallet addMoney(AddMoneyRequest req) {

        Account acc = accountRepo.findById(req.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        Wallet wallet = walletRepo.findById(req.getWalletId())
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        if (acc.getBalance().compareTo(req.getAmount()) < 0) {
            throw new RuntimeException("Insufficient account balance");
        }

        // balances
        acc.setBalance(acc.getBalance().subtract(req.getAmount()));
        wallet.setBalance(wallet.getBalance().add(req.getAmount()));

        accountRepo.save(acc);
        walletRepo.save(wallet);

        // 1️⃣ Save transaction FIRST to get ID
        Transaction tx = new Transaction();
        tx.setSenderAccountId(acc.getId());
        tx.setReceiverWalletId(wallet.getId());
        tx.setAmount(req.getAmount());
        tx.setTransactionType("ADD_MONEY");
        tx.setStatus("SUCCESS");

        tx = txRepo.save(tx); // 🔥 DB generates numeric ID

        // 2️⃣ Generate readable transaction ID
        String txId = "TID" + String.format("%07d", tx.getId());
        tx.setTransactionId(txId);

        txRepo.save(tx); // update with readable ID

        return wallet;
    }

    @Transactional
    public void sendMoney(SendMoneyRequest req) {

        Wallet senderWallet = walletRepo.findById(req.getSenderWalletId())
                .orElseThrow(() -> new RuntimeException("Sender wallet not found"));

        Account receiverAccount = accountRepo
                .findByAccountNumber(req.getReceiverAccountNumber())
                .orElseThrow(() -> new RuntimeException("Receiver account not found"));

        // ❌ Prevent self-transfer
        if (senderWallet.getUser().getId().equals(receiverAccount.getUser().getId())) {
            throw new RuntimeException("You cannot send money to your own account");
        }

        // ❌ Insufficient balance
        if (senderWallet.getBalance().compareTo(req.getAmount()) < 0) {
            throw new RuntimeException("Insufficient wallet balance");
        }

        // ✅ Wallet → Account transfer
        senderWallet.setBalance(senderWallet.getBalance().subtract(req.getAmount()));
        receiverAccount.setBalance(receiverAccount.getBalance().add(req.getAmount()));

        walletRepo.save(senderWallet);
        accountRepo.save(receiverAccount);

        // ✅ Transaction record
        Transaction tx = new Transaction();
        tx.setSenderWalletId(senderWallet.getId());
        tx.setReceiverAccountId(receiverAccount.getId());
        tx.setAmount(req.getAmount());
        tx.setTransactionType("SEND_MONEY");
        tx.setCategory(req.getCategory());
        tx.setPurpose(req.getPurpose());
        tx.setStatus("SUCCESS");

        tx = txRepo.save(tx);
        tx.setTransactionId("TID" + String.format("%07d", tx.getId()));
        txRepo.save(tx);
    }



//    public List<Transaction> history(Long walletId) {
//    }

    public List<Transaction> history(Long walletId) {
        return txRepo.findBySenderWalletIdOrReceiverWalletId(walletId, walletId);
    }

    public Wallet updateMinBalance(Long walletId, BigDecimal minBalance) {

        Wallet wallet = walletRepo.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        wallet.setMinBalance(minBalance);
        wallet.setAlertSent(false); // reset alert if user updates threshold

        return walletRepo.save(wallet);
    }


}
