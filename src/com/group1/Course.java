package com.group1;

import Exceptions.NameNotValidException;
import Exceptions.TutorialLabNameInvalidException;
import Exceptions.TutorialLabNumberInvalidException;
import Exceptions.WeightageNotValidException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Course implements java.io.Serializable{
    private String courseTitle;
    private Coordinator coordinator;
    private ArrayList<AssessmentComponent> components = new ArrayList<>();
    private ArrayList<AssessmentComponent> subcomponents = new ArrayList<>();
    private ArrayList<Tutorial> tutorialGroups = new ArrayList<>();
    private ArrayList<Lab> labGroups = new ArrayList<>();
    private int maxTutorialsNumber = 20;
    private int maxLabsNumber = 20;
    private ArrayList<Student> studentList = new ArrayList<>();



    public Course(String courseTitle){
        this.courseTitle = courseTitle;
    }

    public void AssignCoordinator(){
        Scanner in = new Scanner(System.in);
        try {
            // Get Coordinator Information From user
            System.out.println("Assign the coordinator of the course: " + courseTitle + ". Please Input the name of the coordinator: ");
            String coordinatorName = in.nextLine();

            if(!InputManager.ValidateNameInput(coordinatorName)) // If the input is not valid, throws exception
                throw new NameNotValidException();
            System.out.println("Assign the coordinator of the course: " + courseTitle + ". Please Input the School of the coordinator: ");

            String coordinatorSchool = in.nextLine();
            if(!InputManager.ValidateNameInput(coordinatorSchool)) // If the input is not valid, throws exception
                throw new NameNotValidException();

            this.coordinator = new Coordinator(coordinatorName, coordinatorSchool);
            System.out.println("Assign Coordinator Success: Coordinator " + coordinator.getCoordinatorName() + " of school " + coordinator.getCoordinatorSchool()
                    +" has been assigned to course: " + courseTitle +".");
        }
        catch (NameNotValidException e){
            System.out.println(e.getMessage());
            AssignCoordinator(); // Call the function again to perform the task
        }
    }

    public void AddTutorialLabGroups(String type){
        Scanner in = new Scanner(System.in);
        try{
            System.out.println("Add "+ type + ": Please input the total number of " + type + " to add.");
            String rawNumTutorialGroups = in.nextLine();
            int numTutorialGroups;

            if(Validation.ValidateNumberInput(rawNumTutorialGroups)){ // If the parse result returns true, AKA the input is a valid integer, otherwise throw exception in else block
                numTutorialGroups = Integer.parseInt(rawNumTutorialGroups);
                for(int i = 0 ; i < numTutorialGroups ; i++){
                    System.out.println("Add " + type + " NO." + String.valueOf(i+1) + ". Please input the name of the "+ type + "Group: ");
                    String GroupName = in.nextLine();

                    if (!InputManager.ValidateGroupNameInput(GroupName))
                        throw new TutorialLabNameInvalidException();

                    System.out.println("Add " + type + " NO." + String.valueOf(i+1) + ". Please input the vacancy of the "+ type + " Group: ");
                    String rawTutorialVacancy = in.nextLine();

                    int vacancy;
                    if(Validation.ValidateNumberInput(rawTutorialVacancy)){
                        vacancy = Integer.parseInt(rawTutorialVacancy);
                        // The following block works differently for Tutorial and Lab
                        if(type.equals("Tutorial")) {
                            Tutorial newSession = new Tutorial(GroupName, vacancy);
                            tutorialGroups.add(newSession);
                            System.out.println("Add " + type + " No." + String.valueOf(i+1)+ " Success: " + "The tutorial group name is "+
                                    newSession.GetName() + " and the vacancy is " + String.valueOf(newSession.GetTotalVacancy())+". ");
                        }
                        else if(type.equals("Lab")){
                            Lab newSession = new Lab(GroupName, vacancy);
                            labGroups.add((newSession));
                            System.out.println("Add " + type + " No." + String.valueOf(i+1)+ " Success: " + "The lab group name is "+
                                    newSession.GetName() + " and the vacancy is " + String.valueOf(newSession.GetTotalVacancy())+". ");
                        }
                    }
                    else{
                        throw new TutorialLabNumberInvalidException();
                    }
                }
            }
            else{
                throw new TutorialLabNumberInvalidException();
            }
        }
        // Handles the exception and call the function again
        catch (TutorialLabNumberInvalidException e){
            System.out.println(e.getMessage());
            AddTutorialLabGroups(type);
        }
        catch (TutorialLabNameInvalidException e){
            System.out.println(e.getMessage());
            AddTutorialLabGroups(type);
        }
    }

    public void AssignComponentWeightage(ArrayList<AssessmentComponent> components, String assessmenttype){
        Scanner in = new Scanner(System.in);
        System.out.println("Assign Components and Weightages:\nPlease input the weightage of the " + assessmenttype + " : (a float number between 0-1)");
        String rawExamWeightage = in.nextLine();
        try{
            if(!Validation.ValidateWeightageInput(rawExamWeightage) ||  Float.parseFloat(rawExamWeightage ) <= 0){
                throw new WeightageNotValidException();
            }
            float examWeightage = Float.parseFloat(rawExamWeightage);
            AssessmentComponent newComponent = new AssessmentComponent(examWeightage, assessmenttype);
            components.add(newComponent);
            System.out.println("Assign " + assessmenttype + " Weightage Successful\n");
        }
        catch (WeightageNotValidException e){
            System.out.println(e.getMessage());
            AssignComponentWeightage(components, assessmenttype);
        }

    }
    public void ClearComponentWeightage(ArrayList<AssessmentComponent> components) { components.clear();}

    public void AddTutorial(Tutorial sessionName){
        this.tutorialGroups.add(sessionName);
    }

    public void AddLab(Lab sessionName){
        this.labGroups.add(sessionName);
    }

    public String GetCourseTitle(){
        return courseTitle;
    }
    public Coordinator GetCoordinator(){return coordinator;}
    public ArrayList<Tutorial> GetTutorialList(){return tutorialGroups;}
    public ArrayList<Lab> GetLabList(){return labGroups;}
    public ArrayList<AssessmentComponent> GetComponents(){return components;}
    public ArrayList<AssessmentComponent> GetSubComponents(){return subcomponents;}
    public ArrayList<Student> GetStudentList(){ return studentList;}

    public boolean HaveTutorial(){
        if(this.tutorialGroups.isEmpty())
            return false;
        else return true;
    }

    public boolean HaveLab(){
        if(this.labGroups.isEmpty())
            return false;
        else return true;
    }

}
