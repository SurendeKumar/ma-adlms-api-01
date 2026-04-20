$containerName = "libraryapi-container"
$imageName = "libraryapi"
$hostPort = 8080
$containerPort = 8080

Write-Host "Stopping existing container if it exists..."
docker stop $containerName 2>$null
docker rm $containerName 2>$null

Write-Host "Running new container..."
docker run -d --name $containerName -p ${hostPort}:${containerPort} $imageName

Write-Host "Deployment completed."
docker ps