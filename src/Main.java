import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Main
{
    int[][] sudoku;
 private void generate(int difficulty)
   {
      Generate_sudoku gs=new Generate_sudoku(difficulty);
      sudoku=gs.problem;
      System.out.println("user puzzle ");
      System.out.println("______________________________________");
      for(int i=0;i<9;i++)
      {
          for(int j=0;j<9;j++)
          {
              if(sudoku[i][j]!=0&&((j+1)%3)!=0)
              {
                  if (j == 0) System.out.print("| " + sudoku[i][j] + " | ");
                  else
                      System.out.print(sudoku[i][j] + " | ");
              }
              else if(sudoku[i][j]!=0&&((j+1)%3)==0)
              {
                  System.out.print(sudoku[i][j]+" || ");
              }
              else if(sudoku[i][j]==0&&((j+1)%3)!=0)
              {
                  if(j==0) System.out.print("|   | ");
                  else
                      System.out.print("  | ");
              }
              else if(sudoku[i][j]==0&&((j+1)%3)==0)
              {
                  System.out.print("  || ");
              }

          }
         System.out.println();
             System.out.println("+++++++++++++++++++++++++++++++++++++++++");
          //System.out.println("========================================");
          if((i+1)%3==0)
              System.out.println("+++++++++++++++++++++++++++++++++++++++++");
      }
    }
    public void solve()
    {
        Solving solution=new Solving(sudoku);
    }

    public static void main(String[] args)
    {
        Main sudoku=new Main();
        Scanner sc=new Scanner(System.in);
        System.out.print("1\n------------------SUDOKU----------------------\n");
        System.out.println("Select a difficulty option(1,2 or 3)");
        System.out.println("\t1.Easy\n\t2.Medium\n\t3.Hard");
        int difficulty =sc.nextInt();
        sudoku.generate(difficulty);
        sudoku.solve();
    }
}