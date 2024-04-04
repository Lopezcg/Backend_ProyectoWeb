sudo docker build -t localhost:5000/test:latest .
sudo docker tag localhost:5000/test:latest localhost:5000/test:latest
sudo docker push localhost:5000/test:latest

sudo microk8s kubectl delete service test -n backend
sudo microk8s kubectl delete deployment test -n backend
sudo microk8s kubectl apply -f k8s/deployment.yaml

sudo microk8s kubectl delete ingress test -n backend
sudo microk8s kubectl apply -f k8s/ingress.yaml