# Zap client api example
Example is taken from 
https://github.com/zaproxy/zap-api-java/blob/master/subprojects/zap-clientapi/src/examples/java/org/zaproxy/clientapi/examples/SimpleExample.java

## Run it
1. Download ZAP and start it in daemon mode
> ./zap.sh -daemon -port 8083

2. Deploy application in EAP

3. Run the test, where target is url of your application deployed in EAP 
> mvn clean verify -Dtarget="http://myurl:port/path"
