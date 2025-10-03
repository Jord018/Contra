@echo off
setlocal enabledelayedexpansion

:: Set the project directory
set PROJECT_DIR=%~dp0

:: Set the classpath to include all JARs in the target directory
set CLASSPATH=.
for /r "%PROJECT_DIR%target" %%a in (*.jar) do (
    set CLASSPATH=!CLASSPATH!;%%a
)

:: Add the compiled classes directory
set CLASSPATH=%CLASSPATH%;%PROJECT_DIR%target\classes

:: Print the classpath for debugging
echo Classpath: %CLASSPATH%
echo.

:: Run the application
java -cp "%CLASSPATH%" ^
  --add-opens java.base/java.lang=ALL-UNNAMED ^
  --add-opens java.desktop/java.awt=ALL-UNNAMED ^
  -Dprism.order=sw ^
  -Djava.library.path=%PROJECT_DIR%target\natives ^
  org.game.contra.desktop.Main

pause
