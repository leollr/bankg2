package com.g2.bank.service.UserServiceImpl;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userfront.dao.CheckingAccountDao;
import com.userfront.dao.CheckingTransactionDao;
import com.userfront.dao.PrimaryAccountDao;
import com.userfront.dao.RecipientDao;
import com.userfront.dao.SavingsAccountDao;
import com.userfront.dao.TransactionDao;
import com.userfront.domain.PrimaryAccount;
import com.userfront.domain.Recipient;
import com.userfront.domain.SavingsAccount;
import com.userfront.domain.Transaction;
import com.userfront.domain.User;
import com.userfront.service.TransactionService;
import com.userfront.service.UserService;

@Service
public class TransactionServiceImpl implements TransactionService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TransactionDao transactionDao;
	
	@Autowired
	private CheckingTransactionDao checkingTransactionDao;
	
	@Autowired
	private PrimaryAccountDao primaryAccountDao;
	
	@Autowired
	private SavingsAccountDao savingsAccountDao;
	
	@Autowired
	private CheckingAccountDao checkingAccountDao;
	
	@Autowired
	private RecipientDao recipientDao;
	

	public List<Transaction> findTransactionList(String username){
        User user = userService.findByUsername(username);
        List<Transaction> transactionList = user.getPrimaryAccount().getTransactionList();

        return transactionList;
    }

    public List<Transaction> findSavingsTransactionList(String username) {
        User user = userService.findByUsername(username);
        List<Transaction> savingsTransactionList = user.getSavingsAccount().getTransactionList();

        return savingsTransactionList;
    }
    
    
    public List<Transaction> findCheckingTransactionList(String username) {
        User user = userService.findByUsername(username);
        List<Transaction> checkingTransactionList = user.getCheckingAccount().getTransactionList();

        return checkingTransactionList;
    }

    public void savePrimaryDepositTransaction(Transaction primaryTransaction) {
    	transactionDao.save(primaryTransaction);
    }

    public void saveSavingsDepositTransaction(Transaction savingsTransaction) {
    	transactionDao.save(savingsTransaction);
    }
    
    public void saveCheckingDepositTransaction(Transaction checkingTransaction) {
        checkingTransactionDao.save(checkingTransaction);
    }
    
    public void savePrimaryWithdrawTransaction(Transaction primaryTransaction) {
    	transactionDao.save(primaryTransaction);
    }

    public void saveSavingsWithdrawTransaction(Transaction savingsTransaction) {
    	transactionDao.save(savingsTransaction);
    }
    
    public void saveCheckingWithdrawTransaction(Transaction checkingTransaction) {
    	checkingTransactionDao.save(checkingTransaction);
    }
    
    public void betweenAccountsTransfer(String transferFrom, String transferTo, String amount, PrimaryAccount primaryAccount, SavingsAccount savingsAccount) throws Exception {
    	BigDecimal wAmount = new BigDecimal(amount);
    	if (transferFrom.equalsIgnoreCase("Primary") && transferTo.equalsIgnoreCase("Savings")) {
        	 if(primaryAccount.getAccountBalance().compareTo(wAmount)>=0){
        		 primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
                 savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
                 primaryAccountDao.save(primaryAccount);
                 savingsAccountDao.save(savingsAccount);

                 Date date = new Date();

                 Transaction primaryTransaction = new Transaction(date, "Between account transfer from "+transferFrom+" to "+transferTo, "Account", "Finished", Double.parseDouble(amount), primaryAccount.getAccountBalance(), primaryAccount);
                 transactionDao.save(primaryTransaction);
        	}
            
        } else if (transferFrom.equalsIgnoreCase("Savings") && transferTo.equalsIgnoreCase("Primary")) {
        	if(savingsAccount.getAccountBalance().compareTo(wAmount)>=0){
        		primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().add(new BigDecimal(amount)));
                savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
                primaryAccountDao.save(primaryAccount);
                savingsAccountDao.save(savingsAccount);

                Date date = new Date();

                Transaction savingsTransaction = new Transaction(date, "Between account transfer from "+transferFrom+" to "+transferTo, "Transfer", "Finished", Double.parseDouble(amount), savingsAccount.getAccountBalance(), savingsAccount);
                transactionDao.save(savingsTransaction);
        	}
            
        } else {
            throw new Exception("Invalid Transfer");
        }
    }
    
    public List<Recipient> findRecipientList(Principal principal) {
        String username = principal.getName();
        List<Recipient> recipientList = recipientDao.findAll().stream() 			//convert list to stream
                .filter(recipient -> username.equals(recipient.getUser().getUsername()))	//filters the line, equals to username
                .collect(Collectors.toList());

        return recipientList;
    }

    public Recipient saveRecipient(Recipient recipient) {
        return recipientDao.save(recipient);
    }

    public Recipient findRecipientByName(String recipientName) {
        return recipientDao.findByName(recipientName);
    }

    public void deleteRecipientByName(String recipientName) {
        recipientDao.deleteByName(recipientName);
    }
    
    public void toSomeoneElseTransfer(Recipient recipient, String accountType, String amount, PrimaryAccount primaryAccount, SavingsAccount savingsAccount) {
    	BigDecimal wAmount = new BigDecimal(amount).abs();
    	if (accountType.equalsIgnoreCase("Primary")) {
    		if(primaryAccount.getAccountBalance().compareTo(wAmount)>=0){
    			primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
                primaryAccountDao.save(primaryAccount);

                Date date = new Date();

                Transaction primaryTransaction = new Transaction(date, "Transfer to recipient "+recipient.getName(), "Transfer", "Finished", Double.parseDouble(amount), primaryAccount.getAccountBalance(), primaryAccount);
                transactionDao.save(primaryTransaction);
    		}
            
        } else if (accountType.equalsIgnoreCase("Savings")) {
        	if(savingsAccount.getAccountBalance().compareTo(wAmount)>=0){
        		savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
                savingsAccountDao.save(savingsAccount);

                Date date = new Date();

                Transaction savingsTransaction = new Transaction(date, "Transfer to recipient "+recipient.getName(), "Transfer", "Finished", Double.parseDouble(amount), savingsAccount.getAccountBalance(), savingsAccount);
                transactionDao.save(savingsTransaction);
        	}
            
        }
    }
}
