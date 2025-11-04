package bd1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StudentRegistrationTest {

    private Subject math = new Subject("CS101", "Математик", 3.0f);
    private Subject physics = new Subject("PH202", "Физик", 4.0f);

    @Test
    void testConvertScoreToGPA_A_4_0() {
        assertEquals(4.0f, Student.convertScoreToGPA(96), 0.001); 
    }

    @Test
    void testConvertScoreToGPA_B_3_0() {
        assertEquals(3.0f, Student.convertScoreToGPA(84), 0.001); 
    }
    
    @Test
    void testConvertScoreToGPA_D_minus_0_7() {
        assertEquals(0.7f, Student.convertScoreToGPA(60), 0.001); 
    }

    @Test
    void testConvertScoreToGPA_F_0_0_Boundary() {
        assertEquals(0.0f, Student.convertScoreToGPA(59), 0.001); 
    }

    @Test
    void testConvertScoreToGPA_LowScore_0_0() {
        assertEquals(0.0f, Student.convertScoreToGPA(35), 0.001); 
    }

    @Test
    void testIsFailingGrade_True() {
        assertTrue(Registration.isFailingGrade(59));
    }

    @Test
    void testIsFailingGrade_False() {
        assertFalse(Registration.isFailingGrade(60));
    }

    @Test
    @SuppressWarnings("unchecked")
    void testCalculateGPA_Simple() {
        Student student = new Student("A23");
        student.lessons.add(0, new Lessons(math, 96));
        
        student.calculateGPA();
        assertEquals(4.0f, student.GPA, 0.001);
    }
    
    @Test
    @SuppressWarnings("unchecked")
    void testCalculateGPA_WeightedAverage() {
        Student student = new Student("B24");
        
        student.lessons.add(0, new Lessons(math, 74)); 
        student.lessons.add(1, new Lessons(physics, 91));
        
        float expectedGPA = 2.97142857f; 
        
        student.calculateGPA();
        assertEquals(expectedGPA, student.GPA, 0.001);
    }
    
    @Test
    @SuppressWarnings("unchecked")
    void testCalculateGPA_WithFailingGrade() {
        Student student = new Student("C25");
        
        student.lessons.add(0, new Lessons(math, 91)); 
        student.lessons.add(1, new Lessons(physics, 59)); 
        
        float expectedGPA = 1.585714f; 
        
        student.calculateGPA();
        assertEquals(expectedGPA, student.GPA, 0.001);
    }
    
    @Test
    void testCalculateGPA_NoLessons() {
        Student student = new Student("D26");
        student.calculateGPA();
        assertEquals(0.0f, student.GPA, 0.001);
    }
}