#!/bin/sh


check_network_socket_state() {
  local ip=$1
  if [ -e $ip ]; then
    echo "variable ip1 is not set. cannot continue"
    return 1
  fi
  # the netstat command will return a summarized list of sockets in _WAIT state to a remote ip
  response=$(netstat -anl | grep $ip | awk '/^tcp/ {t[$NF]++}END{for(state in t){print state, t[state]} }')
  if [ "$response" == "" ]; then
    return 0
  else
    echo $response
    return 1
  fi
}

validate_service_url() {
  local url=$1
  if [ -e $url ]; then
    echo "variable url is not set. cannot continue"
    return 1
  fi
  response_code=$(curl --write-out %{http_code} --silent --output /dev/null $url)
  if [ "$response_code" -ne "200" ]; then
    echo "bad url specified as $url. server returned $response_code. check server or specify correct url. cannot continue";
    return 1
  else
    return 0
  fi
}


port_forward() {
  local port=$1
  local toport=$2
  #
  # native ports have to be allowed through the firewall individually,
  # docker port-forwarding doesn't require this manual configuration,
  # all ports exposed through docker containers are allow to pass
  # through the firewall by default
  #
  firewall-cmd --add-forward-port=port=$port:proto=tcp:toport=$toport
}

open_firewall_port() {
  local ports=$1
  running=$(systemctl status firewalld |grep inactive)
  if [ "$running" != "" ]; then #exit if firewalld is not running
    echo $port |tr ',' '\n' |while read port; do #attempt to open each port specfied
      cmd="firewall-cmd --add-port=$port/tcp"
      if [ ! $( id -u ) -eq 0 ]; then #if not root user run sudo
        cmd="sudo $cmd"
      fi
      eval $cmd
    done
  fi
}
