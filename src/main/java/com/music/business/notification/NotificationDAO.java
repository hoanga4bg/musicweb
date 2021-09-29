package com.music.business.notification;

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

	//Lay cac bao cao chua dc xu ly
	@Override
	public List<Notification> findAllUnCheckNoti(Account account) {
		List<Notification> list=notiRepo.findByAccountAndChecked(account,false);
		return list;
	}

	@Override
	public Notification findById(Long id) {
		return notiRepo.findOneById(id);
	}

}
