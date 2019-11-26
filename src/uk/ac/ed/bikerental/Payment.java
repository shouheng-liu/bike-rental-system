package uk.ac.ed.bikerental;

import java.math.BigDecimal;

public class Payment {

    private int orderNumber;
    private BigDecimal deposit;
    private BigDecimal total;
    private boolean depositReturned;

    public Payment(BigDecimal deposit, BigDecimal total, boolean depositReturned) {
        this.deposit = deposit;
        this.total = total;
        this.depositReturned = depositReturned;
        this.orderNumber = Controller.getOrderCount();
        Controller.setOrderCount(this.orderNumber + 1);
    }


}
