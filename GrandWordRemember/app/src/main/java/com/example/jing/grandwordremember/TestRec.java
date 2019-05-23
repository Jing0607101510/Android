package com.example.jing.grandwordremember;

public class TestRec {
    private int id;
    private String word;
    private int level;
    private int test_count;
    private int correct_count;
    TestRec(){}
    TestRec(int id, String word, int level, int test_count, int correct_count){
        this.id = id;
        this.word = word;
        this.level = level;
        this.test_count = test_count;
        this.correct_count = correct_count;
    }
    public int getId(){return id;}
    public void setId(int id){this.id=id;}
    public String getWord(){return word;}
    public void setWord(String word){this.word=word;}
    public int getLevel(){return level;}
    public void setLevel(int level){this.level=level;}
    public int getTestCount(){return test_count;}
    public void setTestCount(int test_count){this.test_count=test_count;}
    public int getCorrectCount(){return correct_count;}
    public void setCorrectCount(int correct_count) {this.correct_count=correct_count;}
}
