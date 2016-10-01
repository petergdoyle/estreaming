
Vagrant.configure(2) do |config|

  config.vm.box = "petergdoyle/CentOS-7-x86_64-Minimal-1503-01"

  config.vm.network "forwarded_port", guest: 22, host: 5222, host_ip: "0.0.0.0", id: "ssh", auto_correct: true
  config.vm.network "forwarded_port", guest: 3000, host: 3000, host_ip: "0.0.0.0", id: "streaming api port", auto_correct: true
  config.vm.network "forwarded_port", guest: 9889, host: 9889, host_ip: "0.0.0.0", id: "xd analytics api port", auto_correct: true
  config.vm.network "forwarded_port", guest: 9393, host: 9393, host_ip: "0.0.0.0", id: "groovy analytics d3 dashboard port", auto_correct: true

  config.vm.network "forwarded_port", guest: 8080, host: 8080, host_ip: "0.0.0.0", id: "google cAdvisor port", auto_correct: true

  config.vm.network "forwarded_port", guest: 8161, host: 8161, host_ip: "0.0.0.0", id: "amq broker", auto_correct: true
  config.vm.network "forwarded_port", guest: 1414, host: 1414, host_ip: "0.0.0.0", id: "ibm mq broker", auto_correct: true

  config.vm.provider "virtualbox" do |vb|
    vb.customize ["modifyvm", :id, "--cpuexecutioncap", "80"]
    vb.cpus=4 #recommended=4 if available
    vb.memory = "4096" #recommended=3072 or 4096 if available
  end

  config.vm.provision "shell", inline: <<-SHELL

  #best to update the os
  yum -y update && yum -y clean
  yum -y install vim htop curl wget tree unzip bash-completion telnet net-tools jq

  eval 'docker --version' > /dev/null 2>&1
  if [ $? -eq 127 ]; then
  #install docker service
  cat >/etc/yum.repos.d/docker.repo <<-EOF
[dockerrepo]
name=Docker Repository
baseurl=https://yum.dockerproject.org/repo/main/centos/7
enabled=1
gpgcheck=1
gpgkey=https://yum.dockerproject.org/gpg
EOF
  yum -y install docker
  systemctl start docker.service
  systemctl enable docker.service

  #allow non-#access to run docker commands for user vagrant
  #if you have problems running docker as the vagrant user on the vm (if you 'vagrant ssh'd in
  #after a 'vagrant up'), then
  #restart the host machine and ssh in again to the vm 'vagrant halt; vagrant up; vagrant ssh'
  groupadd docker
  usermod -aG docker vagrant

  #install docker-compose.
  #Compose is a tool for defining and running multi-container applications with Docker.
  yum -y install python-pip
  pip install -U docker-compose
  else
    echo -e "\e[7;44;96mdocker already appears to be installed. skipping.\e[0m"
  fi

  eval 'java -version' > /dev/null 2>&1
  if [ $? -eq 127 ]; then
    mkdir -p /usr/java
    #install java jdk 8 from oracle
    curl -O -L --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" \
    "http://download.oracle.com/otn-pub/java/jdk/8u60-b27/jdk-8u60-linux-x64.tar.gz" \
      && tar -xvf jdk-8u60-linux-x64.tar.gz -C /usr/java \
      && ln -s /usr/java/jdk1.8.0_60/ /usr/java/default \
      && rm -f jdk-8u60-linux-x64.tar.gz

    alternatives --install "/usr/bin/java" "java" "/usr/java/default/bin/java" 99999; \
    alternatives --install "/usr/bin/javac" "javac" "/usr/java/default/bin/javac" 99999; \
    alternatives --install "/usr/bin/javaws" "javaws" "/usr/java/default/bin/javaws" 99999; \
    alternatives --install "/usr/bin/jvisualvm" "jvisualvm" "/usr/java/default/bin/jvisualvm" 99999

    export JAVA_HOME=/usr/java/default
    cat >/etc/profile.d/java.sh <<-EOF
export JAVA_HOME=$JAVA_HOME
EOF

  else
    echo -e "\e[7;44;96mjava already appears to be installed. skipping."
  fi

  eval 'mvn -version' > /dev/null 2>&1
  if [ $? -eq 127 ]; then
    mkdir /usr/maven
    #install maven
    curl -O http://www-us.apache.org/dist/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.tar.gz \
      && tar -xvf apache-maven-3.3.9-bin.tar.gz -C /usr/maven \
      && ln -s /usr/maven/apache-maven-3.3.9 /usr/maven/default \
      && rm -f apache-maven-3.3.9-bin.tar.gz

    alternatives --install "/usr/bin/mvn" "mvn" "/usr/maven/default/bin/mvn" 99999

    export MAVEN_HOME=/usr/maven/default
    cat >/etc/profile.d/maven.sh <<-EOF
export MAVEN_HOME=$MAVEN_HOME
EOF

  else
    echo -e "\e[7;44;96mmaven already appears to be installed. skipping."
  fi

  eval $'node --version' > /dev/null 2>&1
  if [ $? -eq 127 ]; then
    #install node.js and npm
    yum -y install epel-release gcc gcc-c++ \
    && yum -y install nodejs npm

    # NPM Proxy Settings
    #npm config set proxy $HTTP_PROXY
    #vnpm config set https-proxy $HTTP_PROXY

    #useful node.js packages
    npm install format-json-stream -g
    npm install lorem-ipsum -g
    npm install forever -g
    npm install monitor-dashboard -g

  else
    echo -e "\e[7;44;96node, npm, npm-libs already appear to be installed. skipping."
  fi


  #install mongodb and start it and enable it at startup
  curl -o /etc/yum.repos.d/mongodb.repo --insecure https://gist.githubusercontent.com/petergdoyle/7451a7f694b20df709cc/raw/b01b001478b40fc52f333b0ff9f9cb7ac2a25ac7/mongodb.repo
  yum -y install mongodb-org mongodb-org-server
  #systemctl start mongod
  #chkconfig mongod on
  #systemctl status mongod
  #mongo -version
  #su - vagrant -c 'mongo < /vagrant/mongo/setup.js'


  #install spring boot
  #install gvm first
  yum -y install unzip \
  && curl -s get.sdkman.io | bash

  su - vagrant -c 'source "/home/vagrant/.gvm/bin/gvm-init.sh"'
  printf "gvm_auto_answer=true\ngvm_auto_selfupdate=true\n" > /home/vagrant/.gvm/etc/config
  su - vagrant -c 'gvm install springboot groovy'
  su - vagrant -c 'spring --version'


  export GOPATH=/home/vagrant/go
  cat >/etc/profile.d/go.sh <<-EOF
export GOPATH=$GOPATH
EOF

  eval $'go version' > /dev/null 2>&1
  if [ $? -eq 127 ]; then
    yum -y install golang gpm
    mkdir $GOPATH
  else
    echo -e "\e[7;44;96go already appears to be installed. skipping."
  fi

  eval "$GOPATH/bin/burrow" > /dev/null 2>&1
  if [ $? -eq 127 ]; then
    go get github.com/linkedin/burrow
    cd $GOPATH/src/github.com/linkedin/burrow
    gpm install
    go install
  else
    echo -e "\e[7;44;96burrow already appears to be installed. skipping."
  fi


  # on the vm host you need to open up some temporary ports on the firewall
  # if you are running on fedora or centos7 this is done with firewalld commands
  # firewall-cmd --add-port=3000/tcp
  # firewall-cmd --add-port=9889/tcp
  # firewall-cmd --add-port=9393/tcp

  #set hostname
  hostnamectl set-hostname estreaming.vbx

  SHELL
end
