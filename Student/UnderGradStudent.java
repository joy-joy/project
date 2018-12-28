public class UnderGradStudent extends Student001
{
   private String lastDegree;// = "HSC";
   private String examTimePeriod;// = "3 Hours";
   private String classTimeOfDay;// = "Day";
   
   private final int numberOfCourses = 10;
   private final int courseCredit = 4;
   private final int projectCredit = 6;
   
   private double courseGPAs[] = new double[numberOfCourses];
   private String courseGrades[]=new String[numberOfCourses];
   private boolean courseComplete[] = new boolean[numberOfCourses];
   
   private int coursesCompleted;
   
   private double projectGPA;
   private String projectGrade;
   private double CGPA;
   
   private boolean projectComplete = false;
   
   
   public UnderGradStudent(String studentName, String studentID, char maleOrFemale) 
   {
      super(studentName, studentID, maleOrFemale);
      for (int i = 0; i < courseComplete.length; i++)
      {
         courseComplete[i]=false;
      }
      
      coursesCompleted = 0;
      projectComplete = false;
      
      lastDegree = "HSC";
      examTimePeriod = "3 Hours";
      classTimeOfDay = "Day";
      
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
   public void setProjectGPA (double projectGPA)
   {
      this.projectGPA = projectGPA;
   }
    
   public double getProjectGPA ()
   {
      return projectGPA;
   }
   
   public void setProjectGrade (String projectGrade)
   {
      this.projectGrade = projectGrade;
   }
    
   public String getProjectGrade ()
   {
      return projectGrade;
   }
   
   @Override
   public void lastDegreeCompleted()
   {
      System.out.printf("Last degreee completed by undergraduate student,"
              + " %s, is %s.\n",super.getStudentName(), lastDegree);
   }

   @Override
   public void examTimePeriod()
   {
      System.out.printf("Exam time period for undergraduate student,"
              + " %s, is %s.\n",super.getStudentName(), examTimePeriod);
   }
   
   @Override
   public void classTimeOfDay()
   {
      System.out.printf("Class time for undergraduate student,"
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
   
   public boolean projectComplete()
   {
      return projectComplete;
   }
   
   public boolean completedDegree()
   {
      if (projectComplete() == true && courseComplete() == true)
         return true;
      else
         return false;
   }
   
   public void assignCourseGrade()
   {
      
      courseGPAs[coursesCompleted] = super.randomGPAGenerator();
      courseGrades[coursesCompleted] = super.gradeInsert(courseGPAs[coursesCompleted]);
      courseComplete[coursesCompleted]=true;
      //System.out.printf("coursesComplete = %d\n", coursesCompleted);
      System.out.println("Course grade updated: course-"+(coursesCompleted+1)+"\n"
              + "Course GPA = "+courseGPAs[coursesCompleted]+"\n"
              + "Course Grade = "+courseGrades[coursesCompleted]);
      coursesCompleted++;
   }
   
   public void assignProjectGrade()
   {
      projectGPA = super.randomGPAGenerator();
      projectGrade = super.gradeInsert(projectGPA);
      System.out.println("Thesis GPA obtained = " +projectGPA+ "\n"
              + "Thesis Grade = "+projectGrade);
   }
   
   @Override
   public void exam()
   {
      if (coursesCompleted < numberOfCourses)
      {
         System.out.println("Student appears for exam.");
         assignCourseGrade();
      }
      else 
         System.out.println("All exams already complete.");
   }
   
   public void submitsProject()
   {
      if (!projectComplete)
      {
         projectComplete = true;
         System.out.printf("Student, %s, Submits Project.\n"
                 + "Project Complete = %s\n", super.getStudentName(), projectComplete);
         assignProjectGrade();
      }
      else 
         System.out.println("Project already complete.");
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
      System.out.printf("Courses Completed = %d\n"
              + "Project completed = %b\n", coursesCompleted, projectComplete);
      
      if (coursesCompleted==0 && projectComplete==false)
         System.out.printf("\nNo course grades or no thesis grade.\n");
      
      else if (projectComplete==true && coursesCompleted==0)
      {
         CGPA = projectGPA;
      }
      
      else if (coursesCompleted>0 && projectComplete==false)
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
         CGPA = projectGPA * projectCredit;
         for (int i=0; i<coursesCompleted; i++)
         {      
            CGPA =  CGPA + (courseCredit * courseGPAs[i]);
         }           
         
         CGPA = CGPA / (coursesCompleted*courseCredit + projectCredit);
      } 
      return CGPA;
   }
   
    @Override
   public String toString()
   {
      return String.format("\nBasic Student Info:\n%s\n"
                         + "\nUndergraduate Student Info:\n"
                         + "\nLast degree          : %s"
                         + "\nExam Time Period     : %s"
                         + "\nClass Time Of Day    : %s"
                         + "\nAll Courses Complete : %b"
                         + "\nProject Complete     : %b",
              super.toString(), getLastDegree(), getExamTimePeriod(), 
              getClassTimeOfDay(), courseComplete(), projectComplete());
   }
}
