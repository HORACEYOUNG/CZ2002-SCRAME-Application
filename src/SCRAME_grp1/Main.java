package SCRAME_grp1;

import Exceptions.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * STUDENT COURSE REGISTRATION AND MARK ENTRY Application
 * The application allows the creation of courses and adding of student records.
 *
 *
 *
 * @author Group1 BCG2
 * @version Final version
 */

public class Main {
    /**
     * Main method to the programme that handle user interaction through console GUI
     * @param args nil
     */

    public static void main(String[] args)
    {
	// write your code here
        String studentMatric;
        String courseTitle;
        String tutorialName;
        String labName;
        Course newCourse = null;
        Student newStudent = null;
        Scanner in = new Scanner(System.in);
        DataContainer dataContainer = new DataContainer();
        ArrayList<Tutorial> tutorialList = new ArrayList<>();
        ArrayList<Lab> labList = new ArrayList<>();


        // Initialization and deserialize the data container file
        System.out.println("Initializing...");
        try
        {
            FileInputStream fileIn = new FileInputStream("data/SerializableDataContainer.ser");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            dataContainer = (DataContainer) objectIn.readObject();
            ReadingManager.readProefessors(dataContainer);
            dataContainer.professors.add(new Professor("Li Fang", "SCSE"));
            dataContainer.professors.add(new Professor("Jack", "SCSE"));
            dataContainer.professors.add(new Professor("John", "SCSE"));
            dataContainer.professors.add(new Professor("Peter", "SCSE"));

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        




        String intro =    "|===================================================|" + "\n"
                        + "|      Welcome to the SCRAME application console:   |" + "\n"
                        + "|        Press the corresponding number to use:     |" + "\n"
                        + "|===================================================|" + "\n"
                        + "| 1: Add a new student                              |" + "\n"
                        + "| 2: Add a new course                               |" + "\n"
                        + "| 3: Register student for a course                  |" + "\n"
                        + "| 4: Check available slot in a class                |" + "\n"
                        + "| 5: Print student list by lecture, tutorial or lab |" + "\n"
                        + "| 6. Enter course assessment components weightage   |" + "\n"
                        + "| 7: Enter exam marks for students                  |" + "\n"
                        + "| 8: Enter coursework marks for students            |" + "\n"
                        + "| 9. Print course statistics                        |" + "\n"
                        + "| 10. Print student transcript                      |" + "\n"
                        + "| 11. Quit                                          |" + "\n"
                        + "|===================================================|" + "\n"
                        + "| 0. Save                                           |" + "\n"
                        + "|===================================================|" + "\n";


        int choice = 0;
        while(choice!=11)
        {
            System.out.println(intro);
            choice = in.nextInt();
            in.nextLine();




            switch (choice){
                case 1:
		            //Testcase 1: Add in student
                    dataContainer.addStudent();
                    break;
                case 2:
                    // Testcase 2: Create the course
                    dataContainer.addCourse();
                    break;
                case 3:
                // Testcase 3: Register student for a course
                   EditingManager.register(dataContainer);
                    break;
                case 4:
                // Testcase 4: Check available slot in a class
                    ReadingManager.printVacancy(dataContainer);
                    break;
                case 5:
                //Testcase 5: Print student list by lecture, tutorial or lab

                    while (true) {
                        System.out.println("Please key in Course Code");
                        Scanner sc = new Scanner(System.in);
                        String courseName = sc.nextLine();
                        try{
                            if (!Validation.checkCourseExisted(courseName.toUpperCase(),dataContainer)) {
                                System.out.println("The course you entered does not exist. Please add this course first.\n");
                                continue;

                            }
                            else{
                                ArrayList<Course> courseList = dataContainer.getCourseList();
                                for(Course course:courseList){
                                    if(courseName.equals(course.getCourseTitle())){
                                        newCourse=course;
                                        break;
                                    }
                                }


                                System.out.println("Key in 'Lec' to print by lecture || 'Tut' to print by tutorial || 'Lab' to print by lab || '0' to exit");
                                String printList = sc.nextLine().toUpperCase();

                                if(printList.equals("LEC")){
                                    ReadingManager.printStudentList(newCourse);
                                    break;
                                }
                                else if(printList.equals("TUT")) {
                                    if(!newCourse.haveTutorial())
                                    {
                                        System.out.println("The course you have chosen does not have tutorials.");
                                        continue;
                                    }
                                    System.out.println("Please type the name of a tutorial to check students enrolled in: ");
                                    int i = 1;
                                    while (i <= newCourse.getTutorialList().size()) {
                                        System.out.println(i + ". " + newCourse.getTutorialList().get(i - 1).getName());
                                        i++;
                                    }
                                    tutorialName = in.nextLine();


                                    tutorialList = newCourse.getTutorialList();
                                    Tutorial thisTutorial=null;
                                    boolean found=false;

                                    for(Tutorial tutorial:tutorialList){
                                        if(tutorialName.equals(tutorial.getName())){
                                            thisTutorial = tutorial;
                                            found=true;
                                            }
                                            }if (found==false) throw new TutorialGroupNonExistentException();



                                    ReadingManager.printStudentList(thisTutorial);
                                    break;
                                }
                                else if(printList.equals("LAB")) {
                                    if(!newCourse.haveLab())
                                    {
                                        System.out.println("The course you have chosen does not have labs.");
                                        continue;
                                    }
                                    System.out.println("Please type the name of a lab to check students enrolled in: ");
                                    int i = 1;
                                    while (i <= newCourse.getLabList().size()) {
                                        System.out.println(i + ". " + newCourse.getLabList().get(i - 1).getName());
                                        i++;
                                    }
                                    labName = in.nextLine();


                                    labList = newCourse.getLabList();
                                    Lab thisLab = null;
                                    boolean found=false;

                                    for(Lab lab:labList){
                                        if(labName.equals(lab.getName())){
                                            thisLab = lab;
                                            found=true;
                                        }
                                    }
                                        if (found==false) throw new LabGroupNonExistentException();


                                    ReadingManager.printStudentList(thisLab);
                                    break;
                                }else if(printList.equals("0")) break;
                                else
                                 System.out.println("Invalid option.");

                            }
                        }
                        catch (TutorialGroupNonExistentException e){System.out.println(e.getMessage());}
                        catch (LabGroupNonExistentException e){System.out.println((e.getMessage()));}

                        }
                    break;
                case 6:
                //Enter component weightage
                    System.out.println("Adding course assessment weightage...\n" +
                                       "Please input the course title:");
                    Scanner scanner6 = new Scanner(System.in);
                    String title6 = scanner6.nextLine();
                    try
                    {
                        if (!Validation.checkCourseExisted(title6, dataContainer))
                        {
                            System.out.println("The course you entered does not exist. Please add this course first.\n");
                        }
                        else
                        {
                            EditingManager.addCourseComponent(title6, dataContainer);
                        }
                    }
                    catch (Exception e)
                    {
                    System.out.println(e.getMessage());
                    }
                    break;
                case 7:
                // Testcase 7: Enter exam mark
                    System.out.println("Please enter the course title:");
                    Scanner scanner = new Scanner(System.in);
                    String title7 = scanner.next();
                    try {
                        if (Validation.checkCourseExisted(title7, dataContainer)) {
                            System.out.println("Please enter the student's matriculation number:");
                            String matric = scanner.next();
                            if(Validation.studentExists(matric, dataContainer)) {
                                EditingManager.assignExamResults(matric, title7, dataContainer); }
                            else { throw new StudentNotExistException(); }
                        } else { throw new CourseNotFoundException(); }
                    }
                    catch (Exception e) {
                        System.out.println(e.getMessage());
                        scanner.nextLine();
                    }
                    break;
                case 8:
                    // Testcase 8: Enter coursework mark
                    System.out.println("Please enter the course title:");
                    Scanner scanner8 = new Scanner(System.in);
                    String title8 = scanner8.next();
                    try {
                        if (Validation.checkCourseExisted(title8, dataContainer)) {
                            System.out.println("Please enter the student's matriculation number:");
                            String matric = scanner8.next();
                            if(Validation.studentExists(matric, dataContainer)) {
                                EditingManager.assignCourseworkResults(matric, title8, dataContainer); }
                            else { throw new StudentNotExistException(); }
                        } else { throw new CourseNotFoundException(); }
                    }
                    catch (Exception e) {
                        System.out.println(e.getMessage());
                        scanner8.nextLine();
                    }
                    break;



                case 9:

                 //testcase 9: Print course statistics
                 //Show grade percentage for overall (exam + coursework)
                 //exam only and coursework only.
                    System.out.println("Please enter Course Code to check for course statistics");
                    Scanner sc1 = new Scanner(System.in);
                    String courseTitleName = sc1.nextLine().toUpperCase();
                    try{
                        if (!Validation.checkCourseExisted(courseTitleName, dataContainer))
                            System.out.println("The course you entered does not exist. Please enter another course code.\n");
                        else{
                            ReadingManager.printCourseStatistics(courseTitleName, dataContainer);
                            }
                        }catch (IndexOutOfBoundsException e) {
                        System.out.println("The course is currently empty. Please enter the course statistics before printing Course Statistics!");
                    }
                    break;


            case 10:
            	//test case 10: Print student transcript.
            	//individual overall course mark and grade for all the courses registered
            	//individual component marks ¨C exam, coursework, subcomponents from Result.txt 
            	//The configured weightages should be displayed as well
            	System.out.println("Please enter the Student Matric Number to check for transcript");
            	Scanner sc2 = new Scanner(System.in);
            	String studentMatricTranscript = sc2.nextLine();
            	if (!Validation.studentExists(studentMatricTranscript, dataContainer)) {
            			 System.out.println("The Matric Number does not exist.\n");
            	    }
            	else {
            			ReadingManager.printTranscript(studentMatricTranscript, dataContainer);
            	}
                break;

                case 11:
                    //to quit
                	System.out.println("Congratulations! You have successfully quit the program");
                    break;

                case 0:
                    try{
                        FileOutputStream fileOut = new FileOutputStream("data/SerializableDataContainer.ser");
                        ObjectOutputStream out = new ObjectOutputStream(fileOut);
                        out.writeObject(dataContainer);
                        out.close();
                        fileOut.close();
                        System.out.println("The data has been successfully saved");

                    }
                    catch (Exception e){
                        System.out.println(e.getMessage());
                    }
            }
        }
    }
}
