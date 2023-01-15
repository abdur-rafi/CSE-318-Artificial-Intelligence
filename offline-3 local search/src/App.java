import java.io.File;
import java.util.ArrayList;
import CHeuristics.CHeursitics;
import CHeuristics.SaturationDegree;
import GraphColoring.GraphColoring;
import GraphColoring.Node;
import PHeuristics.CalculatePenalty;
import PHeuristics.PHeuristics;
import Penalty.ExponentialStrategy;
import Penalty.LinearStrategy;
import Penalty.Penalty;
import Penalty.PenaltyStrategy;
import Problem.Course;
import Problem.CourseNode;
import Problem.Spenalty;
import Problem.Student;

public class App {

    public static String[] datasets = {"car-f-92", "car-s-91", "kfu-s-93", "tre-s-92", "yor-f-83", "hec-s-92"};

    

    public void processStudentFile(File file){
        
    }

    public static void printCourseList(ArrayList<Course> courses){
        for(var x : courses){
            System.out.println("course : " + x.getCourseNumber());
            for(var y : x.getConflictingCourses())
                System.out.print(y.getCourseNumber() + " ");
            System.out.println();
        }
    }

    public static ArrayList<Node> mapToCourseNode(ArrayList<Course> courses){
        ArrayList<CourseNode> lst = new ArrayList<>();
        for(var x : courses){
            lst.add(new CourseNode(x));
            // System.out.println(x.getStudentCount());
        }
        for(var x : lst){
            x.calcNeighbors(lst);
        }
        ArrayList<Node> lst2 = new ArrayList<>();
        for(var x : lst){
            lst2.add(x);
        }
        return lst2;
    }

    public static void main(String[] args) throws Exception {
        String fileName = "./data/"+ datasets[0];
        var courses = Course.parseCourseFile(new File(fileName + ".crs"));
        var students = Student.parseStudentFile(new File(fileName + ".stu"), courses);
        
        var courseNodes = mapToCourseNode(courses);
        CHeursitics heursitic = new SaturationDegree(courseNodes);
        GraphColoring coloring = new GraphColoring(heursitic);
        System.out.println(coloring.color());
        CalculatePenalty penalty = new Spenalty(students, new ExponentialStrategy());
        new PHeuristics(courseNodes, penalty).oneStep();;

    }

    
}
