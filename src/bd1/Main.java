package bd1;

public class Main {
    
    public static void main(String[] args) {
        
        Registration registrationSystem = new Registration();

        registrationSystem.readFile("data/Subjects.txt", "Subjects");
        registrationSystem.readFile("data/Professions.txt", "Professions");
        registrationSystem.readFile("data/Exams.txt", "Exams");
        
        registrationSystem.calculateAllGPAs();

        registrationSystem.displaySubjects();
        registrationSystem.displayMajors();
        registrationSystem.displayAverageGPA();
        registrationSystem.displayFailingStudents();
        registrationSystem.displayGradesBySubject();
        registrationSystem.displayGradesByMajor();
    }
}