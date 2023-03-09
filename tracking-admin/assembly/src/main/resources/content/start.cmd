@echo off

set JAVA_OPTS=-Xmx256M -Xms256M -Xss1M -XX:+UseParallelGC

set JAVA_OPTS=%JAVA_OPTS% -XX:-PrintGC -XX:-PrintGCDetails -XX:-PrintGCTimeStamps -Xloggc:gc.log -XX:-UseGCLogFileRotation -XX:NumberOfGClogFiles=1 -XX:GCLogFileSize=5M

set CLASSPATH=.\lib\*;.\conf

"%JAVA_HOME%\bin\java" %JAVA_OPTS% -cp "%CLASSPATH%" -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.port=2631 -Djava.rmi.server.hostname=localhost ru.russianpost.tracking.portal.admin.Main
