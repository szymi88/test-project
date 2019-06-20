echo "Uploading"

docker pull szymi88/teletronics-test-project
docker ps
docker stop app
docker rm app
docker run -d -p 80:8080 --name app szymi88/teletronics-test-project


