package Problem;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Course {
    private int studentCount;
    private int courseNumber;
    private int examDay;

    private ArrayList<Course> conflictingCourses;

    public Course(int a, int b){
        studentCount = b;
        courseNumber = a;
        conflictingCourses = new ArrayList<>();
        examDay = -1;
    }

    public static ArrayList<Course> parseCourseFile(File file){

        ArrayList<Course> list = new ArrayList<>();
        Scanner scanner = null;
        try{
            scanner = new Scanner(file);
            while(scanner.hasNext()){
                int courseId = scanner.nextInt();
                int noStudents = scanner.nextInt();
                list.add(new Course(courseId, noStudents));
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally{
            if (scanner != null)
                scanner.close();
        }
        return list;
    }

    public ArrayList<Course> getConflictingCourses() {
        return conflictingCourses;
    }
    
    public int getStudentCount() {
        return studentCount;
    }

    public int getCourseNumber() {
        return courseNumber;
    }
    public void setExamDay(int examDay) {
        this.examDay = examDay;
    }
    public int getExamDay() {
        return examDay;
    }
}
