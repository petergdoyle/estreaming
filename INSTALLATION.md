
### Installation
**Install Pre-requisites:**

1. [Virtualbox](https://www.virtualbox.org/wiki/Downloads)
2. [Vagrant](https://www.vagrantup.com/downloads.html)
3. Git ([Linux](), [Windows](https://msysgit.github.io/))
5. [X2Go Client](http://wiki.x2go.org/doku.php/download:start)


## Usage

**Create a VirtualBox VM**

For those on Windows, I will only include the instructions in bash (linux), so please install Git Bash  and then we will all be talking bash. I have idea how/if this works on Cygwin.

```sh
$ git clone [https://github.com/petergdoyle/estreaming.git] estreaming
```

Wherever you cloned your git repo, move to that directory ($ESTREAMING_REPO_HOME) and then get into the following location:

```sh
$ cd $ESTREAMING_REPO_HOME
```

You should see a file there called Vagrantfile. That has the details of how the new virtualbox box will be provisioned. **Please note** that the default machine spec is set to 2 vcpus and 2048 Mb memory allocation. This is the minimum acceptable vitural machine spec require to run the demo, so that may or may not work depending on the capabilities of the hardware of the host machine.

Pull a "base" box with this command.
```sh
$ vagrant box add https://github.com/holms/vagrant-centos7-box/releases/download/7.1.1503.001/CentOS-7.1.1503-x86_64-netboot.box --name  CentOS-7.1.1503-x86_64
```
Install the CentOS 7 fix
```sh
$ vagrant plugin install vagrant-centos7_fix
```

Start the box and you should see a bunch of update and intallation messages on the console. This may take a few minutes but run the command:

```sh
$ vagrant up
```

To suspend your box, use this command:

```sh
$ vagrant suspend
```

To remove the box altogether (maybe if problems installing or provisioning):

```sh
$ vagrant destroy
```

If everything went well after running ```vagrant up``` then you should be able to connect to the running box using ```vagrant ssh``` (again you have to be in the $ESTREAMING_REPO_HOME/vagrant/estreaming directory)


**Connect to the running box**

To connect from any ssh client:
Locate the private key for that box (linux, for windows refer to http://stackoverflow.com/questions/9885108/ssh-to-vagrant-box-in-windows)

```sh
$ vagrant ssh-config | grep IdentityFile  | awk '{print $2}'
```


Now you should have a running CentOS 7 Linux system with all the OS and estreaming components installed for you. Now you will be connect to the running system using X2Go.Once you have the location of the rsa private_key identified (mine happened to be $HOME/vagrant/estreaming/.vagrant/machines/default/virtualbox/private_key), connect to the the running virtual machine using X2Go. Add a new connection using the name "vagrant"and NO password but you must specify the location of the rsa private_key file you found with the previous command. As well under the window manager option, specify XFCE as the desktop manager. It will not work without these options.


As well, it is possible to use a regular ssh command to connect from the command line (on the host machine but we are going to open up a lot of terminals so that is not recommended)

```sh
$ ssh -i $ESTREAMING_REPO_HOME/estreaming/.vagrant/machines/default/virtualbox/private_key -l vagrant -p 2222 127.0.0.1
```


Now set up the [Streaming API Server](STREAMING_API_SERVER.md)
