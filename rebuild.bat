@echo off

rem copy eclipse folder
xcopy /e /i ..\..\Utilities\forge-1.11.2-13.20.0.2228\eclipse %cd%\eclipse

rem build workspace
start gradlew setupDecompWorkspace eclipse