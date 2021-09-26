package com.music.business.notification;

import java.util.List;

import com.music.entity.Account;
import com.music.entity.Notification;

public interface INotificationDAO {
	public void save(Notification noti);
	public void deleteById(Long id);
	public List<Notification> findAllByAccount(Account account);
}
