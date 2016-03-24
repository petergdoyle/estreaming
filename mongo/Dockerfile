FROM estreaming/base

COPY mongodb.repo /etc/yum.repos.d/mongodb.repo
COPY setup.js /mongodb/setup.js
COPY find_frame_id.js /mongodb/find_frame_id.js

RUN yum -y install mongodb-org mongodb-org-server
RUN mkdir -p /data/db

ENTRYPOINT ["/usr/bin/mongod"]
