Cubert - An intelligent Rubik's Cube solving robot
--------------------------------------------------
Summer term 2015
Programming Intelligent Applications
Computer Science and Media (M.Sc.)
Media University Stuttgart (HdM)
--------------------------------------------------
Code: Vanessa Werner (vw017@hdm-stuttgart.de) & Maximilian Braun (mb238@hdm-stuttgart.de)
Robot design: David Gilday http://mindcuber.com/ (with a few little changes)
-----------------------------------------------------------------------------------------

Software & drivers (IMPORTANT: All 32Bit!)
-----------------------------------------------------
Java JDK - Development Kit:
http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

USB Driver for the NXT:
http://cache.lego.com/r/education/-/media/lego%20education/home/downloads/software/drivers/fantompc.zip?l.r=-939606712

LeJOS NXJ SDK:
http://sourceforge.net/projects/nxt.lejos.p/files/latest/download?source=files

Eclipse IDE:
https://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/luna/SR2/eclipse-jee-luna-SR2-win32.zip&mirror_id=1190

Eclipse plugin: 
http://www.lejos.org/tools/eclipse/plugin/nxj/

Tutorials: 
----------
LeJOS tutorial:
http://www.lejos.org/nxt/nxj/tutorial/

Construction: 
http://mindcuber.com/mindcuber8527/MindCuber-8527.pdf 

Gear transmission lookup for Lego NXT
http://gears.sariel.pl/

Demo video:
----------
https://youtu.be/THuT3VbyxvY

Presentation:
------------
https://docs.google.com/presentation/d/13D4UeuutqEJWjn6-D78JGo9tZ_4ZXNmDbtxN8ro7jnk/edit?usp=sharing

Robot setup:
------------
![My image](https://github.com/ikeaboy88/cubert/blob/master/pics/DSC_0362.JPG)
![alt text](pics/DSC_0363.jpg)
![alt text](pics/DSC_0364.jpg)
![alt text](pics/DSC_0365.jpg)
![alt text](pics/DSC_0366.jpg)
![alt text](pics/DSC_0367.jpg)
![alt text](pics/DSC_0368.jpg)
![alt text](pics/DSC_0369.jpg)

Usage Instructions:
-------------------

Cubert needs to be connected with your PC via USB!


Initiate connection
-------------------
1a. Start Cubert by pressing the orange button (Wait 10s until he is ready)

1b. Run com.nxt.Main.java as "leJOS NXT Program" (Cubert makes some sound)

1c. Run com.pc.Main.java as "Java Application" (Now Cubert and PC are connected)


Learn the colors
----------------
2. Press right button on Cubert to "learn" the colors (Orientation needed as follows: White facing top, green facing color detector)


Scramble
--------
3. Scramble the cube (7 quarter turns will be solved quickly on any PC - 10 are possible within a minute with a strong CPU)


Recognize state
---------------
4a. Press orange button to start a complete scan of the scrambled state (Takes less than 2 minutes)

4b. When finished the state (scan_result_vector) will automatically be sent to the PC


Solve the cube
--------------
5a. When every cubie was recognized correctly the PC will automatically run A* to find the solution

5b. When the solution has been found, the PC automatically sends the solving sequence to Cubert

5c. Press orange button to make Cubert solve the cube (8 quarter turns should take about 60 s)


Make another solve
------------------
6a. Press orange button

6b. Jump back to 3.


(To kill execution instantly at any time, press the orange and the small grey button at the same time)
