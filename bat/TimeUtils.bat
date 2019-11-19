@echo off
set yyyy=%date:~,4%
set mm=%date:~5,2%
set dd=%date:~8,2% 
set hh=%time:~0,2%
set mi=%time:~3,2%
set ss=%time:~6,2% 

set currentdate=%yyyy%-%mm%-%dd%%hh%:%mi%:%ss%´ò¿¨

echo %currentdate%

pause 
