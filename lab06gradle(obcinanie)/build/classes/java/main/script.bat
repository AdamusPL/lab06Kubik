echo Compiling.

::javac BuoyMain.java

For /L %%y IN (1,1,50) DO start java -Xmx8M pl.edu.pwr.aczekalski.lab06.BuoyMain

echo Running!