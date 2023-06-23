param (
    [switch]$rebuild
)


$imageName = "my-game"
$containerName = "my-game"
$x11Port = "5.0"
$x11LocalBind = "/tmp/.X11-unix"
$x11ContainerBind = "/tmp/.X11-unix:ro"

$savegamesLocalPath = "C:\dev\studium\scala\se\another-scala-game\savegames"
$savegamesContainerPath = "/app/savegames"

# Start Script

Write-Output "Get IP Address"
$ip = (Get-NetIPAddress -InterfaceAlias 'WLAN' -AddressFamily IPv4).IPAddress

$imageExists = docker image inspect $imageName 2>$null

Write-Output "$rebuild"

if (!$imageExists -or $rebuild) {
    Write-Output "Rebuild image $imageName"
    docker build . -t $imageName
} else {
   Write-Output "Found $imageName"
}

$containerExists = docker container inspect -f '{{.State.Running}}' $containerName 2>$null

if ($containerExists) {
    docker stop $containerName
} else {
    Write-Output "Container $containerName not running"
}

Write-Output "Start Container"
docker run --name my-game -it --rm -e DISPLAY=$ip":$x11Port" -v ${x11LocalBind}:${x11ContainerBind} -v ${savegamesLocalPath}:${savegamesContainerPath} $imageName