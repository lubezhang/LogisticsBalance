package com.lube.replenish.entity;

/**
 * Created with IntelliJ IDEA.
 * User: zhangqh
 * Date: 13-1-17
 * Time: 上午12:15
 * To change this template use File | Settings | File Templates.
 */
public class TBalance{
    private String balanceId;
    private String balanceCode;
    private String customerId;
    private String operatorId;
    private String gatherState;
    private float money;
    private String payoffState;
    private String addresseeDate;
    private String addrDateBegin;
    private String addrDateEnd;
    private String isEdit;
    private String balanceUser;
    private String operator;
    private String operatorDate;

    public String getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(String balanceId) {
        this.balanceId = balanceId;
    }

    public String getBalanceCode() {
        return balanceCode;
    }

    public void setBalanceCode(String balanceCode) {
        this.balanceCode = balanceCode;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getGatherState() {
        return gatherState;
    }

    public void setGatherState(String gatherState) {
        this.gatherState = gatherState;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getPayoffState() {
        return payoffState;
    }

    public void setPayoffState(String payoffState) {
        this.payoffState = payoffState;
    }

    public String getAddresseeDate() {
        return addresseeDate;
    }

    public void setAddresseeDate(String addressee) {
        this.addresseeDate = addressee;
    }

    public String getAddrDateBegin() {
        return addrDateBegin;
    }

    public void setAddrDateBegin(String addrDateBegin) {
        this.addrDateBegin = addrDateBegin;
    }

    public String getAddrDateEnd() {
        return addrDateEnd;
    }

    public void setAddrDateEnd(String addrDateEnd) {
        this.addrDateEnd = addrDateEnd;
    }

    public String getBalanceUser() {
        return balanceUser;
    }

    public void setBalanceUser(String balanceUser) {
        this.balanceUser = balanceUser;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getEdit() {
        return isEdit;
    }

    public void setEdit(String edit) {
        isEdit = edit;
    }

    public String getOperatorDate() {
        return operatorDate;
    }

    public void setOperatorDate(String operatorDate) {
        this.operatorDate = operatorDate;
    }
}
