#!/bin/sh

project_name='estreaming'

docker_build() {
  no_cache=$1

  if [ -e $img_name ]; then
    echo "variable img_name is not set. cannot continue"
    return 1
  fi
  if [ -n "$no_cache" ]; then echo "--no_cache"; else echo "cache"; fi

  docker build $no_cache -t=$img_name .

}

docker_destroy() {
  local container_name=$1
  if [ -e $container_name ]; then
    echo "param container_name is not set. cannot continue"
    return 1
  fi
  docker_clean $container_name
}

docker_clean() {
  if [ -e $container_name ]; then
    echo "variable container_name is not set. cannot continue"
    return 1
  fi

  container_status=$(docker ps -a --filter=name=$container_name| grep $container_name)
  #stop if exists and running
  test "$(echo $container_status| grep Up)" != '' && (echo -e "\e[7;40;92mstopping container $container_name...\e[0m") && (docker stop $container_name)
  #remove if exists
  test "$(echo $container_status)" != '' && (echo -e "\e[7;40;92mremoving container $container_name...\e[0m") && (docker rm $container_name)
}


## override start_cmd if required (note sometimes it is not necessary when CMD is set in Dockerfile)
shell_cmd='/bin/bash'
supervisord_cmd='/usr/bin/supervisord -c /etc/supervisord.conf'
start_cmd="$shell_cmd"

## override volumes if required
shared_volume_base="-v $PWD:/docker"
volumes=""
workdir=""
network_native="--net host"
network_default=""
network="$network_default"
links=""

daemon='-d'
transient='--rm'
mode=$daemon

docker_run() {
  local ports=$1
  open_firewall_port $ports
  if [ -e $container_name ]; then
    echo "variable container_name is not set. cannot continue"
    return 1
  fi
  if [ -e $img_name ]; then
    echo "variable img_name is not set. cannot continue"
    return 1
  fi
  msg="creating container $container_name..."
  echo -e "\e[7;40;92m$msg\e[0m"
  docker_clean
  docker_cmd="docker run $mode -ti \
  --name $container_name \
  $links \
  $volumes \
  $workdir \
  $network \
  $img_name \
  $start_cmd"
  echo "running command... $docker_cmd"
  eval $docker_cmd
}


docker_run_all_template() {

  cmd=$1

  for each in $(find . -type f -name $cmd -exec dirname {} \;); do
    cd $each

    echo "running the container specified in $each "

    eval './'$cmd

    if [ $? -ne 0 ]; then
      display_error "the docker run for $each did not complete successfully"
    else
      display_success "the docker run for $each built successfully"
    fi

    cd -
  done
}


docker_run_all_native() {

  docker_run_all_template 'docker_run_native.sh'

}

docker_run_all() {

  docker_run_all_template 'docker_run.sh'

}

docker_stop_all_containers() {
  for each in $(docker ps |grep $project_name |awk 'NF>1{print $NF}'); do
    cmd="docker stop $each"
    echo $cmd
    eval $cmd
  done
}

docker_start_all_native_containers() {
  for each in $(docker ps -a|grep $project_name |grep native |awk 'NF>1{print $NF}'); do
    cmd="docker start $each"
    echo $cmd
    eval $cmd
  done
}

docker_start_all_containers() {
  for each in $(docker ps -a|grep $project_name |awk 'NF>1{print $NF}'); do
    cmd="docker start $each"
    echo $cmd
    eval $cmd
  done
}

##
## note ! this only cleans up the $$project_name images, not all, for all use 'docker rm $(docker ps -a -q)'
##
docker_remove_all_containers() {
  for each in $(docker ps -a|grep $project_name |awk 'NF>1{print $NF}'); do
    cmd="docker stop $each"
    echo $cmd
    eval $cmd
  done
  for each in $(docker ps -a|grep $project_name |awk 'NF>1{print $NF}'); do
    cmd="docker rm $each"
    echo $cmd
    eval $cmd
  done
  # stopy any ... docker stop $(docker ps -a -q)
}

docker_cleanup_dangling_images() {
  dangling_images=$(docker images -q --filter 'dangling=true')
  if [ "$dangling_images" != '' ]; then
    cmd="docker rmi -f $dangling_images"
    echo $cmd
    eval $cmd
  fi
}

##
## note ! this only cleans up the $$project_name images, not all, for all use 'docker rmi $(docker images -q)''
##
docker_remove_all_images() {
  docker_cleanup_dangling_images
  for each in $(docker images| grep $project_name| awk '{print $3;}'); do
    cmd="docker rmi -f $each"
    echo $cmd
    eval $cmd
  done
}

docker_htop() {
  container_name=$1
  if [ -e $container_name ]; then
    echo "variable container_name is not set. cannot continue"
    return 1
  fi
  docker exec -ti $container_name /usr/bin/htop
}

docker_top() {
  container_name=$1
  pid=$2
  if [ -e $container_name ]; then
    echo "variable container_name is not set. cannot continue"
    return 1
  fi
  if [ -e $pid ]; then
    echo "variable pid is not set. cannot continue"
    return 1
  fi
  docker exec -ti $container_name /usr/bin/top -H -p $pid
}

docker_iftop() {
  container_name=$1
  if [ -e $container_name ]; then
    echo "variable container_name is not set. cannot continue"
    return 1
  fi
  docker exec -ti $container_name /usr/sbin/iftop
}

docker_shell() {
  container_name=$1
  if [ -e $container_name ]; then
    echo "variable container_name is not set. cannot continue"
    return 1
  fi
  docker exec -ti $container_name /bin/bash
}
