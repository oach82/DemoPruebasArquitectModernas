FROM ubuntu:22.04

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update && \
    apt-get install -y openjdk-17-jre wget ca-certificates && \
    rm -rf /var/lib/apt/lists/*

COPY ZAP_2_17_0_unix.sh /opt/zap-installer.sh

RUN chmod +x /opt/zap-installer.sh && \
    /opt/zap-installer.sh -q -dir /opt/zap && \
    ln -sf /opt/zap/zap.sh /usr/local/bin/zap.sh

    
EXPOSE 8083

ENTRYPOINT []
CMD ["zap.sh"]
