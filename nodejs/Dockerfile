
FROM estreaming/base

RUN yum -y install epel-release gcc gcc-c++
RUN yum -y install node npm

COPY mongodb.repo /etc/yum.repos.d/mongodb.repo
RUN yum -y install mongodb-org-shell

# install node packages
RUN npm install format-json-stream express express-generator forever -g

COPY cors_proxy/ /nodejs/cors_proxy/
COPY mongo_connect/ /nodejs/mongo_connect/
COPY streaming_api_client/ /nodejs/streaming_api_client/
COPY streaming_api_server/ /nodejs/streaming_api_server/
COPY twitter/ /nodejs/twitter/

WORKDIR /nodejs

CMD /bin/bash
