
# （指定基础image）
# 构建指令，必须指定且需要在Dockerfile其他指令的前面。后续的指令都依赖于该指令指定的image
# FROM指令指定的基础image可以是官方远程仓库中的，也可以位于本地仓库
FROM 192.168.8.128/smartmall/java:8u20

# 记录相关人员信息
MAINTAINER zhouxra ctsig "zhouxra@ctsig.com"

# 在image中设置环境变量
ENV WORK_PATH /home/root
ENV APP_NAME @project.build.finalName@.@project.packaging@
ENV APP_VERSION @project.version@
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 容器需要映射到宿主机器的端口 可映射多个端口
EXPOSE 2018

# 复制$APP_NAME 到指定的$WORK_PATH
COPY $APP_NAME $WORK_PATH/

# 切换目录 设置指令，可以多次切换(相当于cd命令)，对RUN,CMD,ENTRYPOINT生效，/home/root
WORKDIR $WORK_PATH

# -Xms1024M, -Xmx1024M jvm内存限制
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Xms1024M","-Xmx1024M"]

# container启动时执行脚本或系统命令
CMD ["-jar", "@project.build.finalName@.@project.packaging@"]