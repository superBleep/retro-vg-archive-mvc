# Prerequisites
Docker, Minikube, Helm

Create a Docker Hub account and an empty repository
# Setup
## Spring MVC App
`mvn clean install -D.maven.test.skip=true` - Build the app without testing
## Minikube
`minikube start` - Run Minikube

`minikube docker-env | Invoke-Expression` - Configure Powershell to use the Docker daemon inside Minikube

`kubectl create secret docker-registry rvgasecret --docker-server=https://index.docker.io/v1/ --docker-username=USERNAME --docker-password=PASSWORD` - Configure a secret containing the login credentials for the Docker Hub repository

`docker build -t superbleep/rvga-mvc:latest .` - Build the Docker container for the app

`docker push superbleed/rvga-mvc:latest` - Push the Docker image to the repository

`kubectl create namespace monitoring` - Create a “monitoring” namespace for Prometheus and Grafana

Install Grafana through Helm, and change the default login to “admin/admin”
```
helm repo add grafana https://grafana.github.io/helm-charts
helm repo update

helm install grafana grafana/grafana -n monitoring --set adminPassword=admin
```

Install Prometheus through Helm
```
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo update

helm install prometheus prometheus-community/prometheus -n monitoring
```

`kubectl apply -f .` - Apply all YAML configurations

`minikube service grafana -n monitoring` - Access Grafana through port-forwarding

`kubectl port-forward svc/rvga-mvc 8080:80`  Access the app through port-forwarding
# Grafana
Configure a Prometheus datasource with URL `http://prometheus-server.monitoring.svc.cluster.local`

Import dashboard from the `prometheus-config.json` file
# Startup
`minikube start`

`minikube docker-env | Invoke-Expression`

`minikube service grafana -n monitoring`

`kubectl port-forward svc/rvga-mvc 8080:80`