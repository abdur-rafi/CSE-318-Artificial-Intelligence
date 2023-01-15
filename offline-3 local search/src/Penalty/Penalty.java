package Penalty;

import java.util.ArrayList;

import Problem.Student;

public class Penalty {

    ArrayList<Student> students;
    public Penalty(ArrayList<Student> students){
        this.students = students;
    }

    public long cost(PenaltyStrategy strategy){
        long cost = 0;

        for(var x : students){
            for(var y : x.getCourses()){
                for(var z : x.getCourses()){
                    cost += strategy.penalty(y.getExamDay(), z.getExamDay());
                }
            }
        }

        return cost;
    }
    
}
