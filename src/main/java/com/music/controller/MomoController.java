package com.music.controller;

import com.mservice.allinone.models.QueryStatusTransactionResponse;
import com.music.business.account.AccountDAO;
import com.music.business.account.IAccountDAO;
import com.music.business.pay.IPayDAO;
import com.music.config.MoMoService;
import com.music.entity.Account;
import com.music.entity.PayHistory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;



@CrossOrigin(origins = "*")
@RestController
public class MomoController {

    @Autowired
    private MoMoService moMoService;

    @Autowired
    private IAccountDAO accountDAO;
    
    @Autowired
    private IPayDAO payDAO;
    @RequestMapping(path = "/ok", produces = "application/json",method=RequestMethod.POST)
    public String test() {
    	return "hello";
    }
    @PostMapping("/momo/notifyUrl")
    public HashMap notifyUrl(
            @RequestParam("partnerCode") String partnerCode,
            @RequestParam("accessKey") String accessKey,
            @RequestParam("requestId") String requestId,
            @RequestParam("amount") String amount,
            @RequestParam("orderId") String orderId,
            @RequestParam("transId") String transId,
            @RequestParam("errorCode") String errorCode,
            @RequestParam("message") String message,
            @RequestParam("localMessage") String localMessage,
            @RequestParam("payType") String payType,
            @RequestParam("signature") String signature,
            @RequestParam("extraData") String extraData
    ) {

        try {
            

            QueryStatusTransactionResponse transactionResponse = moMoService.transactionResponse(orderId, requestId);
            String account_id=transactionResponse.getExtraData().trim().split("=")[1];
            if (transactionResponse.getErrorCode()==0) {
            	Account account=accountDAO.findById(Long.parseLong(account_id));
            	long currentDiamond=account.getDiamond();
            	long addDiamond=Long.parseLong(amount);
            	addDiamond=(long) (addDiamond/1000);
            	System.out.println("Đã cộng thêm "+addDiamond);
                account.setDiamond(currentDiamond+addDiamond);
                accountDAO.save(account);
                PayHistory pay=new PayHistory();
                pay.setAccount(account);
                pay.setPayDate(new Date());
                pay.setFee(Float.parseFloat(amount));
                payDAO.save(pay);
            }
        } catch (Exception ignored) {
        }


        return new HashMap<String, String>() {{
            put("partnerCode", partnerCode);
            put("accessKey", accessKey);
            put("requestId", requestId);
            put("orderId", orderId);
            put("errorCode", errorCode);
            put("message", message);
            put("responseTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            put("extraData", extraData);
            put("signature", partnerCode);
        }};
    }
}