package com.group1;

import java.util.ArrayList;

public class Validation
{
    //checking if exist, checking if input is legit, all checking method....

    public static boolean studentExists(String matricNumber, DataContainer container){
        if(container.getStudentsList().isEmpty())
            return false;
        for(Student student:container.getStudentsList()){
            if(matricNumber.toUpperCase().equals(student.getMatricNumber()))
                return true;
        }
        return false;
    }
    public static boolean CheckCourseExisted(String courseCode, DataContainer container){

        if(container.getCourseList().isEmpty())
            return false;
        for ( Course course:container.getCourseList()){
            if (course.getCourseTitle().equals(courseCode.toUpperCase()))
                return true;
        }
        return false;
    }
    public static boolean CheckTutExisted(String courseName, String tutGroupName, DataContainer container){
        for ( Course currCourse:container.getCourseList()){
            if (currCourse.getCourseTitle().equals(courseName.toUpperCase())) {
                for (Tutorial currTut: currCourse.GetTutorialList()){
                    if(currTut.getSessionName().equals(tutGroupName.toUpperCase()))
                        return true;
                }
            }
        }
        return false;
    }

    public static boolean CheckLabExisted(String courseName, String labGroupName, DataContainer container){
        for ( Course currCourse:container.getCourseList()){
            if (currCourse.getCourseTitle().equals(courseName.toUpperCase())) {
                for (Lab currLab: currCourse.GetLabList()){
                    if(currLab.getSessionName().equals(labGroupName.toUpperCase()))
                        return true;
                }
            }
        }
        return false;
    }


    public static Boolean CheckStudentResultsRecord(Student student, String coursetitle)
    {
        for(String key:student.GetCourseAndResult().keySet())
        {
            if (key.equals(coursetitle))
            {
                ArrayList<AssessmentComponent> listtemp = student.GetCourseAndResult().get(key);
                if (listtemp.isEmpty())
                    return false;
            }
        }
        return true;
    }

    public static Boolean CheckWhetherStudentRegisteredForACourse(Student student, String coursetitle)
    {
        for(String key:student.GetCourseAndResult().keySet())
        {
            if (key.equals(coursetitle))
                return true;
        }
        return false;
    }

    public static Boolean CheckWhetherHasAssessmentWeightage(Course course)
    {
        if (course.GetComponents().isEmpty())
            return false;
        else
            return true;
    }




    public static boolean ValidateNumberInput(String str){
        try{
            int x = Integer.parseInt(str);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }

    }


    public static boolean ValidateFloatInput(String str){
        try{
            float x = Float.parseFloat(str);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }
    public static boolean ValidateWeightageSum(float f)
    {
        if (f == 1)
            return true;
        else
            return false;
    }

    public static boolean ValidateNameInput(String str){
        if (str.matches("[a-zA-Z\\s]*")) {
            return true;
        }
        else return false;
    }

    public static boolean ValidateGroupNameInput(String str){
        if (str.matches("[a-zA-Z0-9]*")) {
            return true;
        }
        else return false;
    }

}
