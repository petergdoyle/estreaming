FROM estreaming/base

RUN  mkdir /usr/java \
&& curl -O -L --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/8u101-b13/jdk-8u101-linux-x64.tar.gz" \
&& tar -xvf jdk-8u101-linux-x64.tar.gz -C /usr/java \
&& ln -s /usr/java/jdk1.8.0_101/ /usr/java/default \
&& rm -f jdk-8u101-linux-x64.tar.gz

# RUN alternatives --install "/usr/bin/java" "java" "/usr/java/default/bin/java" 99999; \
#   alternatives --install "/usr/bin/javac" "javac" "/usr/java/default/bin/javac" 99999


# install the executables from bin
RUN for each in $(find "/usr/java/default/bin" -executable -type f) ; do name=$(basename $each); alternatives --install "/usr/bin/$name" "$name" "$each" 99999; done


#
# adding a jdk for all containers will double the size of each one whether they use java or not
# this env is only relevant for the containers that install the jdk
#
ENV JAVA_HOME=/usr/java/default
