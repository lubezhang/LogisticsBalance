package com.lube.user.entity;

/**
 * Created with IntelliJ IDEA.
 * User: zhangqh
 * Date: 13-3-10
 * Time: 下午5:03
 * To change this template use File | Settings | File Templates.
 */
public class Operator {
    private String operatorId;
    private String username;
    private String password;
    private String operatorName;
    private String operatorType;

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(String operatorType) {
        this.operatorType = operatorType;
    }

    @Override
    public String toString() {
        return "Operator{" +
                "operatorId='" + operatorId + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", operatorName='" + operatorName + '\'' +
                ", operatorType='" + operatorType + '\'' +
                '}';
    }
}
