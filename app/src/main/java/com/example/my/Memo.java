package com.example.my;

public class Memo {

    int seq;
    String maintext;
    String contentstext;
    String subtext;
    //int isdone;


    public Memo(int seq, String maintext, String contentstext, String subtext) {
        this.seq = seq;
        this.maintext = maintext;
        this.contentstext = contentstext;
        this.subtext = subtext;
    }

    public Memo(String maintext, String contentstext, String subtext){
        this.maintext = maintext;
        this.contentstext = contentstext;
        this.subtext = subtext;
        //this.isdone = isdone;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getMaintext() {
        return maintext;
    }

    public void setMaintext(String maintext) {
        this.maintext = maintext;
    }

    public String getContentstext() {
        return contentstext;
    }

    public void setContentstext(String contentstext) {
        this.contentstext = contentstext;
    }

    public String getSubtext() {
        return subtext;
    }

    public void setSubtext(String subtext) {
        this.subtext = subtext;
    }

//    public int getIsdone() {
//        return isdone;
//    }
//
//    public void setIsdone(int isdone) {
//        this.isdone = isdone;
//    }
}
