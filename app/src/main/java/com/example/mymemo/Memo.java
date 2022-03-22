package com.example.mymemo;

public class Memo {

    int seq;
    String mainContent;
    String regTime;
    int isdone;


    public Memo(int seq, String mainContent, String regTime, int isdone) {
        this.seq = seq;
        this.mainContent = mainContent;
        this.regTime = regTime;
        this.isdone = isdone;
    }

    public Memo(String mainContent, String regTime, int isdone) {
        this.mainContent = mainContent;
        this.regTime = regTime;
        this.isdone = isdone;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getMainContent() {
        return mainContent;
    }

    public void setMainContent(String mainContent) {
        this.mainContent = mainContent;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public int getIsdone() {
        return isdone;
    }

    public void setIsdone(int isdone) {
        this.isdone = isdone;
    }
}