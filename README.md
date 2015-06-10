# estreaming
Create a twitter-like streaming server with spring-xd, mongodb, express, and node.js.

## Installation
Install Pre-requisites:
1. virtualbox
2. vagrant
3. git

**Install Virtualbox**
Follow instructions here https://www.virtualbox.org/wiki/Downloads

If you are running an rpm-based distro then you can find some Virtualbox pre-requisite instructions here http://wiki.centos.org/HowTos/Virtualization/VirtualBox

If you are running Ubuntu then you can find some Virtualbox pre-requisite instructions here https://help.ubuntu.com/community/VirtualBox/Installation


**Install Vagrant**
http://www.vagrantup.com/downloads

  If you are already on an rpm-based linux distro, install it with yum: ```sudo yum install vagrant```

  If you are on a deb-based linux distro, install it with apt-get ```sudo apt-get install vagrant```

  check vagrant website for specific details depending on your host platform.

**Install Git**
if you are on windows, follow instructions here https://git-scm.com/download/win
also if you are on windows, I would suggest installing Git Bash as well
https://msysgit.github.io/

if you are on linux or mac, follow instructions here
https://git-scm.com/book/en/v2/Getting-Started-Installing-Git

If you are behind a firewall, follow these instructions:
http://stackoverflow.com/questions/783811/getting-git-to-work-with-a-proxy-server


**Get the Code**
Pull the code and scripts from Git (you are already there if you are reading this README)
change to an appropriate directory location (likely off your home directory) and in a shell or git bash type
```git clone --depth 1  https://github.com/petergdoyle/estreaming.git```
this will copy clone the git repo.


## Usage

**Create a VirtualBox VM**

Whereever you cloned your git repo, move to that directory and then get into the following location:
```cd vagrant/estreaming```

You should see a file there called Vagrantfile. That has the details of how the new virtualbox box will be provisioned. **Please note** that the default machine spec is set to 2 vcpus and 2048 Mb memory allocation. This is the minimum acceptable vitural machine spec require to run the demo, so that may or may not work depending on the capabilities of the hardware of the host machine.

Pull a "base" box with this command. ```vagrant box add https://github.com/holms/vagrant-centos7-box/releases/download/7.1.1503.001/CentOS-7.1.1503-x86_64-netboot.box -- name  CentOS-7.1.1503-x86_64```
Install the CentOS7 fix
```vagrant plugin install vagrant-centos7_fix```

Start the box and you should see a bunch of update and intallation messages on the console. This may take a few minutes but run the command:
```vagrant up```

To suspend your box, use this command:
```vagrant suspend```

To remove the box altogether (maybe if problems installing or provisioning):
```vagrant destroy```

To connect to the running box using vagrant, move to the ~/vagrant/estreaming directory and type ```vagrant ssh```

To connect from any ssh client:
Locate the private key for that box
```vagrant ssh-config | grep IdentityFile  | awk '{print $2}'```

Once you have that location identified (mine happened to be $HOME/vagrant/estreaming/.vagrant/machines/default/virtualbox/private_key), use a regular ssh command to connect from the command line:
```ssh -XC -c blowfish-cbc,arcfour -i $HOME/vagrant/estreaming/.vagrant/machines/default/virtualbox/private_key -l vagrant -p 2222 127.0.0.1```

**Note**: if you are using PuTTY, that RSA Key must be provided in order to connect

**Note**: the commands are intended to run in Linux or in Git Bash, not from a windows command prompt.

If you want to avoid opening many terminals or PuTTY session, then an X Server and a XFCE Desktop has been installed on the virtual box. You can go get an x2go client here http://wiki.x2go.org/doku.php and connect using "vagrant" and the RSA key described above (without a password).

**Run the demo**
follow along here https://github.com/petergdoyle/estreaming/blob/master/scripts/spring_xd_demo_script it is also located in the scripts folder where you cloned this repo.


## History

TODO: Write history

## Credits

TODO: Write credits

## License

TODO: Write license
