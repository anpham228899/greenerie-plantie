package models;

public class PaymentInfo {
    public double amount;
    public String method;
    public String status;

    public PaymentInfo() {}
    public PaymentInfo(double amount, String method, String status) {
        this.amount = amount;
        this.method = method;
        this.status = status;
    }
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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
}