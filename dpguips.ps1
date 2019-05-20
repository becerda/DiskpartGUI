param(
	[Parameter(Mandatory=$true)][string]$new_version,
	[Parameter(Mandatory=$true)][string]$old_version
)

$url = "https://github.com/becerda/DiskpartGUI/raw/master/Launcher.zip"
$output = "$PSScriptRoot\Launcher_$new_version.zip"
$old_version_path = "$PSScriptRoot\Launcher_$old_version"

Write-Host "Update Script For Diskpart GUI "

if($new_version -eq $old_version){
	Write-Host Already Up To Date!
	exit
}

Write-Host "Updating To Version $new_version "

(New-Object System.Net.WebClient).DownloadFile($url, $output)

if(Test-Path $old_version_path){
	Remove-Item -path $old_version_path
}

if(Test-Path $output){
	Write-Host "Successfully Downloaded Version $new_version! "
}else{
	Write-Host "Failed To Download Update! "
}
