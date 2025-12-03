# Run Phase 3 static analysis and package artifacts
# Usage: Run in PowerShell from the repo root (may require admin privileges to write files)

$repoRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
if (-not $repoRoot) { $repoRoot = Get-Location }
Set-Location -Path "$repoRoot\.."

Write-Host "Running: mvn -DskipTests verify"
$mvnCmd = "mvn -DskipTests verify"
# Run Maven and capture output
& mvn -DskipTests verify 2>&1 | Tee-Object -FilePath build.log

# Collect artifact paths
$artifacts = @()
$checkstyle = "target\site\checkstyle.html"
$pmd = "target\site\pmd.html"
$spotbugsXml = "target\spotbugsXml.xml"
$spotbugsHtml = "target\site\spotbugs.html"

if (Test-Path $checkstyle) { $artifacts += $checkstyle }
if (Test-Path $pmd) { $artifacts += $pmd }
if (Test-Path $spotbugsXml) { $artifacts += $spotbugsXml }
if (Test-Path $spotbugsHtml) { $artifacts += $spotbugsHtml }

# Always include build.log
$artifacts += "build.log"

$destZip = "phase3-artifacts.zip"
if (Test-Path $destZip) { Remove-Item $destZip -Force }

Write-Host "Packaging artifacts: $($artifacts -join ', ')"
Compress-Archive -Path $artifacts -DestinationPath $destZip -Force

Write-Host "Packaged artifacts to $destZip"
Write-Host "You can now upload $destZip or provide it to the reviewer."
