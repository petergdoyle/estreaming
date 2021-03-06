#!/bin/sh
. scripts/lib/docker_functions.sh
. scripts/lib/color_and_format_functions.sh


docker_build_all() {
  no_cache=$1

  #special base build
  cd docker/base
  ./docker_build.sh $no_cache
  cd -
  if [ $? -ne 0 ]; then
    display_error "the docker build for $project_name/base did not complete successfully"
    exit
  else
    display_success "the docker build for $project_name/base built successfully"
  fi
  # jdk is out of the base because it adds about 500Mb to the image and
  # it is not needed by may containers
  cd docker/jdk8
  ./docker_build.sh $no_cache
  cd -
  if [ $? -ne 0 ]; then
    display_error "the docker build for $project_name/jdk8 did not complete successfully"
    exit
  else
    display_success "the docker build for $project_name/jdk8 built successfully"
  fi

  cd docker/nodebase
  ./docker_build.sh $no_cache
  cd -
  if [ $? -ne 0 ]; then
    display_error "the docker build for $project_name/nodejs did not complete successfully"
    exit
  else
    display_success "the docker build for $project_name/nodejs built successfully"
  fi

  for folder in $(find . -type f -name 'Dockerfile' -exec dirname {} \;); do
    if [[ "$folder" == './base' || "$folder" == './jdk8' || "$folder" == './nodebase' ]]; then
      continue
    fi
    cd $folder
    for each in $(find . -type f -name 'docker_build*sh' -exec ls {} \;); do
      echo "building $each container"
      $each $no_cache
      if [ $? -ne 0 ]; then
        display_error "the docker build for $folder$each did not complete successfully"
      else
        display_success "the docker build for $folder$each built successfully"
      fi
    done
    cd -
  done
}

no_cache=$1
docker_build_all $no_cache
