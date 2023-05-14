# k8s-sca projet

# Description

This project is a simple example of a microservice architecture using kubernetes. we have 4 microservices (order-service) and a cache (redis) and a dashboard (kubernetes-dashboard) and a load balancer (ingress-nginx-controller)

# Pre-requisites

- [Docker](https://docs.docker.com/get-docker/)
- [Kubernetes](https://kubernetes.io/docs/tasks/tools/install-kubectl/)
- [Kubernetes Dashboard](https://kubernetes.io/docs/tasks/access-application-cluster/web-ui-dashboard/)
- [Ingress Nginx Controller](https://kubernetes.github.io/ingress-nginx/deploy/)
- [Docker Registry](https://docs.docker.com/registry/deploying/)


# Components

## Order-Service

The order-service is a simple microservice that exposes a REST API to create and get orders. It is written in Java using Spring Boot and uses a MySQL database to store the orders.

## Cache 

The cache is a simple microservice that use redis. It is build with docker image redis:alpine

## Ingess Nginx Controller

The Ingress Nginx Controller is a simple microservice that use nginx. It is build with this commande : 

```bash
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v0.44.0/deploy/static/provider/cloud/deploy.yaml
```

## Kubernetes Dashboard

The Kubernetes Dashboard is a simple microservice that use kubernetes-dashboard. It is build with this commande : 

```bash
kubectl apply -f https://raw.githubusercontent.com/kubernetes/dashboard/v2.2.0/aio/deploy/recommended.yaml

kubectl apply -f "k8s/dashboard-adminuser.yaml"

kubectl proxy
```

After built the dashboard, we can access to it with this link : 
[Kubernetes-Dashboard](http://localhost:8001/api/v1/namespaces/kubernetes-dashboard/services/https:kubernetes-dashboard:/proxy/)

If we need token-adminuser to access to the dashboard, we can use this command : 

```bash
sh scripts/create-token-adminuser.sh
```


# Role of components

## Namespace in kubernetes

A namespace is a virtual cluster that provides a way to divide cluster resources between multiple users. Kubernetes starts with three initial namespaces: default, kube-system, and kube-public. The default namespace is the one we use by default. We can create a new namespace with this command : 

```bash
kubectl create namespace <namespace-name>
```

## Order-Service & Cache

### Deployment

The deployment of the order-service is done with the file deployment.yaml. It contains the configuration of the deployment of the order-service. The deployment is done with 2 replicas. In kubernetes, deployment is a resource object in the Kubernetes API that provides declarative updates to applications. A deployment allows you to describe an application's life cycle, such as which images to use for the app, the number of pods there should be, and the way in which they should be updated. For the deployment we use the image of the order-service that we have pushed in the docker registry. 

### Service

The service of the order-service is done with the file service.yaml. It contains the configuration of the service of the order-service. The service is a resource object in the Kubernetes API that provides an abstraction over pods. It allows you to describe a set of pods that work together to provide a service. Unlike pods, which represent running processes on nodes in your cluster, services represent a set of running pods and how to access them. Services can be exposed in different ways by specifying a type in the serviceSpec: ClusterIP (default), NodePort, LoadBalancer, and ExternalName. By Default we use ClusterIP. Port 9001 is the port of the order-service. 6379 is the port of the cache.

## Ingress

The ingress of the order-service is done with the file ingress.yaml. It contains the configuration of the ingress of the order-service. The ingress is a resource object in the Kubernetes API that manages external access to the services in a cluster, typically HTTP. Ingress may provide load balancing, SSL termination and name-based virtual hosting. Ingress exposes HTTP and HTTPS routes from outside the cluster to services within the cluster. Traffic routing is controlled by rules defined on the Ingress resource. It is configured to redirect the traffic to the order-service inside our namespace.

# How to run without Docker & Kubernetes

## Cache

This microservice is a simple redis application. To run it, you need to have redis installed on your machine. You can run it with this command (On MacOs): 

```bash
brew install redis 
redis-server
```


## Order-Service

This microservice is a simple spring boot application. To run it, you need to have java and maven installed on your machine. You can run it with this command : 

```bash
mvn spring-boot:run
```

# How to run with Docker

## Cache

This microservice is a simple redis application. To run it, you need to have docker installed on your machine. You can run it with this command : 

```bash
docker run -d -p 6379:6379 redis:alpine
```

## Order-Service

This microservice is a simple spring boot application. To run it, you need to have docker installed on your machine. You can run it with this command : 

```bash
docker run -d -p 9001:9001 --name order-service --link redis:redis -e REDIS_HOST=redis -e REDIS_PORT=6379 -e REDIS_PASSWORD= order-service:latest
```

# How to run with Kubernetes

We need to have a kubernetes cluster to run our microservices. We can use docker desktop to run a kubernetes cluster on our machine.

First of all, we need to push our images in the docker registry. We can use the docker hub registry. We need to create an account in docker hub and create a repository for each microservice. After that, we need to build our images and push them in the docker registry. We can use this command to build and push the images : 

```bash
docker build -t <docker-hub-username>/<repository-name>:<tag> .
docker push <docker-hub-username>/<repository-name>:<tag>
```

After that, we need to create a namespace in kubernetes. We can use this command to create a namespace : 

```bash
kubectl create namespace <namespace-name>
```

We suppose that kubenertes dashboard and ingress-nignx-controller are already installed in our cluster. If not, you can see the installation in the pre-requisites section.

First of all, let's create the deployment of the order-service and cache. We create it in order-service namespace. We create 2 replicatSet. We can use this command to create the deployment : 

```bash
kubectl apply -f k8s/deployment.yaml
```

After that, we need to create the service of the order-service and cache. We create it in order-service namespace. In this component, we specify the port and protocal. We can use this command to create the service : 

```bash
kubectl apply -f k8s/service.yaml
```

After that, we need to create the ingress of the order-service. We can use this command to create the ingress : 

```bash
kubectl apply -f k8s/ingress.yaml
```

After that, we go to the dashboard to see the result. We need to see 2 pods in the order-service namespace. One service and ingress in the order-service namespace. In the ingress, we need to see the host and path of the order-service.

If we want to see the logs of the order-service, we can use this command : 

```bash
kubectl get pods -n <namespace-name>
kubectl logs -f <pod-name> -n <namespace-name>
```

If you have any problem or sugestion, please contact me :
- Email : [selim.bouhassatine@etu.u-pec.fr](mailto:selim.bouhassatine@etu.u-pec.fr)
