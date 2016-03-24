
FROM estreaming/nodejs

COPY cors_proxy/ /nodejs/cors_proxy/
COPY mongo_connect/ /nodejs/mongo_connect/
COPY streaming_api_client/ /nodejs/streaming_api_client/
COPY streaming_api_server/ /nodejs/streaming_api_server/
COPY twitter/ /nodejs/twitter/

WORKDIR /nodejs

CMD /bin/bash
