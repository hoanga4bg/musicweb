package com.music;

import java.util.ArrayList;
import java.util.Calendar;
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

import com.cloudinary.Cloudinary;
import com.cloudinary.SingletonManager;
import com.cloudinary.utils.ObjectUtils;
import com.music.apiori.AssociationRule;
import com.music.apiori.ItemSet;
import com.music.apiori.ItemSetCollection;
import com.music.business.account.IAccountDAO;
import com.music.business.ranking.IRankingDAO;
import com.music.business.region.IRegionDAO;
import com.music.entity.Account;
import com.music.entity.Favorite;
import com.music.entity.PlayList;
import com.music.entity.Region;
import com.music.entity.Rule;
import com.music.entity.SongInPlayList;
import com.music.repository.RankingTableRepository;
import com.music.repository.RuleRepository;
import com.music.repository.SongRankRepository;
import com.music.repository.SongRepository;

@SpringBootApplication
public class MusicWebApplication {
	@Autowired
	private IAccountDAO accountDAO;
	
	@Autowired
	private RuleRepository ruleRepo;
	
	@Autowired
	public JavaMailSender emailSender;
	
	@Autowired
	private IRegionDAO regionDAO;
	@Autowired
	private IRankingDAO rankingDAO;

	
	public static void main(String[] args) {
		Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "hoangmusic", // insert here you cloud name
                "api_key", "133884213989554", // insert here your api code
                "api_secret", "Hdew73QFDi47_eCnC-pjCg9cH3I")); // insert here your api secret
	    SingletonManager manager = new SingletonManager();
	    manager.setCloudinary(cloudinary);
	    manager.init();
		SpringApplication.run(MusicWebApplication.class, args);
	}

	
	@Scheduled(cron = "0 0 0 ? * MON")
	@Transactional
	public List<Rule> apiori(){
		ruleRepo.deleteAll();
		List<Account> list=accountDAO.findAll();
		ItemSetCollection itemCol=new ItemSetCollection();
		//lay bai hat trong cac playlist
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
			if(a.getListFavor().size()>0) {
				ItemSet itemFavor=new ItemSet();
				for(Favorite favor:a.getListFavor()) {
					itemFavor.add(favor.getSong().getId()+"");
				}
				itemCol.add(itemFavor);
			}
		}
		
		
		AssociationRule as = new AssociationRule();
		List<AssociationRule> listAss = new ArrayList<AssociationRule>();
		//System.out.println(as.FindingLargeItemset(itemCol, 0.15));
		listAss = as.assRule(itemCol, as.FindingLargeItemset(itemCol, 0.15), 10);
		List<Rule> rules=new ArrayList<>();
		for(AssociationRule ass:listAss) {
			String x="";
			for(String a:ass.getX()) {
				x+=(a+",");
			}
			String y="";
			for(String a:ass.getY()) {
				y+=(a+",");
			}
			Rule temp=new Rule();
			temp.setX(x);
			temp.setY(y);
			rules.add(temp);
		}
		ruleRepo.saveAll(rules);
		return rules;
	}
	
	@Scheduled(cron = "0 0 0 1 * ?")
	@Transactional
	public void createRankTable() {
		List<Region> list=regionDAO.findAll();
		for(Region r:list) {
			Date d=new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(d);
			Boolean check=false;
			int month=calendar.get(Calendar.MONTH);
			int year=calendar.get(Calendar.YEAR);
			if(month>0) {
				check=rankingDAO.existMonthRank(month,year, r);
			}
			else {
				check=rankingDAO.existMonthRank(12,year-1, r);
			}
			System.out.println("created new table");
			if(check==false){
				if(month>0) {
					rankingDAO.createRegionRankingTable(r,month,year);
				}
				else {
					rankingDAO.createRegionRankingTable(r,12,year-1);
				}
			}
			
		}
		
	}
}

@Configuration
@EnableScheduling
@ConditionalOnProperty(name="scheduling.enabled",matchIfMissing = true)
class SchedulingConfiguration{
	
}
