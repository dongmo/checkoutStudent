package com.aws.lambda.s3.sns;

public class StudentCheckout {
    public String rollNo;
    public String name;
    public String testScore;
    public String grade;

    public StudentCheckout() {
    }

    public StudentCheckout(String rollNo, String name, String testScore, String grade) {
        this.rollNo = rollNo;
        this.name = name;
        this.testScore = testScore;
        this.grade = grade;
    }
    
    @Override
    public String toString() {
        return "StudentCheckout{" +
                "rollNo='" + rollNo + '\'' +
                ", name='" + name + '\'' +
                ", testScore='" + testScore + '\'' +
                ", grade='" + grade + '\'' +
                '}';
    }

}
