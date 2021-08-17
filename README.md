# RPG-Game

## Necessary Software
I used IntelliJ to develop this code. You can run this in any Java IDE.

## Credits
I did not make the original images, however I have edited them using GIMP to adapt them for my own purposes. I have obtained the wav file from the open source website: https://www.bensound.com/.

## Demo
To see the game in full action please check out this youtube link: https://youtu.be/AtqMJYB5bns

## Description
This is my RPG game developed using Java OOP and Java Swing. Please refer to the Remarks section for some comments I have on the code and the How to Play sections for instructions on how to play.

The player always starts at the top left the goal is to survive as long as possible. On the way several pickups spawn such as treasure, health, sword slashes and magic! You face an endless army of skeletons 💀 and a boss monster 🐲. Good luck surviving! 😎😎

Features Implemented
- Key Controls: arrow keys to move, F to use magic, E to pause
- Monster spawning
- Boss Monster spawning
- Pickups (Treasure, Life, Magic, Sword Slashes) spawning
- Score system
- Game Over screen
- Background Music

## How to Play

<ol> 
  <li> 
    When you start the game you will get to this screen
    <img src = "https://user-images.githubusercontent.com/53048085/129735927-be52b151-5b14-4656-a9e3-c44230553ee8.png"/>
  </li>
  
  <li> 
    There are several key controls. You can move with the arrow keys, press F to shoot magic and E to pause. When you run into an enemy you activate sword slashes if you have any.
    Note that sword slashes do not guarantee you killing the monster!
  </li>
  
  <li> 
    Move around, collect treasure, and survive! 
  </li>
</ol>

## Remarks
I made this project in June 2019. Looking back at this game now, after maturing my Java coding skills I do see a lot that could have been done differently to make the code cleaner. Although this was a good way to cement my understanding of Java OOP if I wrote this code today I would do it differently. Here is a list of some of the changes I would make:

-  I would implement a Singleton design pattern for the Player since I only have one instance of the player during the game
-  I would do a similar thing as above for the Boss Monster
-  I would extract the monsters from the Map class like I did with the BossMonster class
-  I would implement a Factory design pattern for monster spawning to make spawning cleaner
-  I would extract the pickups (lives, swords, magic, treasure) from the Map class and implement them with the Factory design pattern as well
-  I would increase abstraction so that the code does not rely on concrete classes but interfaces and abstract classes instead to promote portability of the code

Making these changes would make my code more readable and easier to debug. 

## Pictures

![image](https://user-images.githubusercontent.com/53048085/129738560-fa39fe27-0793-4847-a509-4ef42e2128b0.png)

![image](https://user-images.githubusercontent.com/53048085/129738802-f6cd6738-0ebb-450a-860d-b124d4fdc843.png)

![image](https://user-images.githubusercontent.com/53048085/129738915-5fef8fd4-337a-4e3d-941f-7a951a9f432b.png)

![image](https://user-images.githubusercontent.com/53048085/129739195-261a4de5-b757-4c7c-99e1-d1115f67deaf.png)

![image](https://user-images.githubusercontent.com/53048085/129739698-e4bfc530-d3b5-4d91-a172-fd0a30c37433.png)

![image](https://user-images.githubusercontent.com/53048085/129739874-9d637a0d-a7de-4657-95c8-fe1a7a5c352d.png)







