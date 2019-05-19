$url = "https://github.com/becerda/DiskpartGUI/raw/master/DiskPartGUI.jar"

$output = "$PSScriptRoot\DiskPartGUINew.jar";

(New-Object System.Net.WebClient).DownloadFile($url, $output);
