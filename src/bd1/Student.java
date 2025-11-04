package bd1;

import dataStructures.Chain;
import java.util.Iterator;

public class Student {
    public String code;
    public float GPA = 0.0f;
    public Chain lessons;

    public Student(String code) {
        this.code = code;
        this.lessons = new Chain();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return code.equals(student.code);
    }
    
    @Override
    public String toString() {
        return code + " (GPA: " + String.format("%.2f", GPA) + ")";
    }

    public static float convertScoreToGPA(int score) {
        if (score >= 96) return 4.0f; 
        else if (score >= 91) return 3.7f; 
        else if (score >= 88) return 3.4f; 
        else if (score >= 84) return 3.0f; 
        else if (score >= 81) return 2.7f; 
        else if (score >= 78) return 2.4f; 
        else if (score >= 74) return 2.0f; 
        else if (score >= 71) return 1.7f; 
        else if (score >= 68) return 1.3f; 
        else if (score >= 64) return 1.0f; 
        else if (score >= 60) return 0.7f; 
        else return 0.0f;
    }

    public void calculateGPA() {
        if (lessons.isEmpty()) {
            this.GPA = 0.0f;
            return;
        }

        float totalWeightedGPA = 0.0f;
        float totalCredits = 0.0f;

        Iterator iterator = lessons.iterator();
        while (iterator.hasNext()) {
            Lessons lesson = (Lessons) iterator.next();
            float gpaScore = convertScoreToGPA(lesson.score);
            
            totalWeightedGPA += gpaScore * lesson.learned.credit;
            totalCredits += lesson.learned.credit;
        }

        if (totalCredits > 0) {
            this.GPA = totalWeightedGPA / totalCredits;
        } else {
            this.GPA = 0.0f;
        }
    }
}