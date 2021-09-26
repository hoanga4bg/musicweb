package com.music.business.account;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.music.config.MyUserDetails;
import com.music.entity.Account;
import com.music.repository.AccountRepository;

@Service
public class AccountDAO implements IAccountDAO{
	
	@Autowired
	private AccountRepository accountRepo;
	@Override
	public List<Account> findAll() {
		
		return accountRepo.findAll();
	}



	@Override
	public List<Account> getBlock() {
		// TODO Auto-generated method stub
		return accountRepo.findByStatus(false);
	}

	@Override
	public Account getLogingAccount() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username=((MyUserDetails) principal).getUsername() ;
		if(username.equals("")||username==null) {
			return null;
		}
		Account account=accountRepo.findOneByUsername(username);
		return account;
	}

	@Override
	public Account findByUsername(String username) {
		return accountRepo.findOneByUsername(username);
	}

	@Override
	public Account findByEmail(String email) {
		// TODO Auto-generated method stub
		return accountRepo.findOneByEmail(email);
	}

	@Override
	public Account findById(Long id) {
		
		return accountRepo.findOneById(id);
	}

	@Override
	public void save(Account account) {
		accountRepo.save(account);
		
	}

	@Override
	public List<Account> getVipActive() {
		return accountRepo.findByStatusAndVip(true,true);
	}

	@Override
	public List<Account> getNormalActive() {
		// TODO Auto-generated method stub
		return accountRepo.findByStatusAndVip(true,false);
	}

}
