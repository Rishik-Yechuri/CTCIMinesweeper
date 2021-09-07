import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class CTCIMinesweeper {
    public static void main(String[] args) {
        try
        {
            CTCIMinesweeper obj = new CTCIMinesweeper();
            obj.run (args);
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
    }
    public void run(String[] args){
        MineSweeper newGame = new MineSweeper(10);
        newGame.startGame();
    }
}
class MineSweeper{
    int[][] holdMap;
     ArrayList<Integer> randomizedMines;
    int numOfUnclicked;
    int size;
    public MineSweeper(int tempSize){
        this.size = tempSize;
        holdMap = new int[size][size];
        int numOfMines = (int)(0.12 * Math.pow(size,2));
        randomizedMines = new ArrayList<Integer>();
        for(int x=0;x<numOfMines;x++){
            randomizedMines.add(1);
        }
        for(int x=0;x<Math.pow(size,2)-numOfMines;x++){
            randomizedMines.add(0);
        }
        numOfUnclicked = (int)Math.pow(size,2) - numOfMines;
        Collections.shuffle(randomizedMines);
        for(int y=0;y<size;y++){
            for(int x=0;x<size;x++){
                if(randomizedMines.remove(0) == 1){
                    holdMap[y][x] = -1;
                    if(y>0 && holdMap[y-1][x] != -1){
                        holdMap[y-1][x]++;
                    }
                    if(y+1<size && holdMap[y+1][x] != -1){
                        holdMap[y+1][x]++;
                    }
                    if(x>0 && holdMap[y][x-1] != -1){
                        holdMap[y][x-1]++;
                    }
                    if(x+1<size && holdMap[y][x+1] != -1){
                        holdMap[y][x+1]++;
                    }
                }else{
                    holdMap[y][x] = 0;
                }
            }
        }
    }
    public void startGame(){
        String[][] hiddenMap = new String[size][size];
        for(int y=0;y<size;y++){
            for(int x=0;x<size;x++){
                hiddenMap[y][x] =  "x";
            }
        }
        Scanner scanner = new Scanner(System.in);
        boolean keepGoing = true;
        while(numOfUnclicked > 0 && keepGoing){
            for(int y=0;y<size;y++){
                System.out.println();
                for(int x=0;x<size;x++){
                    System.out.print(hiddenMap[y][x] + " ");
                }
            }
            System.out.println();
            System.out.print("Enter Row:");
            int row = scanner.nextInt();
            System.out.print("Enter Column:");
            int column = scanner.nextInt();
            if(holdMap[row][column] == -1){
                System.out.println("You Lost");
                keepGoing = false;
            }else if(holdMap[row][column] > 0){
                hiddenMap[row][column] = String.valueOf(holdMap[row][column]);
                numOfUnclicked--;
            }else{
                numOfUnclicked-=removeZeroes(hiddenMap,holdMap,0,row,column);
            }
            if(numOfUnclicked == 0){
                System.out.println("You Win");
            }
        }
    }
    public int removeZeroes(String[][] hidden,int[][] map,int numDiscovered,int y,int x){
        if(map[y][x] == 0){
            numDiscovered++;
            hidden[y][x] = "0";
        }
        if(y>0 && hidden[y-1][x].equals("x") && map[y-1][x] == 0){
            numDiscovered = removeZeroes(hidden,map,numDiscovered,y-1,x);
        }
        if(y+1 < size && hidden[y+1][x].equals("x") && map[y+1][x] == 0){
            numDiscovered = removeZeroes(hidden,map,numDiscovered,y+1,x);
        }
        if(x>0 && hidden[y][x-1].equals("x") && map[y][x-1] == 0){
            numDiscovered = removeZeroes(hidden,map,numDiscovered,y,x-1);
        }
        if(x+1 < size && hidden[y][x+1].equals("x") && map[y][x+1] == 0){
            numDiscovered = removeZeroes(hidden,map,numDiscovered,y,x+1);
        }
        return numDiscovered;
    }
}