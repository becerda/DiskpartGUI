
# DiskpartGUI
GUI for Windows Diskpart

## Diskpart Write-Up
This readme will go over the commands used to preform the background tasks of various processes.

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
List - Command to list all disks, partitions, volume, or virtual disks
Volume - Command used in conjunction with List to display all volumes

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
Attributes Disk [Set | Clear] [ReadOnly] [NoErr] 
```
	Attributes - Runs attribute command
	Disk - Supplies the selected disk.
	Set | Clear - Sets or clears the supplied attribute.
	ReadOnly - The ReadOnly attribute.
	NoErr - Flag for setting no error output

### Returns
Successful or error message

#### Command 2:
```bash
Attributes Disk
```
	Attributes - Runs attribute command
	Disk - Specific disk to get attributes of

### Returns
Attribute for selected disk.

### Displayed Info On GUI
A column in the previously mentioned JTable marked as "Yes" or "No"

## Safely Eject Drives
PowerShell command executed through Java, the selected drive will be ejected.
### PowerShell
```bash
(New-Object -comObject Shell.Application).Namespace(17).ParseName(“<VolumeLetter>:”).InvokeVerb(“Eject”)
```
	(New-Object -comObject Shell.Application) - Creates a new shell object
	Namespace(17) - Gets drives
	ParseName(“<VolumeLetter>:”) - Selects the volume with given letter
	InvokeVerb(“Eject”) - Ejects selected volume

### Returns
PowerShell does not return anything.

### Displayed Info On GUI
Button below displayed drive with “Eject” on it.
Will show popup notification of either safely ejected or error.

## Format Removable Drives
Diskpart command line interpreter by Java will be used to format removable drives.
### Diskpart
```bash
format [fs=<fs>] [label=<label>] [unit=<n>] [quick]
```
	format - Command to format volume
	fs=<fs> - Sets the file system to use
	label=<label> - Sets the label for the volume
	uint=<n> - Sets default allocation unit size
	quick - Flag to set quick format

### Returns
Diskpart will return either successful or error message.

### Displayed Info On GUI
Button below displayed drive with “Format” on it.
A pop up window will appear upon completion showing either successful or error.

## Rename Volume
Label command line interpreter by Java will be used to rename removable drives.
### Label
```bash
label [drive:\] [label]
```
	label - Runs Label command
	drive:\ - The specified drive to rename
	label - The new label name

### Returns
Label does not return anything

### Displayed Info On GUI
Button below displayed drive with “Rename” on it.
Pop-up window to set new name.
Displays pop-up of successful or error message.

## Activate BitLock
Control command executed through Java to open BitLock window.

### Control
```bash
control /name Microsoft.BitLockerDriveEncryption
```
	control - Tells window to open specific application
	/name - Name flag of control
	Microsoft.BitLockerDriveEncryption - BitLocker application to open

### Returns
Nothing is returned

###Displayed Info On GUI
Button below displayed drive with “BitLocker” on it.