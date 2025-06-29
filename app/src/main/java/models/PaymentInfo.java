package models;

public class PaymentInfo {
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(double shippingFee) {
        this.shippingFee = shippingFee;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PaymentInfo(double amount, double discount, double shippingFee, double total, String method, String status) {
        this.amount = amount;
        this.discount = discount;
        this.shippingFee = shippingFee;
        this.total = total;
        this.method = method;
        this.status = status;
    }

    public double amount;
    public double discount;
    public double shippingFee;
    public double total;
    public String method;
    public String status;

    public PaymentInfo() {
    }
}