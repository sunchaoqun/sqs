# sqs

config.properites 
根据自己的环境设置参数

通过代码中CreateFIFOQueue 创建 FIFO Queue 或者直接从AWS Web Console创建均可

修改pom.xml , 确定生成的是发送客户端还是接收客户端
    <archive>
        <manifest>
        <mainClass>org.scq.sqs.SendFIFOMessage</mainClass>
        </manifest>
    </archive>

执行 mvn package 对java进行打包

mv target/sqs-0.0.1-SNAPSHOT-jar-with-dependencies.jar send.jar

命名为 send.jar read.jar 方便进行测试

java -jar send.jar 

java -jar read.jar 

请注意config.properties 要跟jar文件在同级目录

如下：

drwxrwxr-x  5 ubuntu ubuntu     4096 Mar 18 12:35 ./
drwxr-xr-x 76 ubuntu ubuntu     4096 Mar 18 12:27 ../
-rw-rw-r--  1 ubuntu ubuntu      324 Mar 18 12:31 config.properties
drwxrwxr-x  8 ubuntu ubuntu     4096 Mar 18 12:35 .git/
-rw-rw-r--  1 ubuntu ubuntu       18 Mar 18 12:27 .gitignore
-rw-rw-r--  1 ubuntu ubuntu     2153 Mar 18 12:31 pom.xml
-rw-rw-r--  1 ubuntu ubuntu 12551798 Mar 18 12:28 read.jar
-rw-rw-r--  1 ubuntu ubuntu        5 Mar 18 12:27 README.md
-rw-rw-r--  1 ubuntu ubuntu 12551797 Mar 18 12:35 send.jar
drwxrwxr-x  4 ubuntu ubuntu     4096 Mar 18 12:27 src/
drwxrwxr-x 10 ubuntu ubuntu     4096 Mar 18 12:35 target/