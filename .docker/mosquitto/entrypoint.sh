#!/bin/sh

set -e

# Crear passwd con ambos usuarios
mosquitto_passwd -b -c /mosquitto/config/passwd "$MQTT_USER_PUB" "$MQTT_USER_PUB_PASSWORD"
mosquitto_passwd -b /mosquitto/config/passwd "$MQTT_USER_SUB" "$MQTT_USER_SUB_PASSWORD"

# Crear ACL
echo "user $MQTT_USER_PUB" > /mosquitto/config/acl
echo "topic write $MQTT_TOPIC" >> /mosquitto/config/acl
echo "" >> /mosquitto/config/acl
echo "user $MQTT_USER_SUB" >> /mosquitto/config/acl
echo "topic read $MQTT_TOPIC" >> /mosquitto/config/acl

# Crear mosquitto.conf
cat <<EOF > /mosquitto/config/mosquitto.conf
listener 1883
allow_anonymous false
password_file /mosquitto/config/passwd
acl_file /mosquitto/config/acl
EOF

chown -R 1883:1883 /mosquitto/config
chmod 0700 /mosquitto/config/acl

exec mosquitto -c /mosquitto/config/mosquitto.conf