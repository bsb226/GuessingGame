#!/bin/sh
rm -f GuessingGame.jar
javac -sourcepath src -d bin src/ApplicationStarter.java src/Application.java
jar -cfm GuessingGame.jar MainClass audio images -C bin/ .
