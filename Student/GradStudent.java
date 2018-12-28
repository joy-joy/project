
public class GradStudent extends Student001 
{
   private String lastDegree;// = "Undergraduate";
   private String examTimePeriod;// = "2 Hours";
   private String classTimeOfDay;// = "Evening";
   
   private final int numberOfCourses = 8;
   private int coursesCompleted;
   
   private double courseGPAs[] = new double[numberOfCourses];
   private String courseGrades[]=new String[numberOfCourses];
   private final int courseCredit = 3;
   
   private double thesisGPA;
   private String thesisGrade;
   private final int thesisCredit = 6;
   
   private boolean courseComplete[] = new boolean[numberOfCourses];
   private boolean thesisComplete;
   
   private double CGPA;
   
   public GradStudent(String studentName, String studentID, char maleOrFemale) 
   {
      super(studentName, studentID, maleOrFemale);
      for (int i = 0; i < courseComplete.length; i++)
      {
         courseComplete[i]=false;
      }
      
      coursesCompleted = 0;
      thesisComplete = false;
      
      lastDegree = "Undergraduate";
      examTimePeriod = "2 Hours";
      classTimeOfDay = "Evening";
      
      CGPA=0.0;
   }

   public void setLastDegree (String lastDegree)
   {
      this.lastDegree = lastDegree;
   }
    
   public String getLastDegree ()
   {
      return lastDegree;
   }
    public void setExamTimePeriod (String examTimePeriod)
   {
      this.examTimePeriod = examTimePeriod;
   }
    
   public String getExamTimePeriod ()
   {
      return examTimePeriod;
   }
   public void setClassTimeOfDay (String classTimeOfDay)
   {
      this.classTimeOfDay = classTimeOfDay;
   }
    
   public String getClassTimeOfDay ()
   {
      return classTimeOfDay;
   }
   
   public void setThesisGPA (double thesisGPA)
   {
      this.thesisGPA = thesisGPA;
   }
    
   public double getThesisGPA ()
   {
      return thesisGPA;
   }
   
   public void setThesisGrade (String thesisGrade)
   {
      this.thesisGrade = thesisGrade;
   }
    
   public String getThesisGrade ()
   {
      return thesisGrade;
   }
   @Override
   public void lastDegreeCompleted()
   {
      System.out.printf("Last degreee completed by graduate student,"
              + " %s, is %s.\n",super.getStudentName(), lastDegree);
   }

   @Override
   public void examTimePeriod()
   {
      System.out.printf("Exam time period for graduate student,"
              + " %s, is %s.\n",super.getStudentName(), examTimePeriod);
   }
   
   @Override
   public void classTimeOfDay()
   {
      System.out.printf("Class time for graduate student,"
              + " %s, is %s.\n",super.getStudentName(), classTimeOfDay);
   }
   
   @Override
   public boolean courseComplete()
   {
      for (int i = 0; i < courseComplete.length; i++)
      {
         if (courseComplete[i]==false)
            return false;
         else if (i+1 == courseComplete.length && courseComplete[i]==true)
            return true;
         
      }
      return false;
   }
   
   public boolean thesisComplete()
   {
      return thesisComplete;
   }
   
   public boolean completedDegree()
   {
      if (thesisComplete() == true && courseComplete() == true)
         return true;
      else
         return false;
   }
   
    
   public void assignCourseGrade()
   {
      
      courseGPAs[coursesCompleted] = super.randomGPAGenerator();
      courseGrades[coursesCompleted] = super.gradeInsert(courseGPAs[coursesCompleted]);
      courseComplete[coursesCompleted]=true;
      
      System.out.println("Course grade updated: Course-"+(coursesCompleted+1)+"\n"
              + "Course GPA = "+courseGPAs[coursesCompleted]+"\n"
              + "Course Grade = "+courseGrades[coursesCompleted]);
      coursesCompleted++;
   }
   
   public void assignThesisGrade()
   {
      thesisGPA = super.randomGPAGenerator();
      
      thesisGrade = super.gradeInsert(thesisGPA);
      System.out.println("Thesis GPA obtained = " +thesisGPA+ "\n"
              + "Thesis Grade = "+thesisGrade);
   }
   
   @Override
   public void exam()
   {
      if (coursesCompleted < numberOfCourses)
      {
         System.out.printf("Student, %s, appears for exam.\n", super.getStudentName());
         assignCourseGrade();
      }
      else 
         System.out.println("All exams already complete.");
   }
   
   public void submitsThesis()
   {
      if (!thesisComplete)
      {  
         thesisComplete = true;
         System.out.printf("Student, %s, submits thesis.\n"
                 + "Thesis Complete = %s\n", super.getStudentName(), thesisComplete);
         assignThesisGrade();
      }
      else 
         System.out.println("Thisis already complete.");
   }
   
   @Override
   public void showGrades()
   {
      System.out.println("Subject-wise Grades\n\n"
                       + "Course   \t Grade");
      for (int i = 0; i<numberOfCourses; i++)
      {
         System.out.printf(courseGrades[i]!=null? 
                 "Course-%d   \t   %s\n" : 
                 "Course-%d   \t   \n", i+1, courseGrades[i]);
      }
   }
   
   public double showCGPA()
   {
      System.out.printf("Courses completed = %d\n"
              + "Thesis completed = %b\n", coursesCompleted, thesisComplete);
      
      if (coursesCompleted==0 && thesisComplete==false)
         System.out.printf("\nNo course grades or no thesis grade.\n");
      
      else if (thesisComplete==true && coursesCompleted==0)
      {
         CGPA = thesisGPA;
      }
      
      else if (coursesCompleted>0 && thesisComplete==false)
      {
         CGPA = 0;
         
         for (int i=0; i<coursesCompleted; i++)
         {
            CGPA =  CGPA + (courseCredit * courseGPAs[i]);
         }           
         CGPA = CGPA / (coursesCompleted*courseCredit);
      }
      
      else
      {  
         CGPA = thesisGPA * thesisCredit;
         for (int i=0; i<coursesCompleted; i++)
         {      
            CGPA =  CGPA + (courseCredit * courseGPAs[i]);
         }           
         
         CGPA = CGPA / (coursesCompleted*courseCredit + thesisCredit);
      } 
      return CGPA;
   }
   
    @Override
   public String toString()
   {
      return String.format("\nBasic Student Info:\n%s\n"
                         + "\nGraduate Student Info:\n"
                         + "\nLast degree          : %s"
                         + "\nExam Time Period     : %s"
                         + "\nClass Time Of Day    : %s"
                         + "\nAll Courses Complete : %b"
                         + "\nThesis Complete      : %b",
              super.toString(), getLastDegree(), getExamTimePeriod(), 
              getClassTimeOfDay(), courseComplete(), thesisComplete());
   }
}
