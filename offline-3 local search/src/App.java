import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Scanner;

import CHeuristics.CHeursitics;
import CHeuristics.LargestDegree;
import CHeuristics.LargestEnrollment;
import CHeuristics.RandomNode;
import CHeuristics.SaturationDegree;
import GraphColoring.GraphColoring;
import GraphColoring.Node;
import PHeuristics.CalculatePenalty;
import PHeuristics.PHeuristics;
import Penalty.ExponentialStrategy;
import Penalty.LinearStrategy;
import Problem.Course;
import Problem.CourseNode;
import Problem.Spenalty;
import Problem.Student;

public class App {

    public static String[] datasets = {"car-f-92", "car-s-91", "kfu-s-93", "tre-s-92", "yor-f-83"};

    // "car-f-92", "car-s-91", "kfu-s-93",

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


    public static void resetCourses(ArrayList<Course> courses){
        for(var x : courses)
            x.setExamDay(-1);
    }

    public static void main(String[] args) throws Exception {

        BufferedWriter writer = new BufferedWriter(new FileWriter("stat.txt"));
        BufferedWriter writer2 = new BufferedWriter(new FileWriter("color.txt"));

        for(var f : datasets){

            // f = datasets[2];
            String fileName = "./data/"+ f;
            int iterations = 10000;

            writer.write("============" + fileName + "===========\n");

            var courses = Course.parseCourseFile(new File(fileName + ".crs"));
            var students = Student.parseStudentFile(new File(fileName + ".stu"), courses);
            
            var courseNodes = mapToCourseNode(courses);
            
            writer.write("Largest Degree\n");

            CHeursitics heursitic = new LargestDegree(courseNodes);
            GraphColoring coloring = new GraphColoring(heursitic);
            
            writer.write("colors used: " + coloring.color() + "\n");
            writer.write("Expo Strategy\n");
            // for(var x : courses){
            //     writer2.write(x.getCourseNumber() + ", " + x.getExamDay() + "\n");
            // }
            CalculatePenalty penalty = new Spenalty(students, new ExponentialStrategy());
            writer.write("penalty: " + penalty.averagePenalty() + "\n");
            new PHeuristics(courseNodes, penalty).reduce(iterations, writer);
            writer.write("penalty: " + penalty.averagePenalty() + "\n");
            writer.flush();
            
            // break;
            resetCourses(courses);

            writer.write("Saturation Degree\n");
            
            heursitic = new SaturationDegree(courseNodes);
            coloring = new GraphColoring(heursitic);
            
            writer.write("colors used: " + coloring.color() + "\n");
            writer.write("Expo Strategy\n");
            // penalty = new Spenalty(students, new ExponentialStrategy());
            writer.write("penalty: " + penalty.averagePenalty() + "\n");
            new PHeuristics(courseNodes, penalty).reduce(iterations, writer);
            writer.write("penalty: " + penalty.averagePenalty() + "\n");


            writer.flush();
            resetCourses(courses);


            writer.write("Largest Enrollment\n");
            
            heursitic = new LargestEnrollment(courseNodes);
            coloring = new GraphColoring(heursitic);
            
            writer.write("colors used: " + coloring.color() + "\n");
            writer.write("Expo Strategy\n");
            // penalty = new Spenalty(students, new ExponentialStrategy());
            writer.write("penalty: " + penalty.averagePenalty() + "\n");
            new PHeuristics(courseNodes, penalty).reduce(iterations, writer);
            writer.write("penalty: " + penalty.averagePenalty() + "\n");
            


            writer.flush();
            resetCourses(courses);


            writer.write("Random\n");
            
            heursitic = new RandomNode(courseNodes);
            coloring = new GraphColoring(heursitic);
            
            writer.write("colors used: " + coloring.color() + "\n");
            writer.write("Expo Strategy\n");
            // penalty = new Spenalty(students, new ExponentialStrategy());
            writer.write("penalty: " + penalty.averagePenalty() + "\n");
            new PHeuristics(courseNodes, penalty).reduce(iterations,writer);
            writer.write("penalty: " + penalty.averagePenalty() + "\n");
            

            writer.flush();
            resetCourses(courses);



            writer.write("Saturation Degree\n");
            
            heursitic = new SaturationDegree(courseNodes);
            coloring = new GraphColoring(heursitic);
            
            writer.write("colors used: " + coloring.color() + "\n");
            writer.write("Linear Strategy\n");
            penalty = new Spenalty(students, new LinearStrategy());
            writer.write("penalty: " + penalty.averagePenalty() + "\n");
            new PHeuristics(courseNodes, penalty).reduce(iterations,writer);
            writer.write("penalty: " + penalty.averagePenalty() + "\n");
            
            writer.flush();

            // break;
            
        }

        writer.close();
        writer2.close();
    }

    
}
