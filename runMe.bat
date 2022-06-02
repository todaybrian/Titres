javac -d out/production/ics4u-assignment -target 1.8 -source 1.8 src/tetris/controls/*.java src/tetris/gui/widget/*.java src/tetris/gui/*.java src/tetris/util/*.java src/tetris/settings/*.java src/tetris/*.java
java -cp out/production/ics4u-assignment tetris.Main -Dsun.java2d.uiScale=1.0
