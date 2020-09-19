package com.zf.zjtf.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "configuration")
public class Configuration {
    private String appid;
    private String secret;
    private String apiPrefix;
    private String existsMember;
    private String getQuestionPaper;
    private String doQuestionPaper;
    private String getIdentifyCode;
    private String identifyLogIn;
    private String attendanceDetail;
    private String questionid;
    private String localAccessTokenPath;
    private String mainUrl;
    private String tmpMsgSendTo;
    private String title;
    private String desc;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getApiPrefix() {
        return apiPrefix;
    }

    public void setApiPrefix(String apiPrefix) {
        this.apiPrefix = apiPrefix;
    }

    public String getExistsMember() {
        return existsMember;
    }

    public void setExistsMember(String existsMember) {
        this.existsMember = existsMember;
    }

    public String getGetQuestionPaper() {
        return getQuestionPaper;
    }

    public void setGetQuestionPaper(String getQuestionPaper) {
        this.getQuestionPaper = getQuestionPaper;
    }

    public String getDoQuestionPaper() {
        return doQuestionPaper;
    }

    public void setDoQuestionPaper(String doQuestionPaper) {
        this.doQuestionPaper = doQuestionPaper;
    }

    public String getGetIdentifyCode() {
        return getIdentifyCode;
    }

    public void setGetIdentifyCode(String getIdentifyCode) {
        this.getIdentifyCode = getIdentifyCode;
    }

    public String getIdentifyLogIn() {
        return identifyLogIn;
    }

    public void setIdentifyLogIn(String identifyLogIn) {
        this.identifyLogIn = identifyLogIn;
    }

    public String getAttendanceDetail() {
        return attendanceDetail;
    }

    public void setAttendanceDetail(String attendanceDetail) {
        this.attendanceDetail = attendanceDetail;
    }

    public String getQuestionid() {
        return questionid;
    }

    public void setQuestionid(String questionid) {
        this.questionid = questionid;
    }

    public String getLocalAccessTokenPath() {
        return localAccessTokenPath;
    }

    public void setLocalAccessTokenPath(String localAccessTokenPath) {
        this.localAccessTokenPath = localAccessTokenPath;
    }

    public String getMainUrl() {
        return mainUrl;
    }

    public void setMainUrl(String mainUrl) {
        this.mainUrl = mainUrl;
    }

    public String getTmpMsgSendTo() {
        return tmpMsgSendTo;
    }

    public void setTmpMsgSendTo(String tmpMsgSendTo) {
        this.tmpMsgSendTo = tmpMsgSendTo;
    }
}
