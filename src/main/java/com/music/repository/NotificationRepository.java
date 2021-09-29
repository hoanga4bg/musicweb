package com.music.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.music.entity.Account;
import com.music.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification,Long>{

	List<Notification> findAllByAccount(Account account);

	List<Notification> findByAccountAndChecked(Account account, boolean checked);

	Notification findOneById(Long id);
	
}
