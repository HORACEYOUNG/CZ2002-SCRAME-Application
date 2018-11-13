package com.group1;

public class AssessmentComponent implements java.io.Serializable{
    private float weightage;
    private float result = -1;
    private String assessmentType;

    public AssessmentComponent(float weightage, String assessmentType){
        this.weightage = weightage;
        this.assessmentType = assessmentType;
    }


    public AssessmentComponent(AssessmentComponent another)
    {
        this.weightage = another.weightage;
        this.result = another.result;
        this.assessmentType = another.assessmentType;
    }

    public float getWeightage(){return weightage;}
    public float getResult() {return result;}
    public String getAssessmentType(){return assessmentType;}


    public void setResult(float result)
    {
        this.result = result;
    }

}
