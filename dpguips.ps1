$url = "https://github.com/becerda/DiskpartGUI/raw/master/Launcher.zip"

$output = "$PSScriptRoot\NewLauncher.zip";

(New-Object System.Net.WebClient).DownloadFile($url, $output);
