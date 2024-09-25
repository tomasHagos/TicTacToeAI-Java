import greenfoot.*;
import java.util.Random;
  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyWorld extends World
{

    private x object1;
    private o object2;
    private MouseInfo mouse;
    int player;
    boolean start = true;
    boolean start1 = false;
    int[][] list = {{0,0,0},{0,0,0},{0,0,0}};
    boolean winner = false;
    int draw = 0;
    int wait = 0;
    public MyWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(300, 300, 1); 
        setBackground(new GreenfootImage("board.jpg"));
        Random rand1 = new Random();
        player = rand1.nextInt(2) + 1;
       // int[][] list = {{0,0,0},{0,0,0},{0,0,0}};
    }
    public void act(){
        if(player == 1){
            showText("Player", 80,30);
        }
        else{
            showText("Computer", 80,30);
        }
        
        if(Greenfoot.mouseClicked(null)){
            mouse = Greenfoot.getMouseInfo();
        int[] positions = getRowCol(mouse.getX(), mouse.getY());
        if(list[positions[0]][positions[1]] == 0){
             draw++;
            if(player == 1){
            object1 = new x();
            object1.setImage("x.png");
            addObject(object1, positions[0],positions[1]);
            list[positions[0]][positions[1]] = 1;
            if(start) start1 = true;
            start = false;
            if(winner(list,player)){
                winner = true;
                Greenfoot.stop();
            }
            player = 2;
        }
    }

        //else{
           // object2 = new o();
            //object2.setImage("o.png");
            //addObject(object2,positions[0], positions[1]);
            //list[positions[0]][positions[1]] = 2;
            //if(winner(list,player)){
                //winner = true;
                //Greenfoot.stop();
           // }
           // player =1;
       // }
       // if(draw == 9){
          //  Greenfoot.stop();
        //}
        
        }
        if(player == 2){
            wait++;
            if(wait > 50){
            object2 = new o();
            object2.setImage("o.png"); 
            if(start){
            addObject(object2,1,1);
            list[1][1] = 2;
            start = false;
         }
         else{
            int[] ai = stupidAI(list,player,start1);
            addObject(object2,ai[0],ai[1]);
            list[ai[0]][ai[1]] = 2;
        }
        if(start1) start1= false;
            draw++;
            if(winner(list,player)){
                winner = true;
                Greenfoot.stop();
            }
            player = 1;
            wait = 0;
        }
            }
        if(draw == 9){
            Greenfoot.stop();
        }
    }
    public void stopped(){
         showText("", 80,30);
        if(winner){
        if(player == 1) player = 2;
        else player =1;
        if(player == 1)
        showText("Player"+ player +" Won", 60, 30);
        else
        showText("AI Won Take the L", 80,30);
    }
    else{
        showText("Draw", 60, 30);
    }
    }
    
    public int[] getRowCol(int x, int y){
        int row = y/102;
        int col = x/102;
        int[] returned = {row,col};
        return returned;
    }
    public void addObject(x object , int x, int y){
        super.addObject(object, ((y+1)*102)-51, ((x+1)*102)-51);
    }
    public void addObject( o object, int x, int y){
         super.addObject(object, ((y+1)*102)-51, ((x+1)*102)-51);
    }
    
    public boolean winner(int[][] list, int player){
        // row check
        for(int i=0; i < 3; i++){
            for(int j=0; j < 3; j++){
                if(list[i][j] != player) break;
                if( j == 2) return true;
                }
        }
        // column check
        for(int i = 0; i < 3; i++){
            for(int j=0; j < 3; j++){
                if(list[j][i] != player) break;
                if( j == 2) return true;
                }
        }
        // diagonal check
        for(int i=0; i < 3; i++){
            if(list[i][i] != player)break;
            if(i == 2) return true;
        }
        // diagonal check
        int i = 0; 
        int j = 2;
        int counter = 0;
        while(counter < 3){
            if(list[i][j] != player) break;
            i ++;
            j--;
            counter++;
            if(i == 3 && j == -1) return true;
        }
        return false;
    }
    public int[] stupidAI(int[][] list,int player, boolean started){
       if(started){
            int[][] list2 = {{0,0},{0,2},{2,0},{2,2}, {1,1}};
           if(list[1][1] == 1){
               Random rand2 = new Random();
               int nextpos = rand2.nextInt(4);
               return list2[nextpos];
           }
           else{
               return list2[4];
           }
        } 
       boolean found = false;
       Random rand = new Random();
       int row = 0;
       int col = 0;
       int[] position = defensiveOrOffensive(list,player);
       if(position[0] == -1 && position[1] == -1){ 
       position = defensiveOrOffensive(list,1);
        if(position[0] == -1 && position[1] == -1){
       while(!found){
          row = rand.nextInt(3);
          col = rand.nextInt(3);
         if(list[row][col] == 0) found = true;
       }
       position[0] = row;
       position[1] = col;
       return position;
    }
    else{
        return position;
    }
}
    else{
        return position;
    }
}
// this method is used by the AI. It uses to decide whether it has a winning position
// or whether there is a position that needs to be blocked.

    public int[] defensiveOrOffensive(int[][] list, int player){
        int row = -1;
        int col = -1;
        // row checks
        for(int i = 0; i < 3; i++){
            int count = 0;
            for(int j= 0; j < 3; j++){
                if(list[i][j] != player && list[i][j] != 0) break;
                if(list[i][j] == 0){
                    count++;
                    row = i;
                    col = j;
                }
                if(j == 2 && count == 1){
                    int[] returned = {row,col};
                    return returned;
                }
                }
        }
        //column checks
            for(int i=0; i < 3; i ++){
                int count = 0;
                for(int j=0; j < 3; j++){
                    if(list[j][i] != player && list[j][i] != 0) break;
                    if(list[j][i] == 0){
                        count++;
                        row = j;
                        col= i;
                    }
                    if( j == 2 && count == 1){
                    int[] returned = {row,col};
                    return returned;  
                    }
                }
            }
            // vertical checks
            int i = 0;
            int j = 0;
            int counter = 0;
            int count = 0;
            while( counter < 3){
                if(list[i][j] != player && list[i][j] != 0) break;
                if(list[i][j] == 0){
                    count++;
                    row = i;
                    col = j;
                }
                if(counter == 2 && count == 1){
                    int[] returned = {row,col};
                    return returned;  
                }
                i++;
                j++;
                counter++;
            }
             i = 0;
             j = 2;
             counter = 0;
             count = 0;
             while( counter < 3){
                if(list[i][j] != player && list[i][j] != 0) break;
                if(list[i][j] == 0){
                    count++;
                    row = i;
                    col = j;
                }
                if(counter == 2 && count == 1){
                    int[] returned = {row,col};
                    return returned;  
                }
                i++;
                j--;
                counter++;
            }
            int[] returned = {-1,-1};
            return returned;
        }
        public int[] twoWinPosition(int[][] list){
            int[] pos = {0,0};
         return pos;
        }
    }

    
