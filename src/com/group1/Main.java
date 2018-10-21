package com.group1;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Scanner in = new Scanner(System.in);
        String intro = "Welcome to the SCRAME application console: \n Press the corresponding number to use: \n"
                + "1: Add a new Course:\n"
                + "6. Enter course assessment components weightage and sub-component weightage "
                + "7: Exit:";
        System.out.println(intro);
        int choice = 0;
        while(choice!=7){
            System.out.println(intro);
            choice = in.nextInt();
            in.nextLine();
            switch (choice){
                case 1:
                    // Create the course
                    Course newCourse1 = CourseManager.AddCourse();
                    // Assign the coordinator
                    try{
                        if(FileReadManager.CheckDuplicateCourses(newCourse1.GetCourseTitle())){
                            System.out.println("Add Course Failed: a course with the same course title has already been added");
                        }
                        else{
                            newCourse1.AssignCoordinator();
                            FileOutputManager.WriteCourse(newCourse1);
                            // Add Labs and Tutorialss
                            newCourse1.AddTutorialLabGroups("Tutorial");
                            newCourse1.AddTutorialLabGroups("Lab");
                            FileOutputManager.WriteSessions(newCourse1);
                        }
                    }
                    catch (IOException e){
                        System.out.println(e.getMessage());
                    }

                    break;

                case 6:
                    //Enter component weightage
                    System.out.println("Adding course assessment weightage...\n" +
                                       "Please input the course title:\n");
                    Scanner scanner6 = new Scanner(System.in);
                    String title6 = scanner6.nextLine();
                    try
                    {
                        if (!FileReadManager.CheckDuplicateCourses(title6))
                        {
                            System.out.println("The course you entered does not exist. Please add this course first");

                        }
                        else
                        {
                            CourseManager.AddCourseComponent(title6);
                        }
                    }
                    catch (IOException e)
                    {
                        System.out.println(e.getMessage());
                    }
                    break;




            }
        }

    }
}
