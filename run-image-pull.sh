chmod 400 szymi.pem
echo ls -l *.pem
ssh -o StrictHostKeyChecking=no -i szymi.pem ec2-user@ec2-34-244-51-218.eu-west-1.compute.amazonaws.com < update-image.sh