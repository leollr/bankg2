package com.g2.bank.service.UserServiceImpl;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userfront.dao.CheckingAccountDao;
import com.userfront.dao.PrimaryAccountDao;
import com.userfront.dao.SavingsAccountDao;
import com.userfront.domain.CheckingAccount;
import com.userfront.domain.PrimaryAccount;
import com.userfront.domain.SavingsAccount;
import com.userfront.domain.Transaction;
import com.userfront.domain.User;
import com.userfront.service.AccountService;
import com.userfront.service.TransactionService;
import com.userfront.service.UserService;

@Service
public class AccountServiceImpl implements AccountService {
	
	private static int nextAccountNumber = 10030200;

    @Autowired
    private PrimaryAccountDao primaryAccountDao;

    @Autowired
    private SavingsAccountDao savingsAccountDao;
    
    @Autowired
    private CheckingAccountDao checkingAccountDao;

    @Autowired
    private UserService userService;
    
    @Autowired
    private TransactionService transactionService;

    public PrimaryAccount createPrimaryAccount() {
        PrimaryAccount primaryAccount = new PrimaryAccount();
        primaryAccount.setAccountBalance(new BigDecimal(0.0));
        primaryAccount.setAccountNumber(accountGen());

        primaryAccountDao.save(primaryAccount);

        return primaryAccountDao.findByAccountNumber(primaryAccount.getAccountNumber());
    }

    public SavingsAccount createSavingsAccount() {
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountBalance(new BigDecimal(0.0));
        savingsAccount.setAccountNumber(accountGen());
        Random random = new Random();
    	int low = 1;
    	int high = 10;
    	int result = random.nextInt(high-low) + low;
    	savingsAccount.setinterestRate(result);

        savingsAccountDao.save(savingsAccount);

        return savingsAccountDao.findByAccountNumber(savingsAccount.getAccountNumber());
    }
    
    public CheckingAccount createCheckingAccount() {
    	CheckingAccount chekingAccount = new CheckingAccount();
    	chekingAccount.setAccountBalance(new BigDecimal(0.0));
    	chekingAccount.setAccountNumber(accountGen());
    	Random random = new Random();
    	double result = 1 + (10 - 1) * random.nextDouble();
    	chekingAccount.setOverdraft(new BigDecimal(result));

    	checkingAccountDao.save(chekingAccount);

        return checkingAccountDao.findByAccountNumber(chekingAccount.getAccountNumber());
    }
    
    public void deposit(String accountType, double amount, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        BigDecimal dAmount = new BigDecimal(amount).abs();
        if (accountType.equalsIgnoreCase("Primary")) {
            PrimaryAccount primaryAccount = user.getPrimaryAccount();
            primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().add(dAmount));
            primaryAccountDao.save(primaryAccount);

            Date date = new Date();

            Transaction primaryTransaction = new Transaction(date, "Deposit to Primary Account", "Account", "Finished", amount, primaryAccount.getAccountBalance(), primaryAccount);
            transactionService.savePrimaryDepositTransaction(primaryTransaction);
            
        } else if (accountType.equalsIgnoreCase("Savings")) {
            SavingsAccount savingsAccount = user.getSavingsAccount();
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(dAmount));
            savingsAccountDao.save(savingsAccount);

            Date date = new Date();
            Transaction savingsTransaction = new Transaction(date, "Deposit to savings Account", "Account", "Finished", amount, savingsAccount.getAccountBalance(), savingsAccount);
            transactionService.saveSavingsDepositTransaction(savingsTransaction);
            
        } else if (accountType.equalsIgnoreCase("Checking")) {
            CheckingAccount checkingAccount = user.getCheckingAccount();
            checkingAccount.setAccountBalance(checkingAccount.getAccountBalance().add(dAmount));
            checkingAccountDao.save(checkingAccount);

            Date date = new Date();
            Transaction checkingTransaction = new Transaction(date, "Deposit to checking Account", "Account", "Finished", amount, checkingAccount.getAccountBalance(), checkingAccount);
            transactionService.saveCheckingDepositTransaction(checkingTransaction);
        }
    }
    
    public void withdraw(String accountType, double amount, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        BigDecimal wAmount = new BigDecimal(amount).abs();
        if (accountType.equalsIgnoreCase("Primary")) {
            PrimaryAccount primaryAccount = user.getPrimaryAccount();
            if(primaryAccount.getAccountBalance().compareTo(wAmount)>=0){
            	primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(wAmount));
                primaryAccountDao.save(primaryAccount);

                Date date = new Date();

                Transaction primaryTransaction = new Transaction(date, "Withdraw from Primary Account", "Account", "Finished", amount, primaryAccount.getAccountBalance(), primaryAccount);
                transactionService.savePrimaryWithdrawTransaction(primaryTransaction);
            }
            
        } else if (accountType.equalsIgnoreCase("Savings")) {
            SavingsAccount savingsAccount = user.getSavingsAccount();
            if(savingsAccount.getAccountBalance().compareTo(wAmount)>=0){
            	savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(wAmount));
                savingsAccountDao.save(savingsAccount);

                Date date = new Date();
                Transaction savingsTransaction = new Transaction(date, "Withdraw from savings Account", "Account", "Finished", amount, savingsAccount.getAccountBalance(), savingsAccount);
                transactionService.saveSavingsWithdrawTransaction(savingsTransaction);
            }
            
            
        }else if (accountType.equalsIgnoreCase("Checking")) {
        	CheckingAccount checkingAccount = user.getCheckingAccount();
        	if(checkingAccount.getOverdraft().add(checkingAccount.getAccountBalance()).compareTo(wAmount)>=0){
        		checkingAccount.setAccountBalance(checkingAccount.getAccountBalance().subtract(wAmount));
            	checkingAccountDao.save(checkingAccount);

                Date date = new Date();
                Transaction checkingTransaction = new Transaction(date, "Withdraw from checking Account", "Account", "Finished", amount, checkingAccount.getAccountBalance(), checkingAccount);
                transactionService.saveCheckingWithdrawTransaction(checkingTransaction);
        	}
        	
        }
    }
    
    private int accountGen() {
        return ++nextAccountNumber;
    }

	

}
