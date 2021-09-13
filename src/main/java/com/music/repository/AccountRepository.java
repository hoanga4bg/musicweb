package com.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.music.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{

	public Account findOneByUsername(String username);

}
