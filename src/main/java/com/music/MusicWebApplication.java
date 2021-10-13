package com.music;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.music.apiori.AssociationRule;
import com.music.apiori.ItemSet;
import com.music.apiori.ItemSetCollection;
import com.music.business.account.IAccountDAO;
import com.music.entity.Account;
import com.music.entity.PlayList;
import com.music.entity.Rule;
import com.music.entity.SongInPlayList;
import com.music.repository.RuleRepository;

@SpringBootApplication

public class MusicWebApplication {
	@Autowired
	private IAccountDAO accountDAO;
	
	@Autowired
	private RuleRepository ruleRepo;
	
	@Autowired
	  public JavaMailSender emailSender;
	private static final Logger LOGGER = LogManager.getLogger(MusicWebApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(MusicWebApplication.class, args);
	}

	
	@Scheduled(cron = "0 0 0 ? * SUN")
	@Transactional
	public List<Rule> apiori(){
		ruleRepo.deleteAll();
		List<Account> list=accountDAO.findAll();
		ItemSetCollection itemCol=new ItemSetCollection();
		for(Account a:list) {
			if(a.getListPlayLists().size()>0) {
				for(PlayList play:a.getListPlayLists()) {
					if(play.getSongInPlayLists().size()>0) {
						List<SongInPlayList> sipList= play.getSongInPlayLists();
						ItemSet item=new ItemSet();
						for(SongInPlayList sip:sipList) {					
							item.add(sip.getSong().getId()+"");
						}
						itemCol.add(item);
					}
				}
			}
		}
		
		
		AssociationRule as = new AssociationRule();
		List<AssociationRule> listAss = new ArrayList<AssociationRule>();
		System.out.println(as.FindingLargeItemset(itemCol, 0.15));
		listAss = as.assRule(itemCol, as.FindingLargeItemset(itemCol, 0.15), 10);
		List<Rule> rules=new ArrayList<>();
		for(AssociationRule ass:listAss) {
			String x="";
			for(String a:ass.getX()) {
				x+=a;
			}
			String y="";
			for(String a:ass.getY()) {
				y+=a;
			}
			Rule temp=new Rule();
			temp.setX(x);
			temp.setY(y);
			rules.add(temp);
		}
		ruleRepo.saveAll(rules);
		return rules;
	}
	
	@Scheduled(cron = "0 * * ? * *")
	public void sendEmail(){
		SimpleMailMessage message = new SimpleMailMessage();
		try {
			LOGGER.info("Sending an email");
			message.setTo("chuadangki678@gmail.com");
			message.setSubject("Test auto");

			message.setText("Hí");
			this.emailSender.send(message);
		} catch (Exception e) {
			LOGGER.warn("Deleting expired AUTH_TOKENS");
		}
	}
}

@Configuration
@EnableScheduling
@ConditionalOnProperty(name="scheduling.enabled",matchIfMissing = true)
class SchedulingConfiguration{
	
}
