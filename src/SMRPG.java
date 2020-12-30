//Saad Makrod
//Mr Jay
//ICS 4U1

import java.awt.*;
import javax.imageio.*; // allows image loading
import java.io.*; // allows file access
import javax.swing.*;
import java.awt.event.*;  // Needed for ActionListener
import javax.sound.sampled.*;

public class SMRPG {//SMRPG Class

    public static void main(String[] args) throws IOException {//Main method
        RPG rg = new RPG();
        rg.setVisible(true);
    }//end method
}//end class

class RPG extends JFrame implements KeyListener {//RPG class
    Life life = new Life();//inner classes with timers connected
    Spawn spawn = new Spawn();
    Points points = new Points();
    Ammo ammo = new Ammo();
    Move move = new Move();
    Shot shot = new Shot();
    Slash slash = new Slash();
    Boss boss = new Boss();
    Sound sound = new Sound();//class for music
    static Timer t, t2, t3, t4, t5, t6, t7, t8;//timers so that i can spawn objects (monsters, lives, ammo...)
    Map map = new Map(40, 40, 20, 20);//map class object
    JLabel pointsCounter = new JLabel("Points: 0");
    JLabel ammoCounter = new JLabel("Magic: 3");
    JLabel livesCounter = new JLabel("Lives: 3");
    JLabel swordCounter = new JLabel("Sword Slashes: 0");
    JLabel treasure = new JLabel("Treasure: 0");

    RPG() throws IOException {//constructor
        addKeyListener(this);//key listener
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        JLabel character = new JLabel("<html><br/>Character<br/><br/><html>", SwingConstants.CENTER);//center and format jlabels accordingly
        JLabel inventory = new JLabel("<html><br/>Inventory<br/><br/><html>", SwingConstants.CENTER);
        JLabel inst = new JLabel("<html><br/>Instructions<br/><html>", SwingConstants.CENTER);
        JLabel ins = new JLabel("<html><br/>1. Press Space to Play<html>");
        JLabel ins2 = new JLabel("2. Move With Arrow Key");
        JLabel ins3 = new JLabel("3. Press F to Shoot Fire");
        JLabel ins4 = new JLabel("4. Press E to Pause");
        JLabel ins5 = new JLabel("<html>5.There is an 20% Chance<br/>to Kill a Monster Without<br/>a Sword and 70%" +
                " Chance to kill a Monster With a Sword<html>");
        JLabel ins6 = new JLabel("<html>6. A Boss Monster Will Spawn Every Two<br/>Minutes if there is Not One on the Map Already.<br/>" +
                "Each Boss Monster has 10 Lives. Have Fun!<html>");
        JLabel ins7 = new JLabel("<html>7. Collect Treasure and<br/>Survive!<html>");

        DrawArea d = new DrawArea(800, 800);

        JPanel west = new JPanel();
        JPanel content = new JPanel();

        west.setLayout(new BoxLayout(west, BoxLayout.Y_AXIS));

        character.setFont(new Font("Centaur", Font.BOLD, 20));//set font jlabels
        pointsCounter.setFont(new Font("Centaur", Font.PLAIN, 20));
        livesCounter.setFont(new Font("Centaur", Font.PLAIN, 20));
        inventory.setFont(new Font("Centaur", Font.BOLD, 20));
        treasure.setFont(new Font("Centaur", Font.PLAIN, 20));
        ammoCounter.setFont(new Font("Centaur", Font.PLAIN, 20));
        swordCounter.setFont(new Font("Centaur", Font.PLAIN, 20));
        inst.setFont(new Font("Centaur", Font.BOLD, 20));
        ins.setFont(new Font("Centaur", Font.PLAIN, 20));
        ins2.setFont(new Font("Centaur", Font.PLAIN, 20));
        ins3.setFont(new Font("Centaur", Font.PLAIN, 20));
        ins4.setFont(new Font("Centaur", Font.PLAIN, 20));
        ins5.setFont(new Font("Centaur", Font.PLAIN, 20));
        ins6.setFont(new Font("Centaur", Font.PLAIN, 20));
        ins7.setFont(new Font("Centaur", Font.PLAIN, 20));

        west.add(character);
        west.add(pointsCounter);
        west.add(livesCounter);
        west.add(inventory);
        west.add(treasure);
        west.add(ammoCounter);
        west.add(swordCounter);
        west.add(inst);
        west.add(ins);
        west.add(ins2);
        west.add(ins3);
        west.add(ins4);
        west.add(ins5);
        west.add(ins6);
        west.add(ins7);

        west.setPreferredSize(new Dimension(200, 800));

        content.add(west, "West");
        content.add(d, "East");

        setContentPane(content);
        pack();
        setTitle("RPG");
        setSize(1100, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);           // Center window.

        d.addKeyListener(this);

        t = new Timer(60000, life);//timers set in milliseconds
        t2 = new Timer(30000, this.ammo);
        t3 = new Timer(10000, this.points);
        t4 = new Timer(5000, spawn);
        t5 = new Timer(500, move);
        t6 = new Timer(200, shot);
        t7 = new Timer(45000, slash);
        t8 = new Timer(120000, boss);
    }// end constructoe

    //Mandatory methods
    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP://if up pressed
                map.move(0, -1);
                break;
            case KeyEvent.VK_DOWN://down pressed
                map.move(0, 1);
                break;
            case KeyEvent.VK_LEFT://left pressed
                map.move(-1, 0);
                break;
            case KeyEvent.VK_RIGHT://right presses
                map.move(1, 0);
                break;
            case KeyEvent.VK_F://f presses
                map.shoot();
                break;
            case KeyEvent.VK_SPACE://space presses
                t.start();//all timer started
                t2.start();
                t3.start();
                t4.start();
                t5.start();
                t6.start();
                t7.start();
                t8.start();
                sound.playMusic();//music played
                break;
            case KeyEvent.VK_E://if e pressed
                t.stop();//all timers stopped
                t2.stop();
                t3.stop();
                t4.stop();
                t5.stop();
                t6.stop();
                t7.stop();
                t8.stop();
                sound.getClip().stop();//music stopped
                break;
        }
        repaint();
    }

    public void keyReleased(KeyEvent e) {
    }

    class DrawArea extends JPanel {// drawarea inner class

        public DrawArea(int width, int height) {
            this.setPreferredSize(new Dimension(width, height)); // size
        }//set size

        public void paintComponent(Graphics g) {
            if (map.isRunning()) {//if the map is still running (player has lives)
                pointsCounter.setText("Points: " + map.getPoints());//get all the player stats
                ammoCounter.setText("Magic: " + map.getAmmo());
                livesCounter.setText("Lives: " + map.getLives());
                swordCounter.setText("Sword Slashes: " + map.getSlashes());
                treasure.setText("Treasure: " + map.getTreasure());
            } else {//player out of lives -> game finished
                sound.getClip().stop();
                sound.getGameOver().start();
            }
            map.print(g);
        }
    }

    class Life implements ActionListener {//life class

        public void actionPerformed(ActionEvent e) {// add life according to timer
            map.addLife();
            repaint();
        }
    }//end class

    class Points implements ActionListener {//points class

        public void actionPerformed(ActionEvent e) {//add points according to timer
            map.addPoints();
            repaint();
        }
    }//end class

    class Spawn implements ActionListener {//spawn class

        public void actionPerformed(ActionEvent e) {//spawn monster according to timer
            map.spawnMonster();
            repaint();
        }
    }//end class

    class Ammo implements ActionListener {//ammo class

        public void actionPerformed(ActionEvent e) {//add ammo according to timer
            map.addAmmo();
            repaint();
        }
    }//end class

    class Move implements ActionListener {//move class

        public void actionPerformed(ActionEvent e) {//move monsters according to timer
            map.moveMonster();
            repaint();
        }
    }//end class

    class Shot implements ActionListener {//shot class

        public void actionPerformed(ActionEvent e) {//shot moved according to timer
            map.moveShot();
            repaint();
        }
    }//end class

    class Slash implements ActionListener {//slash class

        public void actionPerformed(ActionEvent e) {//sword spawned according to timer
            map.addSword();
            repaint();
        }
    }//end class

    class Boss implements ActionListener {//boss class

        public void actionPerformed(ActionEvent e) {//boss added according to timer
            map.addBoss();
            repaint();
        }
    }//end class
}//end class

class Map {//map class --> HANDLES ALL MOVEMENT AND PRINTS MAP GETS IMAGES, SOUND, AND STATS FROM OTHER CLASSES
    private char[][] grid, moveMons;
    private boolean[][] canMove;
    private int width, height, r = 1, c = 1, lastX = 2, lastY = 1, counter = 0;
    private boolean running = true;
    BossMonster boss = new BossMonster();//class objects for main player, boss, images, music
    Sound sound = new Sound();
    Images image = new Images();
    Player player = new Player();

    Map(int r, int c, int width, int height) throws IOException {//constructor
        this.width = width;
        this.height = height;

        //three main arrays used throughout
        grid = new char[r][c];//record what goes where (monster, trees, player, lives, ammo...)
        moveMons = new char[r][c];//records movement of player, fireball, monsters
        canMove = new boolean[r][c];//check if the things that move have moved already in their turn, can move only once per time method is called

        for (int i = 0; i < r; i++) {//double for loop set the whole canMove array to true
            for (int x = 0; x < c; x++) {
                canMove[i][x] = true;
            }
        }

        for (int i = 0; i < r; i++) {//double for loop will set up the basic map with trees around the border
            for (int x = 0; x < c; x++) {
                if (i == 0 || i == r - 1 || x == 0 || x == c - 1) {
                    grid[i][x] = 'T';
                } else {
                    grid[i][x] = 'S';
                }
            }
        }

        for (int i = 0; i < r; i++) {//double for loop will set all movemons array to E for no movement
            for (int x = 0; x < c; x++) {
                moveMons[i][x] = 'E';
            }
        }

        for (int i = 0; i < 20; i++) {// for loop to spawn things for the beginning
            int row = (int) (Math.random() * (grid.length - 2) + 1);//20 monsters spawned random positions
            int col = (int) (Math.random() * (grid[0].length - 2) + 1);
            grid[row][col] = 'M';

            int z = (int) (Math.random() * 4) + 1;

            if (z == 1) {//random direction assigned based off of z
                moveMons[row][col] = 'U';
            } else if (z == 2) {
                moveMons[row][col] = 'D';
            } else if (z == 3) {
                moveMons[row][col] = 'L';
            } else {
                moveMons[row][col] = 'R';
            }

            if (i < 10) {//10 treasures added at random positions
                int x = (int) (Math.random() * (grid.length - 2) + 1);
                int y = (int) (Math.random() * (grid[0].length - 2) + 1);
                grid[x][y] = 'P';
            }

            if (i < 15) {//15 trees spawned at random positions
                int x = (int) (Math.random() * (grid.length - 2) + 1);
                int y = (int) (Math.random() * (grid[0].length - 2) + 1);
                grid[x][y] = 'T';
            }
        }
    }//end constructor

    public void print(Graphics g) {//print method
        if (player.getLives() > 0) {//if the player has lives
            running = true;//the game is running

            for (int row = 0; row < grid.length; row++) { // number of rows
                for (int col = 0; col < grid[0].length; col++) { // length of first row
                    if (grid[row][col] == 'L') // Life
                        g.drawImage(image.getImage(8), col * width, row * height, null);
                    else if (grid[row][col] == 'M') { // monster
                        if (moveMons[row][col] == 'U')//check its direction will output the back
                            g.drawImage(image.getImage(11), col * width, row * height, null);
                        else if (moveMons[row][col] == 'D')//output front
                            g.drawImage(image.getImage(4), col * width, row * height, null);
                        else if (moveMons[row][col] == 'L')//output left
                            g.drawImage(image.getImage(12), col * width, row * height, null);
                        else//output right
                            g.drawImage(image.getImage(13), col * width, row * height, null);
                    } else if (grid[row][col] == 'B') { // boss monster
                        if (moveMons[row][col] == 'U')//check its direction will output the back
                            g.drawImage(boss.getImage(1), col * width, row * height, null);
                        else if (moveMons[row][col] == 'D')//output front
                            g.drawImage(boss.getImage(0), col * width, row * height, null);
                        else if (moveMons[row][col] == 'L')//output left
                            g.drawImage(boss.getImage(2), col * width, row * height, null);
                        else//output right
                            g.drawImage(boss.getImage(3), col * width, row * height, null);
                    } else if (grid[row][col] == 'T') // Tree
                        g.drawImage(image.getImage(6), col * width, row * height, null);
                    else if (grid[row][col] == 'A') // Ammo
                        g.drawImage(image.getImage(5), col * width, row * height, null);
                    else if (grid[row][col] == 'S') // space will erase what was there and put grass
                        g.drawImage(image.getImage(7), col * width, row * height, null);
                    else if (grid[row][col] == 'P')//count score icon
                        g.drawImage(image.getImage(9), col * width, row * height, null);
                    else if (grid[row][col] == 'F')//fireball
                        g.drawImage(image.getImage(10), col * width, row * height, null);
                    else if (grid[row][col] == 'K') // Sword
                        g.drawImage(image.getImage(15), col * width, row * height, null);
                }
            }
            //PLAYER DRAWN
            if (lastX > c) {// if the lastx position is greater then the current col output left
                g.drawImage(image.getImage(2), c * width, r * height, null);
            } else if (lastX < c) {//output right
                g.drawImage(image.getImage(3), c * width, r * height, null);
            } else if (lastY > r) {//if the lasty pos is greater then the current row ouput front
                g.drawImage(image.getImage(1), c * width, r * height, null);
            } else {//output back
                g.drawImage(image.getImage(0), c * width, r * height, null);
            }
        } else {//if no lives
            running = false;//game not running

            g.setColor(Color.white);//background is white

            for (int i = 0; i < grid.length; i++) {//double for loop
                for (int j = 0; j < grid[0].length; j++) {
                    g.fillRect(j * width, i * height, width, height); //all of the map is white
                }
            }

            g.drawImage(image.getImage(14), 50, 200, null);//draw gameover pic
        }
    }//end method

    public void move(int x, int y) {//move player method
        try {
            lastX = c;//record last row and col
            lastY = r;
            if (grid[r + y][c + x] == 'S') {//space
                r += y;//move to new pos
                c += x;
            } else if (grid[r + y][c + x] == 'M') {//if the next place has a monster
                if (player.getSlashes() > 0) {//check if player has any sword slashes
                    sound.loadSlash();//load and play sword sound
                    sound.getSlash().start();
                    if (Math.random() > 0.3) {//70% to kill monster with sword
                        grid[r + y][c + x] = 'S';//place becomes empty
                        moveMons[r + y][c + x] = 'E';
                        player.setPoints(player.getPoints() + 100);//get 100 points
                    } else {//slash failed
                        sound.loadGrunt();//load and play grunt sound
                        sound.getGrunt().start();
                        player.setLives(player.getLives() - 1);//lose life
                    }
                    player.setSlashes(player.getSlashes() - 1);//lose slash
                } else {//no sword slash
                    sound.loadGrunt();//load and play grunt
                    sound.getGrunt().start();
                    if (Math.random() > 0.8) {//20% chance to kill
                        grid[r + y][c + x] = 'S';
                        moveMons[r + y][c + x] = 'E';
                        player.setPoints(player.getPoints() + 100);
                    }
                    player.setLives(player.getLives() - 1);//lose life no matter what
                }
            } else if (grid[r + y][c + x] == 'L') {//life
                sound.loadPickup();//play pickup sound
                sound.getPickup().start();
                grid[r + y][c + x] = 'S';
                player.setLives(player.getLives() + 1);//get life
                player.setPoints(player.getPoints() + 100);
            } else if (grid[r + y][c + x] == 'A') {//ammo
                sound.loadPickup();
                sound.getPickup().start();
                grid[r + y][c + x] = 'S';
                player.setAmmo(player.getAmmo() + 3);//get ammo
                player.setPoints(player.getPoints() + 10);
            } else if (grid[r + y][c + x] == 'P') {//treasure
                sound.loadPickup();
                sound.getPickup().start();
                grid[r + y][c + x] = 'S';
                player.setPoints(player.getPoints() + 20);
                player.setTreasure(player.getTreasure() + 25);//get treasure
            } else if (grid[r + y][c + x] == 'K') {//sword
                sound.loadPickup();
                sound.getPickup().start();
                grid[r + y][c + x] = 'S';
                player.setPoints(player.getPoints() + 20);
                player.setSlashes(player.getSlashes() + 3);//get slashes
            } else if (grid[r + y][c + x] == 'B') {//boss monster --> logic is the same as monster except boss monster has 10 lives
                if (player.getSlashes() > 0) {
                    sound.loadSlash();
                    sound.getSlash().start();
                    if (Math.random() > 0.3) {//70% chance to take out boss life
                        boss.setLives(boss.getLives() - 1);//boss lose life
                        if (boss.getLives() == 0) {//if boss has 0 lives
                            grid[r + y][c + x] = 'S';
                            moveMons[r + y][c + x] = 'E';
                        }
                        player.setPoints(player.getPoints() + 100);//100 points for each hit
                    } else {
                        sound.loadGrunt();
                        sound.getGrunt().start();
                        player.setLives(player.getLives() - 1);//if hit fails player loses life
                    }
                    player.setSlashes(player.getSlashes() - 1);//lose slash
                } else {//no slashes
                    sound.loadGrunt();
                    sound.getGrunt().start();
                    if (Math.random() > 0.8) {//20% chance to land successful hit
                        boss.setLives(boss.getLives() - 1);
                        if (boss.getLives() == 0) {
                            grid[r + y][c + x] = 'S';
                            moveMons[r + y][c + x] = 'E';
                        }
                        player.setPoints(player.getPoints() + 100);
                    }
                    player.setLives(player.getLives() - 1);
                }
            }
        } catch (Exception ignored) {//catch in case position cannot be found
        }
    }//end method

    //THE FOLLOWING ADD AND MOVEMENT CLASSES ARE ALL CALLED BY THE TIMERS IN THE RPG CLASS

    public void spawnMonster() {//spawnMonster method
        int row = (int) (Math.random() * (grid.length - 2) + 1);//random row and col
        int col = (int) (Math.random() * (grid[0].length - 2) + 1);
        int count = 0;

        for (int i = 0; i < grid.length; i++) {//double loop to find out how many monsters there are
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 'M' || grid[i][j] == 'B')
                    count = count + 1;
            }
        }

        if (grid[row][col] == 'S' && count <= 40) {//monster count must be below 40 and place must be free
            grid[row][col] = 'M';//add monster

            int z = (int) (Math.random() * 4) + 1;

            if (z == 1) {//assign random direction based off of z
                moveMons[row][col] = 'U';
            } else if (z == 2) {
                moveMons[row][col] = 'D';
            } else if (z == 3) {
                moveMons[row][col] = 'L';
            } else {
                moveMons[row][col] = 'R';
            }
        }
    }//end method

    public void addBoss() {//addboss method --> same logic as spawnMonster method
        int x = 0;
        int row = (int) (Math.random() * (grid.length - 2) + 1);
        int col = (int) (Math.random() * (grid[0].length - 2) + 1);

        for (int i = 0; i < grid.length; i++) {//check if boss is in map
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 'B')
                    x = 1;
            }
        }

        if (grid[row][col] == 'S' && x != 1) {//if no boss and space is free
            grid[row][col] = 'B';//add boss
            boss.setLives(10);//reset lives to 10

            int z = (int) (Math.random() * 4) + 1;

            if (z == 1) {
                moveMons[row][col] = 'U';
            } else if (z == 2) {
                moveMons[row][col] = 'D';
            } else if (z == 3) {
                moveMons[row][col] = 'L';
            } else {
                moveMons[row][col] = 'R';
            }
        }
    }//end method

    public void addAmmo() {//add ammo
        int row = (int) (Math.random() * (grid.length - 2) + 1);
        int col = (int) (Math.random() * (grid[0].length - 2) + 1);
        grid[row][col] = 'A';
    }

    public void addSword() {//add sword
        int row = (int) (Math.random() * (grid.length - 2) + 1);
        int col = (int) (Math.random() * (grid[0].length - 2) + 1);
        grid[row][col] = 'K';
    }

    public void addPoints() {//add points
        int row = (int) (Math.random() * (grid.length - 2) + 1);
        int col = (int) (Math.random() * (grid[0].length - 2) + 1);
        grid[row][col] = 'P';
    }

    public void addLife() {//add life
        int row = (int) (Math.random() * (grid.length - 2) + 1);
        int col = (int) (Math.random() * (grid[0].length - 2) + 1);
        grid[row][col] = 'L';
    }

    public void moveMonster() {//move monster
        for (int i = 0; i < r; i++) {//set all monsters able to move
            for (int x = 0; x < c; x++) {
                canMove[i][x] = true;
            }
        }

        if (counter % 5 == 0) {//if the monsters have moved 5 steps they will change direction
            for (int i = 0; i < grid.length; i++) {//double for loop will run for the whole grid
                for (int j = 0; j < grid[0].length; j++) {
                    if (grid[i][j] == 'M' || grid[i][j] == 'B') {
                        int x = (int) (Math.random() * 4) + 1;
                        if (x == 1) {//assign direction based off random number x
                            moveMons[i][j] = 'L';
                        } else if (x == 2) {
                            moveMons[i][j] = 'R';
                        } else if (x == 3) {
                            moveMons[i][j] = 'U';
                        } else {
                            moveMons[i][j] = 'D';
                        }
                    }
                }
            }
        }

        for (int i = 0; i < grid.length; i++) {//double for loop will run for grid
            for (int x = 0; x < grid[0].length; x++) {
                if (grid[i][x] == 'M' || grid[i][x] == 'B') {//send each monster to the appropriate method for their direction
                    if (moveMons[i][x] == 'U') {
                        moveUp(i, x);
                    } else if (moveMons[i][x] == 'D') {
                        moveDown(i, x);
                    } else if (moveMons[i][x] == 'L') {
                        moveLeft(i, x);
                    } else if (moveMons[i][x] == 'R') {
                        moveRight(i, x);
                    }
                }
            }
        }
        counter++;//add step
    }//end method

    public void moveUp(int row, int col) {//moveup method
        if (canMove[row][col]) {//if allowed to move
            canMove[row][col] = true;//set current place as available to move
            canMove[row - 1][col] = false;//place moving to can no longer move
            if (row - 1 == r && col == c) {//if moving into player, monster will disappear player move method will account for the life lost
                grid[row][col] = 'S';
                moveMons[row][col] = 'E';
            } else if (grid[row - 1][col] == 'S' && row > 1 && canMove[row][col]) {//if there is a space and monster can move
                if ((grid[row - 1][col - 1] == 'M' || grid[row - 1][col - 1] == 'B') && moveMons[row - 1][col - 1] == 'R' && canMove[row][col]) {//if there is a monster to the left which is moving into the same position
                    if (grid[row + 1][col] == 'S' && row < grid.length - 2) {//the spot is already taken so change direction
                        moveMons[row][col] = 'D';
                        moveDown(row, col);
                    } else if (grid[row][col + 1] == 'S' && col < grid[0].length - 2 && canMove[row][col]) {
                        moveMons[row][col] = 'R';
                        moveRight(row, col);
                    } else if (grid[row][col - 1] == 'S' && col > 1 && canMove[row][col]) {
                        moveMons[row][col] = 'L';
                        moveLeft(row, col);
                    }
                } else if ((grid[row - 2][col] == 'M' || grid[row - 2][col] == 'B') && moveMons[row - 2][col] == 'D' && canMove[row][col]) {//if monster above is moving into same position
                    if (grid[row + 1][col] == 'S' && row < grid.length - 2) {//sot is taken change position
                        moveMons[row][col] = 'D';
                        moveDown(row, col);
                    } else if (grid[row][col + 1] == 'S' && col < grid[0].length - 2 && canMove[row][col]) {
                        moveMons[row][col] = 'R';
                        moveRight(row, col);
                    } else if (grid[row][col - 1] == 'S' && col > 1 && canMove[row][col]) {
                        moveMons[row][col] = 'L';
                        moveLeft(row, col);
                    }
                } else if ((grid[row - 1][col + 1] == 'M' || grid[row - 1][col + 1] == 'B') && moveMons[row - 1][col + 1] == 'L' && canMove[row][col]) {//if there is monster to right moving into same position
                    if (grid[row + 1][col] == 'S' && row < grid.length - 2) {//spot taken change direction
                        moveMons[row][col] = 'D';
                        moveDown(row, col);
                    } else if (grid[row][col + 1] == 'S' && col < grid[0].length - 2 && canMove[row][col]) {
                        moveMons[row][col] = 'R';
                        moveRight(row, col);
                    } else if (grid[row][col - 1] == 'S' && col > 1 && canMove[row][col]) {
                        moveMons[row][col] = 'L';
                        moveLeft(row, col);
                    }
                } else if (canMove[row][col] && grid[row][col] == 'M') {//position not taken, if it is reg monster
                    grid[row - 1][col] = 'M';
                    grid[row][col] = 'S';
                    moveMons[row - 1][col] = 'U';
                    moveMons[row][col] = 'E';
                } else if (canMove[row][col] && grid[row][col] == 'B') {//position not taken if it is boss monster
                    grid[row - 1][col] = 'B';
                    grid[row][col] = 'S';
                    moveMons[row - 1][col] = 'U';
                    moveMons[row][col] = 'E';
                }
            } else if (grid[row + 1][col] == 'S' && row < grid.length - 2) {//if no space in direction then switch direction (due to trees)
                moveMons[row][col] = 'D';
                moveDown(row, col);
            } else if (grid[row][col + 1] == 'S' && col < grid[0].length - 2) {
                moveMons[row][col] = 'R';
                moveRight(row, col);
            } else if (grid[row][col - 1] == 'S' && col > 1) {
                moveMons[row][col] = 'L';
                moveLeft(row, col);
            }
        }
    }//end method

    public void moveDown(int row, int col) {//movedown method
        if (canMove[row][col]) {//if place can move
            canMove[row + 1][col] = false;//place moving into can no longer move
            canMove[row][col] = true;//place leaving can now move
            if (row + 1 == r && col == c) {//if moving into player
                grid[row][col] = 'S';
                moveMons[row][col] = 'E';
            } else if (grid[row + 1][col] == 'S' && row < grid.length - 2 && canMove[row][col] && grid[row][col] == 'M') {//if within bounds and reg monster
                grid[row + 1][col] = 'M';
                grid[row][col] = 'S';
                moveMons[row + 1][col] = 'D';
                moveMons[row][col] = 'E';
            } else if (grid[row + 1][col] == 'S' && row < grid.length - 2 && canMove[row][col] && grid[row][col] == 'B') {//if within bounds and boss monster
                grid[row + 1][col] = 'B';
                grid[row][col] = 'S';
                moveMons[row + 1][col] = 'D';
                moveMons[row][col] = 'E';
            } else if (grid[row - 1][col] == 'S' && row > 1 && canMove[row][col]) {//no space switch direction
                moveMons[row][col] = 'U';
                moveUp(row, col);
            } else if (grid[row][col - 1] == 'S' && col > 1 && canMove[row][col]) {
                moveMons[row][col] = 'L';
                moveLeft(row, col);
            } else if (grid[row][col + 1] == 'S' && col < grid[0].length - 2 && canMove[row][col]) {
                moveMons[row][col] = 'R';
                moveRight(row, col);
            }
        }
    }//end method

    public void moveRight(int row, int col) {//move right method
        if (canMove[row][col]) {//if can move
            canMove[row][col] = true;//switch can move with place moving into
            canMove[row][col + 1] = false;
            if (row == r && col + 1 == c) {//if moving into player
                grid[row][col] = 'S';
                moveMons[row][col] = 'E';
            } else if (grid[row][col + 1] == 'S' && col < grid[0].length - 2 && canMove[row][col]) {//if there is space
                if ((grid[row - 1][col + 1] == 'M' || grid[row - 1][col + 1] == 'B') && moveMons[row - 1][col + 1] == 'D') {//if monster is moving down into same position
                    if (grid[row][col - 1] == 'S' && col > 1) {//spot taken change direction
                        moveMons[row][col] = 'L';
                        moveLeft(row, col);
                    } else if (grid[row + 1][col] == 'S' && row < grid.length - 2) {
                        moveMons[row][col] = 'D';
                        moveDown(row, col);
                    } else if (grid[row - 1][col] == 'S' && row > 1) {
                        moveMons[row][col] = 'U';
                        moveUp(row, col);
                    }
                } else if (grid[row][col] == 'M') {//if monster and space is free
                    grid[row][col + 1] = 'M';
                    grid[row][col] = 'S';
                    moveMons[row][col + 1] = 'R';
                    moveMons[row][col] = 'E';
                } else if (grid[row][col] == 'B') {//if boss monster and space is free
                    grid[row][col + 1] = 'B';
                    grid[row][col] = 'S';
                    moveMons[row][col + 1] = 'R';
                    moveMons[row][col] = 'E';
                }
            } else if (grid[row][col - 1] == 'S' && col > 1) {//no space change direction
                moveMons[row][col] = 'L';
                moveLeft(row, col);
            } else if (grid[row + 1][col] == 'S' && row < grid.length - 2) {
                moveMons[row][col] = 'D';
                moveDown(row, col);
            } else if (grid[row - 1][col] == 'S' && row > 1) {
                moveMons[row][col] = 'U';
                moveUp(row, col);
            }
        }
    }//end method

    public void moveLeft(int row, int col) {//moveleft method
        if (canMove[row][col]) {//if canmove
            canMove[row][col - 1] = false;
            canMove[row][col] = true;
            if (row == r && col - 1 == c) {//if moving into player
                grid[row][col] = 'S';
                moveMons[row][col] = 'E';
            } else if (grid[row][col - 1] == 'S' && col > 1) {//if there is space
                if ((grid[row - 1][col - 1] == 'M' || grid[row - 1][col - 1] == 'B') && moveMons[row - 1][col - 1] == 'D') {//if there is a monster moving down into the same position
                    if (grid[row][col + 1] == 'S' && col < grid[0].length - 2) {//change direction
                        moveMons[row][col] = 'R';
                        moveRight(row, col);
                    } else if (grid[row + 1][col] == 'S' && row < grid.length - 2) {
                        moveMons[row][col] = 'D';
                        moveDown(row, col);
                    } else if (grid[row - 1][col] == 'S' && row > 1) {
                        moveMons[row][col] = 'U';
                        moveUp(row, col);
                    }
                } else if ((grid[row][col - 2] == 'M' || grid[row][col - 2] == 'B') && moveMons[row][col - 2] == 'R') {//if there is a monster moving right into the same position
                    if (grid[row][col + 1] == 'S' && col < grid[0].length - 2) {//change direction
                        moveMons[row][col] = 'R';
                        moveRight(row, col);
                    } else if (grid[row + 1][col] == 'S' && row < grid.length - 2) {
                        moveMons[row][col] = 'D';
                        moveDown(row, col);
                    } else if (grid[row - 1][col] == 'S' && row > 1) {
                        moveMons[row][col] = 'U';
                        moveUp(row, col);
                    }
                } else if (canMove[row][col] && grid[row][col] == 'M') {//if reg monster and space is free
                    grid[row][col - 1] = 'M';
                    grid[row][col] = 'S';
                    moveMons[row][col - 1] = 'L';
                    moveMons[row][col] = 'E';
                } else if (canMove[row][col] && grid[row][col] == 'B') {//if boss monster and space if free
                    grid[row][col - 1] = 'B';
                    grid[row][col] = 'S';
                    moveMons[row][col - 1] = 'L';
                    moveMons[row][col] = 'E';
                }
            } else if (grid[row][col + 1] == 'S' && col < grid[0].length - 2) {//if no space change direction
                moveMons[row][col] = 'R';
                moveRight(row, col);
            } else if (grid[row + 1][col] == 'S' && row < grid.length - 2) {
                moveMons[row][col] = 'D';
                moveDown(row, col);
            } else if (grid[row - 1][col] == 'S' && row > 1) {
                moveMons[row][col] = 'U';
                moveUp(row, col);
            }
        }
    }//end method

    public void shoot() {//shoot method
        if (player.getAmmo() > 0) {//if have ammo
            sound.loadFireball();//load and play fireball sound
            sound.getFireball().start();
            if (lastX > c) {//if player last moved left then shoot left
                grid[r][c - 1] = 'F';
                moveMons[r][c - 1] = 'L';
            } else if (lastX < c) {//if player last moved right the shoot right
                grid[r][c + 1] = 'F';
                moveMons[r][c + 1] = 'R';
            } else if (lastY > r) {//if player last moved up then shoot up
                grid[r - 1][c] = 'F';
                moveMons[r - 1][c] = 'U';
            } else {//if player last moved down then shoot down
                grid[r + 1][c] = 'F';
                moveMons[r + 1][c] = 'D';
            }
            player.setAmmo(player.getAmmo() - 1);//one ammo lost
        }//if no ammo player will not shoot
    }//end method

    public void moveShot() {//moveshot-->CALLED BY TIMER IN THE RPG CLASS
        for (int i = 0; i < grid.length; i++) {//set all positions to can move
            for (int j = 0; j < grid[0].length; j++) {
                canMove[i][j] = true;
            }
        }

        for (int i = 0; i < grid.length; i++) {//double for loop to search for all current fireballs
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 'F') {//if spot is a fireball
                    if (moveMons[i][j] == 'U' && canMove[i][j]) {//if moving up and can move
                        if (grid[i - 1][j] == 'S') {//if space is free
                            grid[i - 1][j] = 'F';
                            moveMons[i - 1][j] = 'U';
                        } else if (i - 1 != 0) {//if space is not free
                            if (grid[i - 1][j] == 'M') {//if space is monster
                                player.setPoints(player.getPoints() + 100);
                                grid[i - 1][j] = 'S';
                                moveMons[i - 1][j] = 'E';
                            } else if (grid[i - 1][j] == 'B') {//if space is boss
                                boss.setLives(boss.getLives() - 1);
                                if (boss.getLives() == 0) {
                                    grid[i - 1][j] = 'S';
                                    moveMons[i - 1][j] = 'E';
                                }
                                player.setPoints(player.getPoints() + 100);
                            } else {//if space is anything else
                                grid[i - 1][j] = 'S';
                                moveMons[i - 1][j] = 'E';
                            }
                        }
                        canMove[i - 1][j] = false;//no longer can move
                        canMove[i][j] = true;
                        grid[i][j] = 'S';//old position fire ball is gone
                        moveMons[i][j] = 'E';
                    } else if (moveMons[i][j] == 'D' && canMove[i][j]) {//if moving down and can move
                        if (grid[i + 1][j] == 'S') {//if space is free
                            grid[i + 1][j] = 'F';
                            moveMons[i + 1][j] = 'D';
                        } else if (i + 1 != grid.length - 1) {//if space is not free
                            if (grid[i + 1][j] == 'M') {//if space is monster
                                player.setPoints(player.getPoints() + 100);
                                grid[i + 1][j] = 'S';
                                moveMons[i + 1][j] = 'E';
                            } else if (grid[i + 1][j] == 'B') {//if space is boss
                                boss.setLives(boss.getLives() - 1);
                                if (boss.getLives() == 0) {
                                    grid[i + 1][j] = 'S';
                                    moveMons[i + 1][j] = 'E';
                                }
                                player.setPoints(player.getPoints() + 100);
                            } else {//if space is anything else
                                grid[i + 1][j] = 'S';
                                moveMons[i + 1][j] = 'E';
                            }
                        }
                        canMove[i][j] = true;//switch the can move
                        canMove[i + 1][j] = false;
                        grid[i][j] = 'S';//old fireball is removed
                        moveMons[i][j] = 'E';
                    } else if (moveMons[i][j] == 'L' && canMove[i][j]) {//if moving left and can move
                        if (grid[i][j - 1] == 'S') {//if the space is free
                            grid[i][j - 1] = 'F';
                            moveMons[i][j - 1] = 'L';
                        } else if (j - 1 != 0) {//if the space is not free
                            if (grid[i][j - 1] == 'M') {//if the space is a monster
                                player.setPoints(player.getPoints() + 100);
                                grid[i][j - 1] = 'S';
                                moveMons[i][j] = 'E';
                            } else if (grid[i][j - 1] == 'B') {//if the space is a boss
                                boss.setLives(boss.getLives() - 1);
                                if (boss.getLives() == 0) {
                                    grid[i][j - 1] = 'S';
                                    moveMons[i][j - 1] = 'E';
                                }
                                player.setPoints(player.getPoints() + 100);
                            } else {//if the space is anything else
                                grid[i][j - 1] = 'S';
                                moveMons[i][j - 1] = 'E';
                            }
                        }
                        canMove[i][j - 1] = false;//switch the can move place
                        canMove[i][j - 1] = true;
                        grid[i][j] = 'S';//remove the old fireball
                        moveMons[i][j] = 'E';
                    } else if (canMove[i][j]) {//if moving right and can move
                        if (grid[i][j + 1] == 'S') {//if the space is free
                            grid[i][j + 1] = 'F';
                            moveMons[i][+1] = 'R';
                        } else if (j + 1 != grid[0].length - 1) {//if the space is not free
                            if (grid[i][j + 1] == 'M') {//if the space had a monster
                                player.setPoints(player.getPoints() + 100);
                                grid[i][j + 1] = 'S';
                                moveMons[i][j + 1] = 'E';
                            } else if (grid[i][j + 1] == 'B') {//if the space has a boss
                                boss.setLives(boss.getLives() - 1);
                                if (boss.getLives() == 0) {
                                    grid[i][j + 1] = 'S';
                                    moveMons[i][j + 1] = 'E';
                                }
                                player.setPoints(player.getPoints() + 100);
                            } else {//if the space has anything else
                                grid[i][j + 1] = 'S';
                                moveMons[i][j + 1] = 'E';
                            }
                        }
                        canMove[i][j] = true;//switch the can move places
                        canMove[i][j + 1] = false;
                        grid[i][j] = 'S';//remove the old fireball
                        moveMons[i][j] = 'E';
                    }
                }
            }
        }
    }//end method

    //FOLLOWING METHODS ARE GETTERS SO THAT THE RPG CLASS CAN OUTPUT THE STATS
    public int getLives() {
        return player.getLives();
    }

    public int getPoints() {
        return player.getPoints();
    }

    public int getAmmo() {
        return player.getAmmo();
    }

    public boolean isRunning() {
        return running;
    }

    public int getSlashes() {
        return player.getSlashes();
    }

    public int getTreasure() {
        return player.getTreasure();
    }
}//End class

class BossMonster {//BossMonster Class
    private int lives;//lives
    private Image image[] = new Image[4];

    BossMonster() throws IOException {//constructor
        lives = 10;//lives is 10

        try {
            image[0] = ImageIO.read(new File("BossF.png"));//load the images
            image[1] = ImageIO.read(new File("BossB.png"));
            image[2] = ImageIO.read(new File("BossL.png"));
            image[3] = ImageIO.read(new File("BossR.png"));
        } catch (Exception ignored) {
        }
    }//end constructor

    //GETTERS AND SETTERS TO DETERMINE WHEN BOSS IS DEAD AND MAKE A NEW BOSS
    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    //IMAGE GETTER TO OUTPUT THE IMAGE OF THE BOSS
    public Image getImage(int x) {
        return image[x];
    }
}//End class

class Sound {//Sound class
    Clip clip, fireball, slash, pickup, grunt, gameOver;

    Sound() {//constructor
        loadFireball();//will load the sounds
        loadGrunt();
        loadPickup();
        loadSlash();
    }//end constructor

    public void loadPickup() {//load pickup sound
        AudioInputStream audioInputStream2 = null;
        try {
            audioInputStream2 = AudioSystem.getAudioInputStream(new File("Pickup.wav").getAbsoluteFile());//get file
        } catch (UnsupportedAudioFileException | IOException ex) {
            ex.printStackTrace();
        }
        pickup = null;
        try {
            pickup = AudioSystem.getClip();//get the clip
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        }
        try {
            pickup.open(audioInputStream2);//open file
        } catch (LineUnavailableException | IOException ex) {
            ex.printStackTrace();
        }
    }//end method

    public void loadFireball() {//load fireball sound
        AudioInputStream audioInputStream1 = null;
        try {
            audioInputStream1 = AudioSystem.getAudioInputStream(new File("Fireball.wav").getAbsoluteFile());//get file
        } catch (UnsupportedAudioFileException | IOException ex) {
            ex.printStackTrace();
        }
        fireball = null;
        try {
            fireball = AudioSystem.getClip();//get clip
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        }
        try {
            fireball.open(audioInputStream1);//open the file
        } catch (LineUnavailableException | IOException ex) {
            ex.printStackTrace();
        }
    }//end method

    public void loadGrunt() {//load grunt sound
        AudioInputStream audioInputStream1 = null;
        try {
            audioInputStream1 = AudioSystem.getAudioInputStream(new File("Grunt.wav").getAbsoluteFile());//get file
        } catch (UnsupportedAudioFileException | IOException ex) {
            ex.printStackTrace();
        }
        grunt = null;
        try {
            grunt = AudioSystem.getClip();//get clip
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        }
        try {
            grunt.open(audioInputStream1);//open file
        } catch (LineUnavailableException | IOException ex) {
            ex.printStackTrace();
        }
    }//end method

    public void loadSlash() {//load slash sound
        AudioInputStream audioInputStream1 = null;
        try {
            audioInputStream1 = AudioSystem.getAudioInputStream(new File("Slash.wav").getAbsoluteFile());//get file
        } catch (UnsupportedAudioFileException | IOException ex) {
            ex.printStackTrace();
        }
        slash = null;
        try {
            slash = AudioSystem.getClip();//get clip
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        }
        try {
            slash.open(audioInputStream1);//open file
        } catch (LineUnavailableException | IOException ex) {
            ex.printStackTrace();
        }
    }//end method

    public void playMusic() {//load background music and gameover sound
        AudioInputStream audioInputStream = null;
        AudioInputStream audioInputStream1 = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File("Background.wav").getAbsoluteFile());//get files
            audioInputStream1 = AudioSystem.getAudioInputStream(new File("GameLost.wav").getAbsoluteFile());
        } catch (UnsupportedAudioFileException | IOException ex) {
            ex.printStackTrace();
        }
        clip = null;
        gameOver = null;
        try {
            clip = AudioSystem.getClip();//get clips
            gameOver = AudioSystem.getClip();
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        }
        try {
            gameOver.open(audioInputStream1);//open files
            clip.open(audioInputStream);
        } catch (LineUnavailableException | IOException ex) {
            ex.printStackTrace();
        }
        clip.loop(Clip.LOOP_CONTINUOUSLY);//loop the background music
    }//end method

    //GETTER METHODS SO THAT THE MAP AND RPG CLASSES CAN PLAY SOUND EFFECTS AND MUSIC WHEN NEEDED

    public Clip getClip() {
        return clip;
    }

    public Clip getFireball() {
        return fireball;
    }

    public Clip getGrunt() {
        return grunt;
    }

    public Clip getGameOver() {
        return gameOver;
    }

    public Clip getPickup() {
        return pickup;
    }

    public Clip getSlash() {
        return slash;
    }
}//End class

class Images {//image class
    Image image[] = new Image[16];

    Images() {//constructor to load all image files
        try {
            image[0] = ImageIO.read(new File("PlayerF.png"));
            image[1] = ImageIO.read(new File("PlayerB.png"));
            image[2] = ImageIO.read(new File("PlayerL.png"));
            image[3] = ImageIO.read(new File("PlayerR.png"));
            image[4] = ImageIO.read(new File("MonsterF.png"));
            image[5] = ImageIO.read(new File("Ammo.png"));
            image[6] = ImageIO.read(new File("Tree.png"));
            image[7] = ImageIO.read(new File("grass.png"));
            image[8] = ImageIO.read(new File("Heart.png"));
            image[9] = ImageIO.read(new File("Treasure Box.png"));
            image[10] = ImageIO.read(new File("Boom.png"));
            image[11] = ImageIO.read(new File("MonsterB.png"));
            image[12] = ImageIO.read(new File("MonsterL.png"));
            image[13] = ImageIO.read(new File("MonsterR.png"));
            image[14] = ImageIO.read(new File("GameOver.png"));
            image[15] = ImageIO.read(new File("Sword.png"));
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }//end constructor

    //GETTER METHOD TO GET IMAGES
    public Image getImage(int x) {
        return image[x];
    }
}//End class

class Player {//Player Class
    int lives, points, treasure, slashes, ammo;

    Player() {//constructor
        lives = 3;//player starts with three lives and ammo
        points = 0;
        treasure = 0;
        slashes = 0;
        ammo = 3;
    }//end constructor

    //FOLLOWING METHODS ARE ALL GETTERS AND SETTERS SO THAT THE MAP CLASS CAN ADD AND REMOVE STATS ON THE PLAYER
    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setSlashes(int slashes) {
        this.slashes = slashes;
    }

    public void setTreasure(int treasure) {
        this.treasure = treasure;
    }

    public int getLives() {
        return lives;
    }

    public int getTreasure() {
        return treasure;
    }

    public int getSlashes() {
        return slashes;
    }

    public int getAmmo() {
        return ammo;
    }

    public int getPoints() {
        return points;
    }
}//end class