package cn.hgf.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Student {
  private int studentId;
  private String sex;
  private String school_name;
  private int classId;
  private int grade;

  public int getStudentId() {
    return studentId;
  }

  public void setStudentId(int studentId) {
    this.studentId = studentId;
  }

  public String getSchool_name() {
    return school_name;
  }

  public void setSchool_name(String school_name) {
    this.school_name = school_name;
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public int getClassId() {
    return classId;
  }

  public void setClassId(int classId) {
    this.classId = classId;
  }

  public int getGrade() {
    return grade;
  }

  public void setGrade(int grade) {
    this.grade = grade;
  }

  public Student() {
  }

  public Student(int studentId, String sex, String school_name, int classId, int grade) {
    this.studentId = studentId;
    this.sex = sex;
    this.school_name = school_name;
    this.classId = classId;
    this.grade = grade;
  }

  @Override
  public String toString() {
    return "Student{" +
            "studentId=" + studentId +
            ", sex='" + sex + '\'' +
            ", school_name='" + school_name + '\'' +
            ", classId=" + classId +
            ", grade=" + grade +
            '}';
  }

  public static List<Student> getStudentList(){

      List<Student> students = new ArrayList<>();
      Student student;
      for (int i = 0; i < 5; i++){

        student = new Student(1,"man","test",1,95);

        students.add(student);

      }

    return students;
  }
}
