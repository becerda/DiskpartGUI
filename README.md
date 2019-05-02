# DiskpartGUI
GUI for Windows Diskpart

## Installation
There are a few things required to run this application:
1) DikspartGUI.jar
2) Launcher.bat
3) Java SE-1.8 (jre1.8.0_211) or later with PATH variable set
4) Diskpart Gui Shortcut (Not required but helps run the launcher)

## How To Set PATH Variable
1) Find location to java.exe, usually in 'C:\Program Files\Java\jre1.8.0_***\bin
2) Type 'WindowsKey + s' and search for 'Control Panel'
3) Select 'System and Security'
4) Select 'System'
5) Select 'Advanced system settings'
6) Under the 'Advanced' tab click 'Environment Variables'
7) Under 'System variables' click on 'Path' then click 'Edit...'
8) Search through the list to see if your Java file location is already there, if it is you are done
9) Click 'Browse...' and find the directory found in step 1)

## How To Run
Run through shortcut link or launcher.bat. This program requires administrative rights and will not work if launched directly through the jar file.

## Write-Up
This README will go over the commands used to preform the background tasks of various processes.

## Requirements
1) List of all drives
2) Quickly change attributes
3) Safely Eject Drives
4) Format Removable Drives
4) Rename Volume
6) Activate BitLock

## Listing Of Found Drive
Diskpart command line interpreter by Java will be used to display information.

### Diskpart
```bash
LIST VOLUME
```
	LIST - Command to list all disks, partitions, volume, or virtual disks
	VOLUME - Command used in conjunction with List to display all volumes

### Returns
	Volume Number
	Volume Letter
	Volume Label
	File System
	Type
	Size
	Status
	Info

### Displayed Info On GUI
Populate JTable with volumes in main window with:

	Volume Number
	Volume Letter
	Label
	Size

## Quickly Change Attributes
Diskpart command line executed by Java will be used to change attributes associated with selected volume.

### Diskpart
#### Command 1:
```bash
ATTRIBUTES DISK [SET | CLEAR] [READONLY] [NOERR] 
```
	ATTRIBUTES - Runs attribute command
	DISK - Supplies the selected disk.
	SET | CLEAR - Sets or clears the supplied attribute.
	READONLY - The ReadOnly attribute.
	NOERR - Flag for setting no error output

### Returns
Successful or error message

### Displayed Info On GUI
A pop-up window will appear upon completion showing either successful or error.

#### Command 2:
```bash
ATTRIBUTES DISK
```
	ATTRIBUTES - Runs attribute command
	DISK - Specific disk to get attributes of

### Returns
Attribute for selected disk.

### Displayed Info On GUI
A column in the previously mentioned JTable marked as "Yes" or "No".

## Safely Eject Drives
Diskpart command executed through Java, the selected drive will be unmounted and safe to remove.

### Diskpart
```bash
REMOVE ALL DISMOUNT
```
	REMOVE - command used to unmount a drive
	ALL - tells Diskpart to unmount all mounting points
	DISMOUNT - tells Diskpart to dismount the selected volume

### Returns
Diskpart returns a success or error message

### Displayed Info On GUI
Button below displayed drive with "Eject" or "Mount" on it.
A pop-up window will appear upon completion showing either successful or error.

## Format Removable Drives
Diskpart command line interpreter by Java will be used to format removable drives.

### Diskpart
```bash
FORMAT [FS=<FS>] [LABEL=<LABEL>] [UINT=<N>] [QUICK]
```
	FORMAT - Command to format volume
	FS=<FS> - Sets the file system to use
	LABEL=<LABEL> - Sets the label for the volume
	UINT=<N> - Sets default allocation unit size
	QUICK - Flag to set quick format

### Returns
Diskpart will return either successful or error message.

### Displayed Info On GUI
Button below displayed drive with “Format” on it.
A pop-up window will appear upon completion showing either successful or error.

## Rename Volume
Label command line interpreter by Java will be used to rename removable drives.

### Label
```bash
LABEL [DRIVE:\] [LABEL]
```
	LABEL - Runs Label command
	DRIVE:\ - The specified drive to rename
	LABEL - The new label name

### Returns
Label does not return anything

### Displayed Info On GUI
Button below displayed drive with “Rename” on it.
A pop-up window will appear upon completion showing either successful or error.

## Activate BitLock
Control command executed through Java to open BitLock window.

### Control
```bash
CONTROL /NAME Microsoft.BitLockerDriveEncryption
```
	CONTROL - Tells window to open specific application
	/NAME - Name flag of control
	Microsoft.BitLockerDriveEncryption - BitLocker application to open

### Returns
Nothing is returned

### Displayed Info On GUI
Button below displayed drive with “BitLocker” on it.