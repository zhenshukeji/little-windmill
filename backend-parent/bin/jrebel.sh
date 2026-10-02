git pull origin master
mvn clean package
#TODO 修改端口号与项目名称
kill $(lsof -i:8128|awk '{print $2}' |sort|uniq|awk '{if($0!="PID") print ""$0" " }')
nohup java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5011 -agentpath:/usr/local/java/jrebel/lib/libjrebel64.so  -Drebel.remoting_plugin=true -Xdebug  -jar target/fortuneWisdomLife.jar &
tail -n 500 -f nohup.out