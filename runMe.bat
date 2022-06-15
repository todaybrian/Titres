javac -d out/production/ics4u-assignment -target 1.8 -source 1.8 src/tetris/game/*.java src/tetris/game/randomizer/*.java src/tetris/controls/*.java src/tetris/gui/widget/*.java src/tetris/gui/*.java src/tetris/util/*.java src/tetris/settings/*.java src/tetris/*.java src/tetris/music/*.java
java -cp out/production/ics4u-assignment tetris.Main -Dsun.java2d.uiScale=1.0
