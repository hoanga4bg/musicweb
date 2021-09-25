package com.music.config;

import com.mservice.allinone.models.CaptureMoMoResponse;
import com.mservice.allinone.models.QueryStatusTransactionResponse;
import com.mservice.allinone.processor.allinone.CaptureMoMo;
import com.mservice.allinone.processor.allinone.QueryStatusTransaction;
import com.mservice.shared.sharedmodels.Environment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoMoService {
    private final Environment ENV_MOMO = Environment.selectEnv(Environment.EnvTarget.DEV, Environment.ProcessType.PAY_GATE);

    @Autowired
    org.springframework.core.env.Environment env;

    public synchronized CaptureMoMoResponse captureMoMoResponse(String returnUrl) throws Exception {
        String notifyURL = "localhost:8080/momo/notifyUrl";;
        String amount = String.valueOf(200000);
        String orderId = String.valueOf(System.currentTimeMillis());
        String requestId = String.valueOf(System.currentTimeMillis());
        String orderInfo = "Thanh toán đơn hàng: NÂNG CẤP TÀI KHOẢN VIP";

        return CaptureMoMo.process(ENV_MOMO, orderId, requestId, amount, orderInfo, returnUrl, notifyURL, "");
    }

    public String getMoMoPayUrl() {
       
            try {
                String returnUrl ="https://www.facebook.com/";       
                return "redirect:" + captureMoMoResponse(returnUrl).getPayUrl();
            } catch (Exception e) {
                e.printStackTrace();
            }
 
        return null;
    }

    public QueryStatusTransactionResponse transactionResponse(String orderId, String requestId) throws Exception {
        return QueryStatusTransaction.process(ENV_MOMO, orderId, requestId);
    }
}
