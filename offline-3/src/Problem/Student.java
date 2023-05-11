package Problem;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Student {
    private int studentId;
    private ArrayList<Course> courses;


    public Student(int studentId){
        this.studentId = studentId;
        this.courses = new ArrayList<>();
    }

    public static ArrayList<Student> parseStudentFile(File file, ArrayList<Course> courseList){
        Scanner scanner = null;
        ArrayList<Student> students = new ArrayList<>();
        try {
            scanner = new Scanner(file);
            String line;
            int studentId = 1;
            while(scanner.hasNext()){
                line = scanner.nextLine();
                Student student = new Student(studentId++);
                String[] tokens = line.split(" ");
                students.add(student);
                for(var x : tokens){
                    Course from = courseList.get(Integer.parseInt(x) - 1);
                    student.courses.add(from);
                    for(var y : tokens){
                        if(x.equalsIgnoreCase(y))
                            continue;
                        Course to = courseList.get(Integer.parseInt(y) - 1);
                        if(from.getConflictingCourses().contains(to))
                            continue;
                        from.getConflictingCourses().add(to);
                        to.getConflictingCourses().add(from);
                    }
                }
            }
            
        } catch (Exception s) {
            s.printStackTrace();
        }
        finally{
            if(scanner != null)
                scanner.close();
        }
        return students;
    }

    public int getStudentId() {
        return studentId;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }
}
