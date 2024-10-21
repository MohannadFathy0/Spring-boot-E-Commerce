package com.fog.e_commerce.payment;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/paypal")
public class PayPalController {

    private final PayPalService paypalService;

    public PayPalController(PayPalService paypalService) {
        this.paypalService = paypalService;
    }

    @GetMapping("/pay")
    public RedirectView pay() {
        String cancelUrl = "http://localhost:8080/paypal/cancel";
        String successUrl = "http://localhost:8080/paypal/success";
        try {
            Payment payment = paypalService.createPayment(
                    10.00, "USD", "paypal",
                    "sale", "Payment description",
                    cancelUrl, successUrl);
            for(Links link:payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    return new RedirectView(link.getHref());
                }
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return new RedirectView("/paypal/error");
    }

    @GetMapping("/success")
    public String successPay(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                return "success";
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return "failure";
    }

    @GetMapping("/cancel")
    public String cancelPay() {
        return "cancel";
    }
}