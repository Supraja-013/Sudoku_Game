import java.util.*;
public class Generate_sudoku
{
    boolean flag=false;
    HashMap<Integer,ArrayList<Integer>> map=new HashMap<>();
    int[][] sudoku=new int[9][9];
    int[][] copy=new int[9][9];
    int[][] problem=new int[9][9];
    int difficulty;
    Generate_sudoku(int k)
    {
       difficulty=k;
       boolean ans=solve(0,0);
       for(int i=0;i<9;i++)
       {
           for(int j=0;j<9;j++)
           {
               copy[i][j]=sudoku[i][j];
               //System.out.print(copy[i][j]+"  ");
           }
           //System.out.println();
       }
       if(difficulty==1) {
           easy_sudoku();
           //System.out.println("first generated problem");
           for(int i=0;i<9;i++)
           {
               for(int j=0;j<9;j++)
               {
                   problem[i][j]=sudoku[i][j];
                   //System.out.print(problem[i][j]+" ");
               }
               //System.out.println();
           }
           int score_of_sudoku=score();
           //System.out.println(score_of_sudoku+"is sudoku score");
           while(!(score_of_sudoku>42&&score_of_sudoku<46))
           {
               sudoku=copy;
              //System.out.println("repeated generating sudoku");
               /*for(int i=0;i<9;i++)
               {
                   for(int j=0;j<9;j++)
                   {
                       System.out.print(sudoku[i][j]+" ");
                   }
                   System.out.println();
               }*/
               easy_sudoku();
               //System.out.println("repeated generating problem");
               for(int i=0;i<9;i++)
               {
                   for(int j=0;j<9;j++)
                   {
                       problem[i][j]=sudoku[i][j];
                      // System.out.print(problem[i][j]+" ");
                   }
                   //System.out.println();
               }
               score_of_sudoku=score();
           }

       }
       else if(difficulty==2) {
           medium_sudoku();
           for(int i=0;i<9;i++)
           {
               for(int j=0;j<9;j++)
               {
                   problem[i][j]=sudoku[i][j];
                   //System.out.print(problem[i][j]+" ");
               }
               //System.out.println();
           }
           int sudoku_score=score();
           //System.out.println(sudoku_score+" is sudoku score");
           while(!(sudoku_score>49&&sudoku_score<53))
           {
               //sudoku=copy;
               //System.out.println("the generating sudoku is::");
               for(int i=0;i<9;i++)
               {
                   for(int j=0;j<9;j++)
                   {
                       sudoku[i][j]=copy[i][j];
                       //System.out.print(sudoku[i][j]+" ");
                   }
                   //System.out.println();
               }
               medium_sudoku();
               //System.out.println("problem sudoku is  ");
               for(int i=0;i<9;i++)
               {
                   for(int j=0;j<9;j++)
                   {
                       problem[i][j]=sudoku[i][j];
                       //System.out.print(problem[i][j]+" ");
                   }
                   //System.out.println();
               }
               sudoku_score=score();
           }
       }
       else {
           hard_sudoku();
           for(int i=0;i<9;i++)
           {
               for(int j=0;j<9;j++)
               {
                   problem[i][j]=sudoku[i][j];
                   //System.out.print(problem[i][j]+" ");
               }
               //System.out.println();
           }
           int score_sudoku=score();
           while(!(score_sudoku>56&&score_sudoku<60))
           {
               //sudoku=copy;
               for(int i=0;i<9;i++)
               {
                   for(int j=0;j<9;j++)
                   {
                       sudoku[i][j]=copy[i][j];
                   }
               }
               hard_sudoku();
               for(int i=0;i<9;i++)
               {
                   for(int j=0;j<9;j++)
                   {
                       problem[i][j]=sudoku[i][j];
                       //System.out.print(problem[i][j]+" ");
                   }
                   //System.out.println();
               }
               score_sudoku=score();
           }
       }
    }
    public int score()
    {
        int result=0;
        do {
            do {
                flag = false;
                result = result + CRME(0, 0);
            } while (flag);
            if (emp() != 0) {
                    flag = false;
                    result += twin_row(0);
                if(flag) continue;
            }
            if (emp() != 0) {
                    flag = false;
                    result += triplets_row(0);
                    result += triplets_column(0);
                    result += triplets_minigrid(0);
                if(flag) continue;
            }
            if (emp() != 0) {
                    flag = false;
                    result += bruteforce();
            }
        }while(flag);
        return result;
    }
    public int bruteforce()
    {
        int result=0;
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                int[][] temporary=sudoku;
                if(sudoku[i][j]==0)
                {
                    for(Integer t:map.get(i*10+j))
                    {
                        sudoku[i][j]=t;
                        int temp_result=score();
                        if(emp()==0)
                            return (result+1)*5;
                        else
                        {
                            sudoku=temporary;
                            result++;
                        }
                    }
                }
            }
        }
        return result*5;
    }
    public int triplets_minigrid(int grid)
    {
        if(grid>=9) return 0;
        int result=0;
        int r=(grid/3)*3;
        int c=(grid%3)*3;
        for(int i=r;i<r+3;i++)
        {
            for(int j=c;j<c+3;j++)
            {
             Integer key1=i*10+j;
             if(sudoku[i][j]==0&&map.get(key1).size()==3)
                 for(int k=i;k<r+3;k++)
                 {
                     for(int l=j+1;l<c+3;l++)
                     {
                         Integer key2=k*10+l;
                         if(sudoku[k][l]==0&&map.get(key2).size()==3&&map.get(key1).equals(map.get(key2)))
                             for(int m=k;m<r+3;m++)
                             {
                                 for(int n=l+1;n<c+3;n++)
                                 {
                                     Integer key3=m*10+n;
                                     if(sudoku[m][n]==0&&map.get(key3).size()==3&&map.get(key1).equals(map.get(key3)))
                                         elimination_minigrid_triplets(i,j,k,l,m,n,map.get(key3));
                                     result+=reduced();
                                 }
                             }
                     }
                 }
            }
        }
        return result*4+triplets_minigrid(grid+1);
    }
    public void elimination_minigrid_triplets(int row1,int col1,int row2,int col2,int row3,int col3,ArrayList<Integer> l)
    {
        int r=row1/3;
        int c=col1/3;
        for(int i=r;i<r+3;i++)
        {
            for(int j=c;j<c+3;j++)
            {
                if(sudoku[i][j]==0&&(i!=row1&&i!=row2&&i!=row3)&&(j!=col1&&j!=col2&&j!=col3))
                {
                    Integer key=i*10+j;
                    if(map.get(key).contains(l.get(0)))
                        map.get(key).remove(Integer.valueOf(l.get(0)));
                    if(map.get(key).contains(l.get(1)))
                        map.get(key).remove(Integer.valueOf(l.get(1)));
                    if(map.get(key).contains(l.get(2)))
                        map.get(key).remove(Integer.valueOf(l.get(2)));
                }
            }
        }
    }
    public int triplets_column(int num)
    {
        if(num>=9) return 0;
        int result=0;
        for(int i=0;i<9;i++)
        {
            Integer l1=i*10+num;
            if(sudoku[i][num]==0&&map.get(l1).size()==3)
                for(int j=i+1;j<9;j++)
                {
                    Integer l2=j*10+num;
                    if(sudoku[j][num]==0&&map.get(l2).size()==3&&map.get(l1).equals(map.get(l2)))
                        for(int k=j+1;k<9;k++)
                        {
                            Integer l3=k*10+num;
                            if(sudoku[k][num]==0&&map.get(l3).size()==3&&map.get(l3).equals(map.get(l3)))
                                elimination_triplets_col(num,i,j,k,map.get(l3));
                            result+=reduced();
                        }
                }
        }
        return result*4+triplets_column(num+1);
    }
    public void elimination_triplets_col(int col,int i1,int i2,int i3,ArrayList<Integer> l)
    {
        for(int i=0;i<9;i++)
        {
            if(sudoku[i][col]==0&&i!=i1&&i!=i2&&i!=i3)
            {
                Integer key=i*10+col;
                if(map.get(key).contains(l.get(0)))
                    map.get(key).remove(Integer.valueOf(l.get(0)));
                if(map.get(key).contains(l.get(1)))
                    map.get(key).remove(Integer.valueOf(l.get(1)));
                if(map.get(key).contains(l.get(2)))
                    map.get(key).remove(l.get(2));
            }
        }
    }
    public int triplets_row(int num)
    {
        if(num>=9) return 0;
        int result=0;
        for(int i=0;i<9;i++)
        {
            Integer l1=num*10+i;
            if(sudoku[num][i]==0&&map.get(l1).size()==3)
                for(int j=i+1;j<9;j++)
                {
                    Integer l2=num*10+j;
                    if(sudoku[num][j]==0&&map.get(l2).size()==3&&map.get(l1).equals(map.get(l2)))
                        for(int k=j+1;k<9;k++)
                        {
                            Integer l3=num*10+k;
                            if(sudoku[num][k]==0&&map.get(l3).size()==3&&map.get(l3).equals(map.get(l3)))
                                elimination_triplets_row(num,i,j,k,map.get(l3));
                            result+=reduced();
                        }
                }
        }
        return result*4+triplets_row(num+1);
    }
    public void elimination_triplets_row(int row,int i1,int i2,int i3,ArrayList<Integer> l)
    {
        for(int i=0;i<9;i++)
        {
            if(sudoku[row][i]==0&&i!=i1&&i!=i2&&i!=i3)
            {
                Integer key=row*10+i;
                if(map.get(key).contains(l.get(0)))
                    map.get(key).remove(Integer.valueOf(l.get(0)));
                if(map.get(key).contains(l.get(1)))
                    map.get(key).remove(Integer.valueOf(l.get(1)));
                if(map.get(key).contains(l.get(2)))
                    map.get(key).remove(l.get(2));
            }
        }
    }
    public int twin_row(int num)
    {
        if(num>=9)
            return 0;
        int result=0;
        for(int i=0;i<9;i++)
        {
            Integer l1=num*10+i;
            if(map.containsKey(l1))
                map.replace(l1,possible_values(num,i));
            if(sudoku[num][i]==0&&map.get(l1).size()==2)
            for(int j=i+1;j<9;j++)
            {
                Integer l2=num*10+j;
                if(map.containsKey(l2))
                    map.replace(l2,possible_values(num,j));
                if(sudoku[num][j]==0&&map.get(l2).size()==2&&map.get(l1).equals(map.get(l2)))
                    elimination_in_row(num,i,j,map.get(l2));
                result+=reduced();

            }
        }
        for(int i=0;i<9;i++)
        {
            Integer l1=i*10+num;
            if(map.containsKey(l1))
                map.replace(l1,possible_values(i,num));
            if(sudoku[i][num]==0&&map.get(l1).size()==2)
                for(int j=i+1;j<9;j++)
                {
                    Integer l2=j*10+num;
                    if(map.containsKey(l2))
                        map.replace(l2,possible_values(j,num));
                    if(sudoku[j][num]==0&&map.get(l2).size()==2&&map.get(l1).equals(map.get(l2)))
                        elimination_in_col(num,i,j,map.get(l2));
                    result+=reduced();
                }
        }
        int r=num/3;
        int c=num/3;
        for(int i=r;i<r+3;i++)
        {
            for(int j=c;j<c+3;j++)
            {
                Integer l1=i*10+j;
                if(map.containsKey(l1))
                    map.replace(l1,possible_values(i,j));
                if(sudoku[i][j]==0&&map.get(l1).size()==2)
                for(int k=r;k<r+3;k++)
                {
                    for(int l=j+1;l<c+3;l++)
                    {
                        Integer l2=k*10+l;
                        if(map.containsKey(l2))
                            map.replace(l2,possible_values(k,l));
                        if(sudoku[k][l]==0&&map.get(l2).size()==2&&map.get(l1).equals(map.get(l2)))
                            elimination_minigrid(i,j,k,l,map.get(l1));
                        result+=reduced();
                    }
                }
            }
        }

            return result*3+twin_row(num+1);
    }
    public int reduced()
    {
        int answer=0;
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                if(sudoku[i][j]==0)
                {
                    Integer l1=i*10+j;
                    if(sudoku[i][j]==0&&map.get(l1).size()==1)
                    {
                        sudoku[i][j]=map.get(l1).get(0);
                        flag=true;
                        answer+=1;
                    }
                }
            }
        }
        return answer;
    }
    public void elimination_in_row(int row,int index1,int index2,ArrayList<Integer> l1)
    {
        for(int i=0;i<9;i++)
        {
            Integer key=row*10+i;
            if(sudoku[row][i]==0)
            if(i!=index1&&i!=index2&&(map.get(key).contains(l1.get(0))||(map.get(key).contains(l1.get(1)))))
            {
                     map.get(key).remove(Integer.valueOf(l1.get(0)));
                     map.get(key).remove(Integer.valueOf(l1.get(1)));
            }
        }
    }
    public void elimination_in_col(int col,int index1,int index2,ArrayList<Integer> l1)
    {
        for(int i=0;i<9;i++)
        {
            Integer key=i*10+col;
            if(sudoku[i][col]==0)
            if(i!=index1&&i!=index2&&(map.get(key).contains(l1.get(0))||(map.get(key).contains(l1.get(1)))))
            {
                    map.get(key).remove(Integer.valueOf(l1.get(0)));
                    map.get(key).remove(Integer.valueOf(l1.get(1)));
            }
        }
    }
    public void elimination_minigrid(int row1,int col1,int row2,int col2,ArrayList<Integer> l2)
    {
        int r=row1/3;
        int c=col1/3;
        for(int i=r;i<r+3;i++)
        {
            for(int j=c;j<c+3;j++)
            {
                if(sudoku[i][j]==0)
                if((i!=row1&&j!=col1)&&(i!=row2&&j!=col2)&&(map.get(i*10+j).contains(l2.get(0))||map.get(i*10+j).contains(l2.get(1))))
                {
                        map.get(i*10+j).remove(Integer.valueOf(l2.get(0)));
                        map.get(i*10+j).remove(Integer.valueOf(l2.get(1)));
                }
            }
        }
    }
    public ArrayList<Integer> possible_values(int row,int column)
    {

        HashSet<Integer> set=new HashSet<>();
        for(int i=1;i<=9;i++)
            set.add(i);
        for(int i=0;i<9;i++)
        {
                set.remove(sudoku[row][i]);
                set.remove(sudoku[i][column]);
        }
        int r=row/3;
        int c=column/3;
        r=r*3;
        c=c*3;
        for(int i=r;i<r+3;i++)
        {
            for(int j=c;j<c+3;j++)
            {
                    set.remove(sudoku[i][j]);
            }
        }
        ArrayList<Integer> l2=new ArrayList<>(set);
        Collections.sort(l2);
        return l2;
    }

    public int CRME(int row,int column)
    {
        if(row>=9) return 0;
        if(column>=9) return CRME(row+1,0);
        HashSet<Integer> set=new HashSet<>();
        for(int i=1;i<=9;i++)
            set.add(i);
        for(int i=0;i<9;i++)
        {
                set.remove(sudoku[i][column]);
                set.remove(sudoku[row][i]);
        }
        int r=row/3;
        int c=column/3;
        r=r*3;
        c=c*3;
        for(int i=r;i<r+3;i++)
        {
            for(int j=c;j<c+3;j++)
            {
                    set.remove(sudoku[i][j]);
            }
        }
        if(set.size()==1)
        {
            if(sudoku[row][column]==0) {
                sudoku[row][column] = set.stream().findFirst().get();
                flag = true;
                return 1 + CRME(row, column + 1);
            }
        }
            return CRME(row,column+1);
    }
    public int emp()
    {
        int count=0;
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                if(sudoku[i][j]==0) count++;
            }
        }
        return count;
    }
    public void easy_sudoku()
    {
        int random=(int)(Math.random()*(45-40+1)+40);
        int count=1;
        while(count<=random)
        {
            int random1=(int)(Math.random()*(81)+0);
            if(sudoku[random1/9][random1%9]!=0) {
                sudoku[random1 / 9][random1 % 9] = 0;
                count++;
            }
        }
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++) {
               if(sudoku[i][j]==0) {
                   Integer list = i * 10 + j;
                   ArrayList<Integer> list2 = possible_values(i, j);
                   map.put(list, list2);
               }
            }
        }
        //System.out.println(map);
    }
    public void medium_sudoku()
    {
        int random=(int)(Math.random()*(46-49+1)+46);
        int count=1;
        while(count<=random)
        {
            int random1=(int)(Math.random()*(81));
            if(sudoku[random1/9][random1%9]!=0) {
                sudoku[random1 / 9][random1 % 9] = 0;
                count++;
            }
        }
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++) {
                if(sudoku[i][j]==0) {
                    Integer list = i * 10 + j;
                    ArrayList<Integer> list2 = possible_values(i, j);
                    map.put(list, list2);
                }
            }
        }
        //System.out.println(map);
    }
    public void hard_sudoku()
    {
        int random=(int)(Math.random()*(50-53+1)+53);
        int count=1;
        while(count<=random)
        {
            int random1=(int)(Math.random()*(81));
            if(sudoku[random1/9][random1%9]!=0) {
                sudoku[random1 / 9][random1 % 9] = 0;
                count++;
            }
        }
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++) {
                if(sudoku[i][j]==0)
                {
                    Integer list = i * 10 + j;
                    ArrayList<Integer> list2 = possible_values(i, j);
                    map.put(list, list2);
                }
            }
        }
        //System.out.println(map);
    }
    public boolean solve(int row,int column) {
        if (row >= 9)
            return true;
        for (int i = 1; i <= 9; i++) {
            int random = (int) (Math.random() * (9 - 1 + 1) + 1);
            sudoku[row][column] = random;
            if (row_correct(sudoku, row, column) && column_correct(sudoku, row, column) && mini_grid_correct(sudoku, row, column)) {
                if (column >= 8) {
                    if (solve(row + 1, 0))
                        return true;
                } else {
                    if (solve(row, column + 1))
                        return true;
                }
            } else
                sudoku[row][column] = 0;

        }
        return false;
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
