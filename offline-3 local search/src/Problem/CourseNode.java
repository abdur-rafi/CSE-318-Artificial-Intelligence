package Problem;

import java.util.ArrayList;
import java.util.List;

import GraphColoring.Node;

public class CourseNode implements Node {
    
    Course course;
    int color;
    ArrayList<Node> lst;
    public CourseNode(Course course){
        this.course = course;
        color = -1;
        lst = new ArrayList<>();
    }

    public void calcNeighbors(ArrayList<CourseNode> arr){
        for(var x : course.getConflictingCourses())
            lst.add(arr.get(x.getCourseNumber() - 1));
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public void setColor(int color) {
        this.color = color;
        course.setExamDay(color);
    }

    @Override
    public List<Node> getNeighbors() {
        return lst;
    }
    
    @Override
    public int getResidents() {
        return course.getStudentCount();
    }

    @Override
    public boolean isColored() {
        return getColor() != -1;
    }

    @Override
    public int getId() {
        return course.getCourseNumber();
    }

}
