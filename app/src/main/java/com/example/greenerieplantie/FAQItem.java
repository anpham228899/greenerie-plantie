package com.example.greenerieplantie;

public class FAQItem {
    private String question;
    private String answer;
    private String tag;
    private String context;

    public FAQItem(String question, String answer, String tag, String context) {
        this.question = question;
        this.answer = answer;
        this.tag = tag;
        this.context = context;
    }

    public String getQuestion() { return question; }
    public String getAnswer() { return answer; }
    public String getTag() { return tag; }
    public String getContext() { return context; }
}