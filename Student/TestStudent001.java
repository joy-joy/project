
import java.util.Scanner;

public class TestStudent001 
{
   public static void main(String[] args)
   {
      boolean gradStudentInstanciated=false;
      boolean underGradStudentInstanciated=false;
      
      
      Scanner input = new Scanner(System.in);
      
      Student001 myGradStudent      = new GradStudent(null, null, '0');
      Student001 myUnderGradStudent = new UnderGradStudent (null, null, '0');
      
      GradStudent castGradStudent            = (GradStudent) myGradStudent;
      UnderGradStudent castUnderGradStudent  = (UnderGradStudent) myUnderGradStudent;
      
      while(true)
      {
         baseMenu(); 
         int typeOfStudent = input.nextInt();
         
         switch(typeOfStudent)
         {
            case 1:  System.out.println("Graduate Student.\n");
            
                     gradStudentInstanciated = myGradInitialInstanciation 
                        (myGradStudent, input, gradStudentInstanciated);
            
                     System.out.println(myGradStudent);
                     while(true)
                     {
                        gradMenu();

                        int choice = input.nextInt();

                        switchCaseGraduate(myGradStudent, castGradStudent, choice);

                        if (choice > 12) break;
                     }
                     break;
           
            case 2:  System.out.println("Undergraduate Student.\n");

                     underGradStudentInstanciated = myUnderGradInitialInstanciation 
                        (myUnderGradStudent, input, underGradStudentInstanciated);

                     System.out.println(myUnderGradStudent);

                     while(true)
                     {
                        undrGradMenu();
                        
                        int choice = input.nextInt();

                        switchCaseUnderGraduate(myUnderGradStudent, 
                              castUnderGradStudent, choice);

                        if (choice >12) break;
                     }
                     break;
           
         }
         
         if (typeOfStudent > 2) break;
      }
   } 
   
   static boolean myGradInitialInstanciation (Student001 myGradStudent, 
           Scanner input, boolean gradStudentInstanciated)
   {
      if (!gradStudentInstanciated)
            {
               String garbage = input.nextLine();

               System.out.println("Please enter the Student Name:");
               myGradStudent.setStudentName(input.nextLine());
               
               System.out.println("\nPlease enter Student ID:");
               myGradStudent.setStudentID(input.nextLine());
               

               System.out.println("\nPlease enter Student gender: (M/F)");
               myGradStudent.setMaleOrFemale(input.next().charAt(0));
               
               gradStudentInstanciated = true;
            }
      return gradStudentInstanciated;
   }
   
   static void switchCaseGraduate(Student001 myGradStudent, 
           GradStudent castGradStudent, int choice)
   {
      switch(choice)
      {
         case 1:  myGradStudent.eats();                  break;
         case 2:  myGradStudent.attendsClass();          break;
         case 3:  castGradStudent.exam();                break;
         case 4:  castGradStudent.submitsThesis();       break;
         case 5:  castGradStudent.lastDegreeCompleted(); break;
         case 6:  castGradStudent.classTimeOfDay();      break;
         case 7:  castGradStudent.examTimePeriod();      break;
         case 8:  System.out.println(castGradStudent.courseComplete()? ""
            + "All courses complete." : "Some courses still incomplete.");
                  break;
         case 9:  System.out.println(castGradStudent.thesisComplete()? ""
                  + "Thesis Grade is: "+castGradStudent.getThesisGrade() : ""
                  + "Thesis is still incomplete");   
                  break;
         case 10: castGradStudent.showGrades();          break;
         case 11: System.out.printf("CGPA = %,.3f\n",  
                  castGradStudent.showCGPA());           break;
         case 12:  System.out.printf(castGradStudent.completedDegree()? 
                 "Yes, student %s, completed Graduate Degree. Degree awarded.\n" 
                 : "No, student %s, has not yet completed Graduate Degree\n"
                ,myGradStudent.getStudentName(),myGradStudent.getStudentName()); 
                  break;  
         default: break; 
      }  
   }
   
   static boolean instanceofCheckingGrad(Student001 myGradStudent)
   {
      if (myGradStudent instanceof GradStudent)
         {
            return true;
         }
      
      else return false;
   }
   
   static boolean instanceofCheckingUnderGrad(Student001 myUnderGradStudent)
   {
      if (myUnderGradStudent instanceof GradStudent)
         {
            return true;
         }
      else return false;
   }
   
   static boolean myUnderGradInitialInstanciation (Student001 myUnderGradStudent, 
           Scanner input,boolean underGradStudentInstanciated)
   {
      if (!underGradStudentInstanciated)
         {
            String garbage = input.nextLine();

            System.out.println("Please enter the Student Name:");
            myUnderGradStudent.setStudentName(input.nextLine());
            
            System.out.println("\nPlease enter Student ID:");
            myUnderGradStudent.setStudentID(input.nextLine());
            
            System.out.println("\nPlease enter Student gender: (M/F)");
            myUnderGradStudent.setMaleOrFemale(input.next().charAt(0));

            underGradStudentInstanciated = true;
         }
      return underGradStudentInstanciated;
   }
   
   static void switchCaseUnderGraduate(Student001 myUnderGradStudent, 
           UnderGradStudent castUnderGradStudent, int choice)
   {
      switch(choice)
      {
         case 1:  myUnderGradStudent.eats();                  break;
         case 2:  myUnderGradStudent.attendsClass();          break;
         case 3:  castUnderGradStudent.exam();                break;
         case 4:  castUnderGradStudent.submitsProject();      break;
         case 5:  castUnderGradStudent.lastDegreeCompleted(); break;
         case 6:  castUnderGradStudent.classTimeOfDay();      break;
         case 7:  castUnderGradStudent.examTimePeriod();      break;
         case 8:  System.out.println(castUnderGradStudent.courseComplete()? ""
            + "All courses complete." : "Some courses still incomplete.");break;
         case 9:  System.out.println(castUnderGradStudent.projectComplete()? ""
                 + "Project Grade is: "+castUnderGradStudent.getProjectGrade() 
                 : "Project is still incomplete");               break;
         case 10: castUnderGradStudent.showGrades();
                  break;
         case 11: System.out.printf("CGPA = %,.3f\n"
                  ,  castUnderGradStudent.showCGPA());          break;

         case 12:  System.out.printf(castUnderGradStudent.completedDegree()? 
                 "Yes, student %s, completed Undergraduate Degree. Degree awarded.\n" 
                 : "No, student %s, has not yet completed Undergraduate Degree\n"
                 , myUnderGradStudent.getStudentName(), myUnderGradStudent.getStudentName());    
                  break;  
         default: break; 
      }
   }
   
   public static void baseMenu()
   {
      System.out.println("Please choose from the following:\n");
      System.out.println("\t 1: Graduate Student");
      System.out.println("\t 2: Undergraduate Student");
      System.out.println("\t 3: Exit");
   }
   
   public static void gradMenu()
   {
      System.out.println("\nPlease choose from the following: \n\n"
         + "      Student:\t\t\tShow Student's:\n\n"
         + "\t1.  Eats\t\t        5.  Last degree completed\n"
         + "\t2.  Attends class\t\t6.  Class time period\n"
         + "\t3.  Sits for exam\t\t7.  Exam time period\n"
         + "\t4.  Submits thesis\n\n"
         + "      Show:\n\n"
         + "\t8.  Whether All courses complete\n"
         + "\t9.  Thesis grade / Thesis completed?\n"
         + "\t10. Subject-wise Grades\n"
         + "\t11. CGPA\n"
         + "\t12. Whether Student completed Degree\n\n"
              + ""
         + "\t13. Back to main menu\n");
   }
   
   public static void undrGradMenu()
   {
      System.out.println("\nPlease choose from the following: \n\n"
         + "      Student:\t\t\tShow Student's:\n\n"
         + "\t1.  Eats\t\t        5.  Last degree completed\n"
         + "\t2.  Attends class\t\t6.  Class time period\n"
         + "\t3.  Sits for exam\t\t7.  Exam time period\n"
         + "\t4.  Submits project\n\n"
         + "      Show:\n\n"
         + "\t8.  Whether All courses complete\n"
         + "\t9.  Project grade / Project completed?\n"
         + "\t10. Subject-wise Grades\n"
         + "\t11. CGPA\n"
         + "\t12. Whether Student completed Degree\n\n"
              + ""
         + "\t13. Back to main menu\n");
   }        
}

