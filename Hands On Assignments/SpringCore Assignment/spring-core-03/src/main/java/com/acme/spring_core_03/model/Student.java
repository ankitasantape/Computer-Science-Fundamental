package com.acme.spring_core_03.model;

public class Student {

    private String studentId;
    private String studentName;
    private Test studentTest;

    public Test getStudentTest() {
        return studentTest;
    }

    public void setStudentTest(Test studentTest) {
        this.studentTest = studentTest;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void display(){
        System.out.println("ID:" + this.getStudentId());
        System.out.println("Name:" + this.getStudentName());
        System.out.println("Test ID:" + this.getStudentTest().getTestId());
        System.out.println("Test Title:" + this.getStudentTest().getTestTitle());
        System.out.println("Test Marks:" + this.getStudentTest().getTestMarks());
        System.out.println();
    }
}
