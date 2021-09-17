#!/bin/bash

echo "DOCKER_CONTAINER_NAME: $DOCKER_CONTAINER_NAME"

echo ""
echo "STOPPING CONTAINER"
docker stop "$DOCKER_CONTAINER_NAME" 2>&1

echo ""
echo "DELETING CONTAINER"
docker rm "$DOCKER_CONTAINER_NAME" 2>&1

echo ""
echo "IMAGE"

docker image ls | grep "$DOCKER_IMAGE_NAME"

#
# Here we expose the internal port configured in the env var
# APP_SPRING_INNER_PORT to the external port configured 
# in the env var APP_SPRING_OUTER_PORT
# To specify the spring profile, it has the next options:
#     Java properties=>   spring.profiles.active=dev
#                   Into a file or  
#                   Into command line execution:
#                       -Dspring.profiles.active=dev
#
#     Environment variable =>  export spring_profiles_active=dev
#

echo ""
echo "RUNNIG CONTAINER"
docker run -d \
   --restart unless-stopped \
   -p "$APP_SPRING_OUTER_PORT":"$APP_SPRING_INNER_PORT" \
   -e spring_profiles_active="$APP_SPRING_PROFILE" \
   --network "$DOCKER_NETWORK_NAME" \
   --hostname "$DOCKER_HOSTNAME" \
   --name "$DOCKER_CONTAINER_NAME" \
   "$DOCKER_IMAGE_NAME":"$DOCKER_IMAGE_TAG" 2>&1


res=$?
echo "RESULT: $res"

exit $res
