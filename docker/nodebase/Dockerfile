
FROM estreaming/base

RUN yum -y install epel-release gcc gcc-c++
RUN yum -y install node npm

COPY mongodb.repo /etc/yum.repos.d/mongodb.repo
RUN yum -y install mongodb-org-shell

# install node packages
RUN npm install format-json-stream express express-generator forever -g

CMD /bin/bash
