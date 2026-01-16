FROM docker.io/jenkins/jenkins:lts

# Usar root para instalar dependencias
USER root

# Instalar Podman, podman-compose, Node.js, Newman y utilidades
RUN apt-get update && \
    apt-get install -y podman podman-compose curl nodejs npm && \
    npm install -g newman && \
    apt-get clean

# Instalar plugins de Jenkins antes del arranque
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli --plugin-file /usr/share/jenkins/ref/plugins.txt

# Scripts de inicialización
COPY init.groovy.d/ /usr/share/jenkins/ref/init.groovy.d/

# Crear carpeta de trabajo
RUN mkdir -p /workspace && \
    chown -R jenkins:jenkins /workspace && \
    chmod -R 775 /workspace

# ⚠️ Mantener USER root para que Podman funcione sin errores
# No cambiar a USER jenkins