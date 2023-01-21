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
            x.getCourses().sort((a,b)->Integer.compare(a.getExamDay(), b.getExamDay()));
            for(int i = 0; i + 1 < x.getCourses().size(); ++i){
                for(int j = i + 1;j < x.getCourses().size(); ++j)
                    cost += strategy.penalty(x.getCourses().get(i).getExamDay(), x.getCourses().get(j).getExamDay());
            }
            // for(var y : x.getCourses()){
            //     for(var z : x.getCourses()){
            //         if(z.getCourseNumber() == y.getCourseNumber())
            //             continue;
            //         cost += strategy.penalty(y.getExamDay(), z.getExamDay());
            //     }
            // }
        }

        return cost;
    }
    
}
