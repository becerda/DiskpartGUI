@echo off
echo Downloading Updated DiskPartGUI...
powershell.exe -noprofile -executionpolicy bypass -file .\dpguips.ps1

echo Removing Old Version
del /f DiskPartGUI.jar
ren DiskPartGUINew.jar DiskPartGUI.jar
echo Update Complete!
pause