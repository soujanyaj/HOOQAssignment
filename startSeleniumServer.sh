#!/bin/bash
osascript -e '
  tell application "Terminal"
  do script "cd '$1' && java -Dwebdriver.chrome.driver='$1'/chromedriver_2.38 -Dwebdriver.gecko.driver='$1'/geckodriver-v0.20.1 -jar selenium-server-standalone-3.12.0.jar -port 4444"
  activate
  end tell '

  

  