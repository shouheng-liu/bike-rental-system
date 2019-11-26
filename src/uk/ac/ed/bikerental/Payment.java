package uk.ac.ed.bikerental;

import java.math.BigDecimal;

public class Payment {

    private int orderNumber;
    private BigDecimal deposit;
    private BigDecimal price;
    private BigDecimal total;
    private boolean depositReturned;

    public Payment(BigDecimal deposit, BigDecimal price, BigDecimal total) {
        this.deposit = deposit;
        this.price = price;
        this.total = total;
        this.depositReturned = false;
        this.orderNumber = Controller.getOrderCount();
        Controller.setOrderCount(this.orderNumber + 1);
    }

    public BigDecimal getTotal() {
        return total;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
