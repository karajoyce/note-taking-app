package com.example.demo.model;

public class HyperLink {
    private int start;
    private int end;
    private Page target;

    public HyperLink(int start, int end, Page target){
        this.start = start;
        this.end = end;
        this.target = target;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public Page getTargetPage() {
        return target;
    }
}
