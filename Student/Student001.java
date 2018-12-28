
import java.security.SecureRandom;

abstract class Student001 
{
   String studentName;
   String studentID;
   char maleOrFemale;
   String lastDegree;
   
   public Student001(String studentName, String studentID, char maleOrFemale)
   {
       this.studentName = studentName;
       this.studentID = studentID;
       this.maleOrFemale = maleOrFemale;
   }  
   
   public void setStudentName (String studentName)
   {
      this.studentName = studentName;
   }
    
   public String getStudentName ()
   {
      return studentName;
   }
   
   public void setStudentID (String studentID)
   {
      this.studentID = studentID;
   }
    
   public String getStudentID ()
   {
      return studentID;
   }
      
   public void setMaleOrFemale (char maleOrFemale)
   {
      this.maleOrFemale = maleOrFemale;
   }
    
   public char getMaleOrFemale ()
   {
      return maleOrFemale;
   }
   
   public void eats()
   {
      System.out.printf("Student, %s, eats.\n", studentName);
   }
   
   public void attendsClass()
   {
      System.out.printf("Student, %s, attends class.\n", studentName);
   }
   
   public double randomGPAGenerator()
   {
      double indivGradeGPAs[] = new double[]
      {2.0, 2.25, 2.5, 2.75, 3.0, 3.25, 3.5, 3.75, 4.0};
      
      SecureRandom randomNumbers = new SecureRandom();
      //int randomValue = randomNumbers.nextInt();
      
      return (indivGradeGPAs[randomNumbers.nextInt(9)]);
      
   }
   public String gradeInsert(double GPA)
   {
      if      (GPA == 4.0)    return "A+";
      else if (GPA == 4.0)    return "A+";
      else if (GPA == 3.75)   return "A";
      else if (GPA == 3.5)    return "A-";
      else if (GPA == 3.25)   return "B+";
      else if (GPA == 3.0)    return "B";
      else if (GPA == 2.75)   return "B-";
      else if (GPA == 2.5)    return "C+";
      else if (GPA == 2.25)   return "C";
      else if (GPA == 2.0)    return "D";
      else return "F";
      
   }

   @Override
   public String toString()
   {
      return String.format("\nName of Student      : %s"
                         + "\nStudent ID           : %s"
                         + "\nStudent Gender       : %c",
              getStudentName(), getStudentID(), getMaleOrFemale());
   }

   //lastDegree = Undergraduate or HSC
   public abstract void lastDegreeCompleted();
   public abstract void examTimePeriod();
   public abstract void classTimeOfDay();
   public abstract boolean courseComplete();
   public abstract void exam();
   public abstract void showGrades();
  
}
 