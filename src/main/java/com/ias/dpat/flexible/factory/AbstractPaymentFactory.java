package com.ias.dpat.flexible.factory;

import com.ias.dpat.flexible.strategy.PaymentStrategy;
import com.ias.dpat.flexible.types.PaymentType;

public abstract class AbstractPaymentFactory {
    public abstract PaymentStrategy createPaymentStrategy(PaymentType paymentType);
}
