package com.group1;

import java.io.*;
import java.lang.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;

public class FileOutputManager
{


    //This output manage includes all method used to write or append data to respective data file.txt.
    //The format for the txt file is that for each line, different field is separated by tab and different sub-field is separated by space


    public static void WriteResults(String matric, String coursecode, String examResult, String courseWorkResult, String[] resultsarray)
    {

        //Results.txt
        //filed 0: matric number
        //field 1: course name
        //field 2: final exam results
        //field 3: coursework results
        //field 4?: coursework component results separated by space
        try
        {
            File file = new File("data/Results.txt");
            file.createNewFile();   //create file if not exist
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            String line = matric+"\t"+coursecode+"\t"+ examResult+"\t"+courseWorkResult+"\t";
            writer.write(line);
            int count = 0;
            while (count<resultsarray.length)
            {
                writer.write(resultsarray[count]+" ");
                count++;
            }
            writer.write("\n");
            writer.newLine();
            writer.close();



        }
        catch (IOException e2)
        {
            System.out.println(e2.getMessage());

        }
    }

/*    public static void WriteRegisteredStudentsforCourse(String courseTitle, String studentMatric){
        //courseTitle.txt
        //filed 0:student matric number
        try
        {
            File file = new File("data/"+courseTitle+".txt");
            file.createNewFile();   //create file if not exist
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            String line = studentMatric;
            writer.write(line);
            writer.newLine();
            writer.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }*/





    public static void WriteSessions(Course course){

        //Tutorial.text Lab.txt
        //filed 0: course title
        //field 1: groupname
        //field 2: total vacancy
        //field 3: current vacancy

        try
        {
            //Write all the tutorials
            File tutFile = new File("data/Tutorials.txt");
            BufferedWriter tutWriter = new BufferedWriter(new FileWriter(tutFile, true));
            ArrayList<Tutorial> tutorialList = course.GetTutorialList();
            for(Tutorial tutorialGroup : tutorialList){
                String newTutGroup = course.GetCourseTitle() + "\t" + tutorialGroup.GetName() + "\t" + String.valueOf(tutorialGroup.GetTotalVacancy())+ "\t" + String.valueOf(tutorialGroup.GetTotalVacancy());
                tutWriter.write(newTutGroup);
                tutWriter.newLine();
            }
            tutWriter.close();

            //Write all the labs
            File labFile = new File("data/Labs.txt");
            BufferedWriter labWriter = new BufferedWriter(new FileWriter(labFile, true));
            ArrayList<Lab> labList = course.GetLabList();
            for(Lab labGroup : labList){
                String newLabGroup = course.GetCourseTitle() + "\t" + labGroup.GetName() + "\t" + String.valueOf(labGroup.GetTotalVacancy())+ "\t" + String.valueOf(labGroup.GetTotalVacancy());
                labWriter.write(newLabGroup);
                labWriter.newLine();
            }
            labWriter.close();


        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }




    public static void RegisterCourseWithoutTutorialLab(String matric, String courseTitle)throws FileNotFoundException {
        try{
            UpdateCourse(matric, courseTitle);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public static void UpdateCourse(String matric, String courseTitle)throws FileNotFoundException, IOException{
        File courseFile = new File("data/Courses.txt");
        BufferedReader courseReader = new BufferedReader(new FileReader(courseFile));
        String buffer = "", line, course = "";
        while((line = courseReader.readLine())!=null){
            String[] currentCourse = line.split("\t");
            if (currentCourse[0].equals(courseTitle)){
                line += "\t" + matric;
            }
            buffer += line + "\r\n";
        }
        courseReader.close();

        FileWriter courseWriter = new FileWriter("data/Courses.txt");
        courseWriter.write(buffer);
        courseWriter.close();
    }

    public static void UpdateSession(String matric, String courseTitle, String sessionName, String sessionType)throws FileNotFoundException, IOException{
        File sessionFile = new File("default");
        String filePath = "default";
        if(sessionType.equals("Tutorial")){
            sessionFile = new File("data/Tutorials.txt");
            filePath = "data/Tutorials.txt";
        }
        else if (sessionType.equals("Lab")){
            sessionFile = new File("data/Labs.txt");
            filePath = "data/Labs.txt";
        }

        BufferedReader courseReader = new BufferedReader(new FileReader(sessionFile));
        String buffer = "", line, course = "";
        while((line = courseReader.readLine())!=null){
            String[] currentCourse = line.split("\t");
            if (currentCourse[0].equals(courseTitle) && currentCourse[1].equals(sessionName)){
                line= currentCourse[0] + "\t" + currentCourse[1] + "\t" + currentCourse[2] + "\t" + String.valueOf(Integer.valueOf(currentCourse[3])-1);
                line += "\t" + matric;
            }
            buffer += line + "\r\n";
        }
        courseReader.close();

        FileWriter courseWriter = new FileWriter(filePath);
        courseWriter.write(buffer);
        courseWriter.close();
    }






}
