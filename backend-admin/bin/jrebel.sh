git pull origin dev
mvn clean package
kill $(lsof -i:8179|awk '{print $2}' |sort|uniq|awk '{if($0!="PID") print ""$0" " }')
nohup java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5008 -agentpath:/usr/local/java/jrebel/lib/libjrebel64.so  -Drebel.remoting_plugin=true -Xdebug  -jar kg-admin/target/kg-admin.jar &
tail -n 500 -f nohup.out
