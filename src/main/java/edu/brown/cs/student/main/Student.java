package edu.brown.cs.student.main;

import java.util.ArrayList;
import java.util.List;
import edu.brown.cs.student.main.KDTree.KDInsert;

public class Student implements KDInsert {
    private String id;
    private String name;
    private double x;
    private double y;
    private double z;

    /**.
         * constructor for students
         * @param studentId string representing id
         * @param Name string representing full name
         * @param years_experience double representing years of experience
         * @param weekly_avail_hours double representing hours available weekly
         * @param software_engn_confidence double representing confidence in software engineering ability
         * @throws IllegalArgumentException if input is malformed
         */
        public Student(String studentId, String Name, double years_experience,
                        double weekly_avail_hours,  double software_engn_confidence)
        {
            this.id = studentId; // index 0
            this.name = Name; // index 1
            this.x = years_experience; // index 8
            this.y = weekly_avail_hours; // index 10
            this.z = software_engn_confidence; //index 13

        }

        /**.
        Returns String Representation of Student data
         Years of Experience
         Weekly Hours
         Software Eng Confidence
         */
        @Override
        public String getID(){return this.id;}
        @Override
        public int dimNum(){return 3;}
        @Override
        public String getName() {
        return name;
    }
        @Override
        public double getX() {
            return this.x;
        }
        @Override
        public double getY() {
        return this.y;
    }
        @Override
        public double getZ() {
        return this.z;
    }
        @Override
        public double calculateDistance(KDInsert Node) {
             return Math.sqrt(Math.pow(Math.abs(Node.getX() - x), 2)
                + Math.pow(Math.abs(Node.getY() - y), 2)
                + Math.pow(Math.abs(Node.getZ() - z), 2));
        }

    @Override
    public double getCoordinate(int dimension) {
        if (dimension == 0) {
            return this.getX();
        } else if (dimension == 1) {
            return this.getY();
        } else {
            return this.getZ();
        }
    }

}
