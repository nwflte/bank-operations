# bank-operations

# Run on Kubernetes

## Notes:
- Keycloak and backend both uses Postgres database.
- Keycloak realm is imported from file keycloak/realm.json
- Postgres databases are created at startup. 

## Setup

Modify absolute paths of volumes in files kubernetes/postgres-deployement.yml and kubernetes/keycloak-deployment.yml to match your system.\
(**"/home/naoufal/bank-operations/pg-init-scripts/"** and **"/home/naoufal/bank-operations/keycloak/realm.json"**).

## Run

From root folder:

0- If you need to build the backend image (skip to step 1 if you want to use the image from docker hub)
```
$ ./gradlew assemble
$ mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*.jar)
$ docker-compose build
$ eval $(minikube docker-env) # Do this If you are using minikube to use images from local docker daemon
```

1-
```
$ kubectl apply -f kubernetes
```

2- Check when all pods are running:
```
$ kubectl get pods
```

3- Get keycloak service URL (Using minikube in this example) :
```
$ echo $(minikube service keycloak --url)/auth
```

4- Modify `keycloak.auth-server-url` env variable in `kubernetes/app-deployment.yml` with the URL from step 3, it will look like this:
```
- name: keycloak.auth-server-url
  value: "http://<ADDR>:30080/auth"
```

5- Apply changes:
```
$ kubectl apply -f kubernetes
```

6- Use the link above to access keycloak admin console. login with admin/admin.

7- Add a user with `ROLE_USER` to keycloak `SpringService` realm.

8- You can now get a token of your user by using this command: (You may be prompted to install jq: `sudo apt install jq`)
```
BEARER_TOKEN=$(curl -s --location --request POST $(minikube service keycloak --url)'/auth/realms/springservice/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'client_id=bank-service' \
--data-urlencode 'username=user1' \
--data-urlencode 'password=password' \
--data-urlencode 'grant_type=password' | jq -r '.access_token')
```

9- Use the token from above to query to the backend:
```
curl --location -s --request GET $(minikube service bank-backend --url)'/api/virements' --header 'Authorization: Bearer '$BEARER_TOKEN
```
