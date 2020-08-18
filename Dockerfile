FROM anapsix/alpine-java

RUN apk add --update unzip \
  && apk add --no-cache tzdata
ENV TZ Asia/Bangkok
ENV JAVA_HOME /opt/jdk
ARG configfile=src/main/resources/application.conf
WORKDIR /root
COPY target/universal/*.zip /root/build.zip
RUN unzip /root/build.zip -d /root/release \
  && rm  /root/build.zip
COPY $configfile /root/application.conf

# Define default command.
CMD ["/root/release/bin/dark-frame", "-Dhttp.address=0.0.0.0", "-Dconfig.file=/root/application.conf", "-Dlogger.resource=logback.xml"]
