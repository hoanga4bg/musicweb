package com.music.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.music.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{

	public Account findOneByUsername(String username);

	public List<Account> findByStatus(boolean b);

	public Account findOneByEmail(String email);

	public Account findOneById(Long id);

	public Account findOneByUsernameAndPassword(String username, String password);



}
