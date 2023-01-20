package Problem;

import java.util.ArrayList;

import PHeuristics.CalculatePenalty;
import Penalty.Penalty;
import Penalty.PenaltyStrategy;

public class Spenalty implements CalculatePenalty {
    
    ArrayList<Student> students;
    PenaltyStrategy strategy;
    Penalty penalty;

    public Spenalty(ArrayList<Student> students, PenaltyStrategy strategy){
        this.strategy = strategy;
        this.students = students;
        penalty = new Penalty(students);
        
    }

    @Override
    public long penalty() {
        return penalty.cost(strategy);
    }
    @Override
    public double averagePenalty() {
        return penalty() * 1. / students.size();
    }
}
