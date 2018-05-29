#!/bin/bash
osascript -e '
  tell application "Terminal"
  do script "java -jar /Users/soujanyamacbook/Projects/HOOQAssignment/lib/selenium-server-standalone-3.12.0.jar"
  activate
  end tell '
