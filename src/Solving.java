import java.util.*;
public class Solving
{
    int[][] users_sudoku=new int[9][9];
    int[][] problem=new int[9][9];
    Stack<Integer> undo=new Stack<Integer>();
    Stack<Integer> redo=new Stack<Integer>();
    Scanner sc=new Scanner(System.in);
    boolean[][] users_visited=new boolean[9][9];
    Solving(int[][] sudoku)
    {
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                users_sudoku[i][j]=sudoku[i][j];
                problem[i][j]=sudoku[i][j];
               if(users_sudoku[i][j]!=0) users_visited[i][j]=true;
            }
        }
        solve();
    }
    public void solve()
    {
        System.out.println("enter 1.to enter a value in a cell");
        System.out.println("2.undo ");
        System.out.println("3.if all cells are filled,check correct or wrong");
        System.out.println("4.show the current puzzle");
        System.out.println("5.redo");
        System.out.println("6.restart the game");
        Integer option=0;
        try
        {
             option = sc.nextInt();
        }
        catch(InputMismatchException e)
        {
            System.out.println(e);
            System.out.println("you entered invalid type of data");
        }
        if(option==1)
        {
            insertion();
            if(emp()==0)
                validation();
            System.out.println("enter 1.insert again ");
            try
            {
                if(sc.nextInt()==1) insertion();
            }
            catch(Exception e)
            {
               // e.printStackTrace();
                System.out.println("you entered invalid input");
            }
            solve();
        }
        else if(option==2)
        {
            int val=-1;
            int col=-1;
            int row=-1;
           if((!undo.isEmpty()) &&(undo.size()>=3)) {
               val = undo.pop();
               col = undo.pop();
               row = undo.pop();
               redo.push(row);
               redo.push(col);
               redo.push(val);
               users_sudoku[row][col] = 0;
           }
           solve();
        }
        else if(option==3)
        {
            validation();
        }
        else if(option==4)
        {
           display();
        }
        else if(option==5)
        {
            redoing();
            solve();
        }
        else if(option==6)
        {
            for(int i=0;i<9;i++)
            {
                for(int j=0;j<9;j++)
                {
                    users_sudoku[i][j]=problem[i][j];
                }
            }
            undo.clear();
            redo.clear();
            display();
        }
        else
        {
            System.out.println("entered invalid option");
            solve();
        }
    }
    public void redoing()
    {
        if(redo.size()>=3)
        {
            int val=redo.pop();
            int col=redo.pop();
            int row=redo.pop();
            users_sudoku[row][col]=val;
        }
    }
    public void display()
    {
        System.out.println("________________sudoku puzzle________________");
        System.out.println("______________________________________");
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                if(users_sudoku[i][j]!=0&&((j+1)%3)!=0)
                {
                    if (j == 0) System.out.print("| " + users_sudoku[i][j] + " | ");
                    else
                        System.out.print(users_sudoku[i][j] + " | ");
                }
                else if(users_sudoku[i][j]!=0&&((j+1)%3)==0)
                {
                    System.out.print(users_sudoku[i][j]+" || ");
                }
                else if(users_sudoku[i][j]==0&&((j+1)%3)!=0)
                {
                    if(j==0) System.out.print("|   | ");
                    else
                        System.out.print("  | ");
                }
                else if(users_sudoku[i][j]==0&&((j+1)%3)==0)
                {
                    System.out.print("  || ");
                }

            }
            System.out.println();
            System.out.println("-------------------------------------");
            //System.out.println("========================================");
            if((i+1)%3==0)
                System.out.println("------------------------------------");
        }

        solve();
    }
    public void insertion()
    {
        int row=0,col=0,val;
        System.out.println("enter row and col");
        try {
            row = sc.nextInt();
            col = sc.nextInt();
        }
        catch(Exception e)
        {
           // e.printStackTrace();
            System.out.println("Invalid type of input");
            insertion();
        }
        if(row>=9||col>=9)
        {
            System.out.println("enter the valid row and column values between 0 to 8");
            insertion();
        }
        if(users_visited[row][col]==false) {
            System.out.println("enter value to be inserted");
            val = sc.nextInt();
            if (val != 0) {
                users_sudoku[row][col] = val;
                undo.push(row);
                undo.push(col);
                undo.push(val);
            }
        }
        else
        {
            System.out.println("you cannot enter into specified cell");
        }
    }
    public void validation()
    {
        boolean flag=true;
       for(int i=0;i<9;i++)
       {
           for(int j=0;j<9;j++)
           {
               if(users_sudoku[i][j]!=0&&users_visited[i][j]==false)
               {
                   if((row_correct(users_sudoku,i,j)&&column_correct(users_sudoku,i,j)&&mini_grid_correct(users_sudoku,i,j))==false)
                   {
                       flag=false;
                       System.out.println("value entered at row "+  i+"and column "+j+" cell is"+users_sudoku[i][j]+" is wrong");
                   }
               }
           }
       }
       if(emp()==0&&flag)
           System.out.println("congratulations!!!.....................sudoku puzzle solved");
       else if(emp()!=0&&flag)
       {
           System.out.println(" till now all insertions are correct");
           solve();
       }
       else
       {
           solve();
       }
    }
    public int emp()
    {
        int count=0;
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                if(users_sudoku[i][j]==0) count++;
            }
        }
        return count;
    }
    public boolean row_correct(int[][] sudoku,int row,int column)
    {
        int num=sudoku[row][column];
        for(int i=0;i<9;i++)
            if(sudoku[row][i]==num&&i!=column) return false;
        return true;
    }
    public boolean column_correct(int[][] sudoku,int row,int column)
    {
        int num=sudoku[row][column];
        for(int i=0;i<9;i++)
            if(sudoku[i][column]==num&&i!=row) return false;
        return true;
    }
    public boolean mini_grid_correct(int[][] sudoku,int row,int column)
    {
        int r=row/3;
        int c=column/3;
        r=r*3;
        c=c*3;
        int num=sudoku[row][column];
        for(int i=r;i<r+3;i++)
        {
            for(int j=c;j<c+3;j++)
            {
                if(i!=row&&j!=column&&sudoku[i][j]==num) return false;
            }
        }
        return true;
    }
}
