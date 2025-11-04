package bd1;

import dataStructures.ArrayLinearList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.io.InputStreamReader; 
import java.io.FileInputStream;

public class Registration {
    public ArrayLinearList studentList; 
    public ArrayLinearList subjectList; 
    public ArrayLinearList majorList;   

    public Registration() {
        this.studentList = new ArrayLinearList();
        this.subjectList = new ArrayLinearList();
        this.majorList = new ArrayLinearList();
    }
    
    public static float getGPA(int score) {
        return Student.convertScoreToGPA(score);
    }
    
    public static boolean isFailingGrade(int score) {
        return getGPA(score) == 0.0f;
    }

    public void readFile(String fileName, String type) {
        try {
            BufferedReader input = new BufferedReader(
                new InputStreamReader(
                    new FileInputStream(fileName), 
                    "UTF-8"
                )
            );
            String line;
            
            System.out.println("Reading " + fileName + " for " + type + "...");

            while ((line = input.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String values[] = line.split("/");

                if (type.equals("Subjects")) {
                    if (values.length < 3) continue;
                    String code = values[0].trim();
                    String name = values[1].trim(); 
                    float credit = Float.parseFloat(values[2].trim());
                    

                    Subject subject = new Subject(code, name, credit);
                    subjectList.add(subjectList.size(), subject);
                    
                } else if (type.equals("Professions")) {
                    if (values.length < 2) continue;
                    Major major = new Major(values[0].trim(), values[1].trim()); 
                    majorList.add(majorList.size(), major);

                } else if (type.equals("Exams")) {
                    if (values.length < 3) continue;
                    String studentCode = values[0].trim();
                    String lessonCode = values[1].trim();
                    int score = Integer.parseInt(values[2].trim());

                    Subject subject = findSubjectByCode(lessonCode);
                    if (subject == null) {
                        System.out.println("Warning: Subject " + lessonCode + " not found!");
                        continue;
                    }

                    Student tempStudent = new Student(studentCode);
                    int studentIndex = -1;
                    for(int i = 0; i < studentList.size(); i++) {
                        Student current = (Student) studentList.get(i);
                        if (current.code.equals(studentCode)) {
                            studentIndex = i;
                            break;
                        }
                    }
                    
                    Student student;
                    if (studentIndex == -1) {
                        student = tempStudent; 
                        studentList.add(studentList.size(), student);
                    } else {
                        student = (Student) studentList.get(studentIndex);
                    }
                    
                    Lessons lesson = new Lessons(subject, score);
                    student.lessons.add(student.lessons.size(), lesson);
                }
            }
            input.close();
            
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found: " + fileName);
            System.exit(1);
        } catch (Exception e) {
            System.out.println("Error reading file " + fileName + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private Subject findSubjectByCode(String code) {
        for (int i = 0; i < subjectList.size(); i++) {
            Subject subject = (Subject) subjectList.get(i);
            if (subject.code.equals(code)) {
                return subject;
            }
        }
        return null;
    }

    public void calculateAllGPAs() {
        for (int i = 0; i < studentList.size(); i++) {
            Student student = (Student) studentList.get(i);
            student.calculateGPA();
        }
    }
    
    public void displaySubjects() {
        System.out.println("\n 1. Нийт хичээлийн жагсаалт ");
        System.out.println("КОД\t       НЭР\t\t\tКРЕДИТ");
        for (int i = 0; i < subjectList.size(); i++) {
            Subject s = (Subject) subjectList.get(i);
            System.out.printf("%s\t%-30s%.1f\n", s.code, s.name, s.credit);
        }
    }

    public void displayMajors() {
        System.out.println("\n 2. Нийт мэргэжлийн жагсаалт ");
        System.out.println("КОД\tНЭР");
        for (int i = 0; i < majorList.size(); i++) {
            Major m = (Major) majorList.get(i);
            System.out.println(m.code + "\t" + m.name);
        }
    }

    public void displayAverageGPA() {
        System.out.println("\n 3. Нийт оюутны дундаж GPA ");
        if (studentList.isEmpty()) {
            System.out.println("Оюутны бүртгэл хоосон байна.");
            return;
        }

        float totalGPA = 0.0f;
        for (int i = 0; i < studentList.size(); i++) {
            Student s = (Student) studentList.get(i);
            totalGPA += s.GPA;
        }

        float averageGPA = totalGPA / studentList.size();
        System.out.printf("Нийт %d оюутны дундаж GPA: %.2f\n", studentList.size(), averageGPA);
    }

    public void displayFailingStudents() {
        System.out.println("\n 4. 3 ба түүнээс дээш F дүнтэй хасагдах оюутнууд ");
        boolean found = false;

        for (int i = 0; i < studentList.size(); i++) {
            Student s = (Student) studentList.get(i);
            int fCount = 0;
            
            Iterator iterator = s.lessons.iterator(); 
            while (iterator.hasNext()) {
                Lessons lesson = (Lessons) iterator.next();
                if (isFailingGrade(lesson.score)) {
                    fCount++;
                }
            }

            if (fCount >= 3) {
                System.out.printf("Код: %s, GPA: %.2f, F тоо: %d\n", s.code, s.GPA, fCount);
                found = true;
            }
        }

        if (!found) {
            System.out.println("3 ба түүнээс дээш F үнэлгээтэй оюутан олдсонгүй (Оноо < 60).");
        }
    }

    public void displayGradesBySubject() {
        System.out.println("\n 5. Хичээл бүр дээрх дүнгийн жагсаалт ");
        
        for (int i = 0; i < subjectList.size(); i++) {
            Subject currentSubject = (Subject) subjectList.get(i);
            System.out.println("\nХичээл: " + currentSubject.name + " (" + currentSubject.code + ")");
            System.out.println("ОЮУТНЫ КОД\t      ОНОО\tGPA");
            boolean found = false;

            for (int j = 0; j < studentList.size(); j++) {
                Student student = (Student) studentList.get(j);
                
              Iterator iterator = student.lessons.iterator();
                while (iterator.hasNext()) {
                    Lessons lesson = (Lessons) iterator.next();
                    if (lesson.learned.code.equals(currentSubject.code)) {
                        System.out.printf("%s\t\t%d\t%.2f\n", 
                                          student.code, 
                                          lesson.score, 
                                          getGPA(lesson.score));
                        found = true;
                        break;
                    }
                }
            }
            if (!found) {
                System.out.println("Тухайн хичээлээр дүн бүртгэгдээгүй байна.");
            }
        }
    }
    public void displayGradesByMajor() {
        System.out.println("\n 6. Мэргэжил бүр дээрх дүнгийн жагсаалт (Дундаж GPA) ");

        for (int i = 0; i < majorList.size(); i++) {
            Major currentMajor = (Major) majorList.get(i);
            String majorCode = currentMajor.code.toUpperCase();
            System.out.println("\nМэргэжил: " + currentMajor.name + " (" + majorCode + ")");
            System.out.println("ОЮУТНЫ КОД\t        GPA");
            
            float totalMajorGPA = 0.0f;
            int majorStudentCount = 0;
            boolean found = false;
            
            for (int j = 0; j < studentList.size(); j++) {
                Student student = (Student) studentList.get(j);
                if (student.code.length() >= 2 && student.code.substring(0, 2).toUpperCase().equals(majorCode)) {
                    System.out.printf("%s\t\t%.2f\n", student.code, student.GPA);
                    totalMajorGPA += student.GPA;
                    majorStudentCount++;
                    found = true;
                }
            }

            if (found) {
                float avgMajorGPA = totalMajorGPA / majorStudentCount;
                System.out.printf("Дундаж GPA: %.2f (%d оюутан)\n", avgMajorGPA, majorStudentCount);
            } else {
                System.out.println("Тухайн мэргэжлийн оюутан олдсонгүй.");
            }
        }
    }
}