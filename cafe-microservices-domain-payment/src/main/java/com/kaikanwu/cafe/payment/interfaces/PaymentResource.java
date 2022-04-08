package com.kaikanwu.cafe.payment.interfaces;


import com.kaikanwu.cafe.infrastructure.infrasturcture.response.Response;
import com.kaikanwu.cafe.payment.application.PaymentApplicationService;
import com.kaikanwu.cafe.payment.domain.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentResource {

    @Autowired
    private PaymentApplicationService service;

    /**
     * 支付或者取消
     */
    @PostMapping("/{payId}")
    public Response updatePaymentStatus(@PathVariable String payId, Integer accountId, Payment.Status status) {
        if (status == Payment.Status.PAYED) {
            service.accomplishPayment(accountId, payId);
        } else {
            service.cancelPayment(payId);
        }
        return Response.success();
    }
}
