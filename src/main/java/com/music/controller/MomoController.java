package com.music.controller;

import com.mservice.allinone.models.QueryStatusTransactionResponse;
import com.music.business.account.IAccountDAO;
import com.music.config.MoMoService;
import com.music.entity.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Controller
public class MomoController {

    @Autowired
    private MoMoService moMoService;

    @Autowired
    private IAccountDAO accountDAO;
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
            
            if (Integer.parseInt(errorCode)==0) {
                Account a=accountDAO.getLogingAccount();
                a.setVip(true);
                accountDAO.save(a);
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
            put("extraData", partnerCode);
            put("signature", partnerCode);
        }};
    }
}