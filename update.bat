@echo off
echo Downloading Updated Laucher.zip File...
powershell.exe -noprofile -executionpolicy bypass -file .\dpguips.ps1

echo Removing Old Version
del /f Launcher.zip
ren NewLauncher.zip Launcher.zip
echo Update Complete!
pause