chmod 400 ci-tools/szymi.pem
echo ls -l *.pem
ssh -o StrictHostKeyChecking=no -i ci-tools/szymi.pem ec2-user@ec2-34-244-51-218.eu-west-1.compute.amazonaws.com < ci-tools/update-image.sh