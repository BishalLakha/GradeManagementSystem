package com.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Menu {

    public static void mainMenu() {
        /*Show main menu*/
        System.out.println(" Enter choice among 1, 2, 3, 4 or 5. \n");
        System.out.println("1. Class Management \n");
        System.out.println("2. Category and Assignment Management \n");
        System.out.println("3. Student Management \n");
        System.out.println("4. Grade Reporting \n");
        System.out.println("5. Exit \n");
    }

    public static void menuCategoryAndAssignmentManagement(Connection conn, CategoryAndAssignmentManagement categoryAndAssignmentManagement, Integer activeClassId) {
        /* accept commands for category management */
        if (activeClassId !=0) {
            System.out.println(" Category and Assignment Management Command List\n" +
                    "● show-categories – list the categories with their weights\n" +
                    "● add-category Name weight – add a new category\n" +
                    "● show-assignment – list the assignments with their point values, grouped by category\n" +
                    "● add- assignment name Category Description points – add a new assignment");
            Scanner scan = new Scanner(System.in);
            String command = scan.nextLine();
            command = command.trim();
            String[] parameters = command.split(" ");
            if (parameters.length == 0) {
                System.out.println("Invalid command!");
            }

            switch (parameters[0]) {
                case "add-category":
                    parameters = command.split(" ", 3);
                    categoryAndAssignmentManagement.createCategory(conn, activeClassId, parameters[1], Integer.valueOf(parameters[2]));
                    break;
                case "show-categories":
                    categoryAndAssignmentManagement.showCategory(conn, activeClassId);
                    break;
                case "add-assignment":
                    parameters = command.split(" ", 5);
                    categoryAndAssignmentManagement.createAssignment(conn, activeClassId, parameters[1], Integer.valueOf(parameters[2]), parameters[3], Integer.valueOf(parameters[4]));
                    break;
                case "show-assignment":
                    categoryAndAssignmentManagement.showAssignment(conn,activeClassId);
                    break;
                default:
                    System.out.println(" Invalid option. Exiting the shell. \n");
                    break;
            }
        } else {
            System.out.println("Please activate the class first");
        }

    }

    public static Integer menuClassManagement(Connection conn, ClassManagement classManagement, Integer activeClassId) {
        /* accept commands for class management */

        System.out.println("Class Management Command lists \n" +
                "● Create a class: new-class CS410 Sp20 1 \"Databases\"\n" +
                "● List classes, with the # of students in each: list-classes\n" +
                "● Activate a class:\n" +
                "   o select-class CS410 selects the only section of CS410 in the most recent term, if there is only one such section; if there are multiple sections it fails.\n" +
                "   o select-class CS410 Sp20 selects the only section of CS410 in Fall 2018; if there are multiple such sections, it fails.\n" +
                "   o select-class CS410 Sp20 1 selects a specific section\n" +
                "● show-class shows the currently-active class");
        Scanner scan = new Scanner(System.in);
        String command = scan.nextLine();
        command = command.trim();
        String[] parameters = command.split(" ");
        if (parameters.length == 0) {
            return 0;
        }

        switch (parameters[0]) {
            case "new-class":
                parameters = command.split(" ", 5);
                classManagement.createClass(conn, parameters[1], parameters[2], Integer.valueOf(parameters[3]), parameters[4]);
                return 0;
            case "list-classes":
                classManagement.listClasses(conn);
                return 0;
            case "select-class":
                parameters = command.split(" ", 4);
                if (parameters.length == 2) {
                    return classManagement.selectClass(conn, parameters[1], "", "");
                } else if (parameters.length == 3) {
                    return classManagement.selectClass(conn, parameters[1], parameters[2], "");
                } else if (parameters.length == 4) {
                    return classManagement.selectClass(conn, parameters[1], parameters[2], parameters[3]);
                }
                return 0;

            case "show-class":
                classManagement.showClass(conn, activeClassId);
                return activeClassId;
            default:
                System.out.println(" Invalid option. Exiting the shell. \n");
                return 0;
        }


    }

    public static void menuStudentManagement(Connection conn, StudentManagement studentManagement, Integer activeClassId) {
        /* accept commands for student management */

        System.out.println("Student Management Commands List\n" +
                "● add-student username studentid Last First — adds a student and enrolls\n" +
                "them in the current class. If the student already exists, enroll them in the class; if the\n" +
                "name provided does not match their stored name, update the name but print a warning\n" +
                "that the name is being changed.\n" +
                "● add-student username — enrolls an already-existing student in the current class. If\n" +
                "the specified student does not exist, report an error.\n" +
                "● show-students – show all students in the current class\n" +
                "● show-students string – show all students with ‘string’ in their name or username\n" +
                "(case-insensitive)\n" +
                "● grade assignmentname username grade – assign the grade ‘grade’ for student\n" +
                "with user name ‘username’ for assignment ‘assignmentname’. If the student already has a\n" +
                "grade for that assignment, replace it. If the number of points exceeds the number of\n" +
                "points configured for the assignment, print a warning (showing the number of points\n" +
                "configured)");
        Scanner scan = new Scanner(System.in);
        String command = scan.nextLine();
        command = command.trim();
        String[] parameters = command.split(" ");
        if (parameters.length == 0) {
            System.out.println("Invalid command!");
        }

        switch (parameters[0]) {
            case "show-students":
                parameters = command.split(" ", 2);
                if (parameters.length == 1){
                    studentManagement.showStudents(conn,activeClassId);

                }else if (parameters.length == 2) {
                    studentManagement.showStudentWithString(conn,parameters[1],activeClassId);
                }
                break;
            case "grade":
                parameters = command.split(" ", 4);
                studentManagement.gradeAssignment(conn,activeClassId,Integer.valueOf(parameters[1]),parameters[2],Float.valueOf(parameters[3]));
                break;
            case "add-student":
                parameters = command.split(" ", 5);
                if (parameters.length == 2) {
                    studentManagement.enrollByUsername(conn,activeClassId,parameters[1]);
                } else if (parameters.length == 5) {
                    studentManagement.addStudent(conn,activeClassId, parameters[1], Integer.valueOf(parameters[2]),parameters[3],parameters[4]);
                }

                break;

            default:
                System.out.println(" Invalid option. Exiting the shell. \n");
        }


    }

    public static void menuGradeManagement(Connection conn, GradeManagement gradeManagement, Integer activeClassId) {
        /* accept commands for grade management */

        System.out.println("● student-grades username – show student’s current grade: all assignments, visually\n" +
                "grouped by category, with the student’s grade (if they have one). Show subtotals for each\n" +
                "category, along with the overall grade in the class.\n" +
                "● gradebook – show the current class’s gradebook: students (username, student ID, and\n" +
                "name), along with their total grades in the class.");
        Scanner scan = new Scanner(System.in);
        String command = scan.nextLine();
        command = command.trim();
        String[] parameters = command.split(" ");
        if (parameters.length == 0) {
            System.out.println("Invalid command!");
        }

        switch (parameters[0]) {
            case "student-grades":
                parameters = command.split(" ", 2);
                gradeManagement.showGradesByUsername(conn,activeClassId,parameters[1],true);

                break;
            case "gradebook":
                gradeManagement.gradeBook(conn,activeClassId);
                break;

            default:
                System.out.println(" Invalid option. Exiting the shell. \n");
        }


    }
}