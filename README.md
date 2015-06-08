# estreaming
Create a twitter-like streaming server with spring-xd, mongodb, express, and node.js.

## Installation
Install Pre-requisites:
virtualbox
vagrant
git

Install Virtualbox
follow instructions here https://www.virtualbox.org/wiki/Downloads

Install Vagrant
http://www.vagrantup.com/downloads

if you are already on an rpm-based linux distro...
sudo yum install vagrant

if you are on a deb-based linux distro
sudo apt-get install vagrant

check vagrant website for details depending on your host platform.

Install Git
if you are on windows, follow instructions here https://git-scm.com/download/win
also if you are on windows, I would suggest installing Git Bash as well
https://msysgit.github.io/
if you are on linux or mac, follow instructions here
https://git-scm.com/book/en/v2/Getting-Started-Installing-Git

If you are behind a firewall, follow these instructions:
http://stackoverflow.com/questions/783811/getting-git-to-work-with-a-proxy-server


Get the Code
Pull the code and scripts from Git (you are already there if you are reading this README)
change to an appropriate directory location (likely off your home directory) and in a shell or git bash type
git clone --depth 1  https://github.com/petergdoyle/estreaming.git
this will copy clone the git repo.


## Usage
Create a VirtualBox VM

whereever you cloned your git repo, move to that directory and then get into the following location:
cd vagrant/estreaming

You should see a file there called Vagrantfile. That has the details of how the new box will be provisioned. Please note that the default machine spec is set to 2 vcpus and 2048 Mb memory allocation. This is the minimum acceptable vitural machine spec require to run the demo, so that may or may not work depending on the capabilities of the hardware of the host machine.

Pull a "base" box  https://github.com/holms/vagrant-centos7-box/releases/download/7.1.1503.001/CentOS-7.1.1503-x86_64-netboot.box
Install the CentOS7 fix
vagrant plugin install vagrant-centos7_fix

Start the box and you should see a bunch of update and intallation messages on the console. This may take a few minutes but run the command:
vagrant up

To suspend your box, use this command:
vagrant suspend

To remove the box altogether (maybe if problems installing or provisioning):
vagrant destroy

to connect from any ssh client...
locate the private key
vagrant ssh-config | grep IdentityFile  | awk '{print $2}'

ssh -XC -c blowfish-cbc,arcfour -i /home/peter/vagrant/estreaming/.vagrant/machines/default/virtualbox/private_key -l vagrant -p 2222 127.0.0.1


## History

TODO: Write history

## Credits

TODO: Write credits

## License

TODO: Write license
