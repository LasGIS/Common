#!/bin/bash

JAVA_HOME=/usr/java/jdk-eight

JAVA_OPTS="-Xmx512M -Xms512M -Xss1M -XX:+UseParallelGC"

CLASSPATH=./lib/*:./conf

# jmx
JAVA_OPTS="${JAVA_OPTS} -Dcom.sun.management.jmxremote.port=2631 -Djava.rmi.server.hostname=localhost -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false"

# debug
#JAVA_OPTS="${JAVA_OPTS} -Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,address=8453,server=y,suspend=n -Djava.compiler=NONE"

# gc logs
JAVA_OPTS=${JAVA_OPTS} -XX:-PrintGC -XX:-PrintGCDetails -XX:-PrintGCTimeStamps -Xloggc:gc.log -XX:-UseGCLogFileRotation -XX:NumberOfGClogFiles=1 -XX:GCLogFileSize=5M

rm -R log

$JAVA_HOME/bin/java $JAVA_OPTS -cp "$CLASSPATH" ru.russianpost.tracking.portal.admin.Main
