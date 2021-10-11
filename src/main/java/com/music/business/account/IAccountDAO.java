package com.music.business.account;

import java.util.List;

import com.music.entity.Account;

public interface IAccountDAO {
	public List<Account> findAll();
	public List<Account> getVipActive();
	public List<Account> getNormalActive();
	public List<Account> getBlock();
	public Account getLogingAccount();
	public Account findByUsername(String username);
	public Account findByEmail(String email);
	public Account findById(Long id);
	public void save(Account account);
	public void sendEmail(String toEmail,String title,String text);
	public Account findByUsernameAndPassword(String username,String password);
}
