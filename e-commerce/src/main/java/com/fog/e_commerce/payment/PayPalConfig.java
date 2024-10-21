package com.fog.e_commerce.payment;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PayPalConfig {

    private String clientId = "AbWzG8V3_UK0d1Z8h_hJSRYyHCY2FDqtUjvIUrABHA0WDr3jjUWI-NX0JWZvVMnV4NF0ZBJemCYRSAvd";
    private String clientSecret = "ELg1ZLep8BnVTrf2mkeQReZ-ZHAozm6CEcVjPTUQqAugazomgp0XEBhNr-BTGhcJLyPzyIWfeiUX-yZt";
    private String mode = "sandbox"; // or "live" for production

    @Bean
    public Map<String, String> paypalSdkConfig() {
        Map<String, String> configMap = new HashMap<>();
        configMap.put("mode", mode);
        return configMap;
    }

    @Bean
    public OAuthTokenCredential oAuthTokenCredential() {
        return new OAuthTokenCredential(clientId, clientSecret, paypalSdkConfig());
    }

    @Bean
    public APIContext apiContext() throws PayPalRESTException {
        APIContext context = new APIContext(oAuthTokenCredential().getAccessToken());
        context.setConfigurationMap(paypalSdkConfig());
        return context;
    }
}
