package com.music.dao.Notification;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.music.entity.Account;
import com.music.entity.Notification;
import com.music.repository.NotificationRepository;

@Service
public class NotificationDAO implements INotificationDAO {
	
	@Autowired
	private NotificationRepository notiRepo;
	@Override
	public void save(Notification noti) {
		notiRepo.save(noti);
		
	}

	@Override
	public void deleteById(Long id) {
		notiRepo.deleteById(id);
		
	}

	@Override
	public List<Notification> findAllByAccount(Account account) {
		List<Notification> list=notiRepo.findAllByAccount(account);
		return list;
	}

}
