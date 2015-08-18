
  #install X and xfce desktop Optional
sudo yum -y groupinstall "X Window System"
sudo yum -y groupinstall xfce
sudo yum -y install x2goserver-xsession
sudo yum -y install xfce4-terminal
sudo curl -O crunchy-dark-grey.tar.gz https://dl.orangedox.com/xCs7czovfGqWuOhBgm/crunchy-dark-grey.tar.gz?dl=1
sudo tar -xvf crunchy-dark-grey.tar.gz
sudo mv crunchy-dark-grey /usr/share/themes
sudo rm -f  crunchy-dark-grey.tar.gz
