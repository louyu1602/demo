CREATE DATABASE blog	DEFAULT CHARACTER SET utf8;
USE blog;

CREATE TABLE `article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `content` longtext COMMENT '内容',
  `keyWords` varchar(255) DEFAULT NULL COMMENT '关键字',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `columnId` int(11) DEFAULT NULL COMMENT '栏目',
  `label` varchar(255) DEFAULT NULL COMMENT '标签',
  `titleImgs` varchar(255) DEFAULT NULL COMMENT '标题图片列表',
  `status` varchar(255) DEFAULT NULL COMMENT '状态',
  `type` varchar(255) DEFAULT NULL COMMENT '公开度',
  `releaseTime` datetime DEFAULT NULL COMMENT '发布时间',
  `author` varchar(255) DEFAULT NULL COMMENT '作者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


CREATE TABLE `columns` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `columnName` varchar(255) DEFAULT NULL COMMENT '栏目名称',
  `aliasName` varchar(255) DEFAULT NULL COMMENT '栏目别名',
  `parentId` int(11) DEFAULT NULL COMMENT '父节点',
  `keyWords` varchar(255) DEFAULT NULL COMMENT '关键字',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `createBy` varchar(255) DEFAULT NULL COMMENT '创建人',
  `createDate` datetime DEFAULT NULL COMMENT '创建时间',
  `sort` int(11) DEFAULT NULL COMMENT '排序号',
  `status` varchar(255) DEFAULT NULL COMMENT '显示/隐藏',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

insert  into `columns`(`id`,`columnName`,`aliasName`,`parentId`,`keyWords`,`description`,`createBy`,`createDate`,`sort`,`status`) values (1,'前端技术','web',NULL,'before','前端技术','admin','2018-11-02 13:01:57',NULL,NULL),(2,'后端程序','program',NULL,'after','后端程序','admin','2018-11-02 13:02:00',NULL,NULL),(3,'管理系统','cms',NULL,'manager','管理系统','admin','2018-11-02 13:02:05',NULL,NULL),(4,'授人以渔','tutorial',NULL,'teach','授人以渔','admin','2018-11-02 13:02:06',NULL,NULL),(5,'程序人生','code',NULL,'code','程序人生','admin',NULL,NULL,NULL);


CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `titleId` int(11) DEFAULT NULL COMMENT '文章Id',
  `userId` int(11) DEFAULT NULL COMMENT '评论人Id',
  `content` varchar(2000) DEFAULT NULL COMMENT '评论内容',
  `commentTime` datetime DEFAULT NULL COMMENT '评论时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `flink` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '友链名字',
  `webUrl` varchar(255) DEFAULT NULL COMMENT '访问url',
  `linkImg` varchar(255) DEFAULT NULL COMMENT '连接图标',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `status` varchar(255) DEFAULT NULL COMMENT '状态',
  `openWays` varchar(255) DEFAULT NULL COMMENT '打开方式',
  `sort` int(11) DEFAULT NULL COMMENT '排序号',
  `createBy` varchar(255) DEFAULT NULL COMMENT '创建人',
  `createDate` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

insert  into `flink`(`id`,`name`,`webUrl`,`linkImg`,`description`,`status`,`openWays`,`sort`,`createBy`,`createDate`) values (1,'百度',NULL,'www.baidu.com','这是百度','1','1',1,'admin','2018-11-06 18:16:08'),(2,'百度','www.baidu.com','www.baidu.com','这是百度','1','1',1,'admin','2018-11-06 18:16:40'),(3,'百度','www.baidu.com','www.baidu.com','这是百度','1','1',1,'admin','2018-11-06 18:16:42'),(4,'百度','www.baidu.com','www.baidu.com','这是百度','1','1',1,'admin','2018-11-06 18:16:44'),(5,'百度','www.baidu.com','www.baidu.com','这是百度','1','1',1,'admin','2018-11-06 18:16:45'),(6,'百度','www.baidu.com','www.baidu.com','这是百度','1','1',1,'admin','2018-11-06 18:16:45'),(7,'百度','www.baidu.com','www.baidu.com','这是百度','1','1',1,'admin','2018-11-06 18:16:47'),(8,'百度','www.baidu.com','www.baidu.com','这是百度','1','1',1,'admin','2018-11-06 18:16:48'),(9,'æºè¾°','http://www.hyycinfo.com','http://www.hyycinfo.com','è¿æ¯è¡¡é³å¸æºè¾°ä¿¡æ¯çå®ç½',NULL,'1',0,NULL,'2018-11-06 18:30:20'),(10,'源辰','http://localhost:9999/Blog_Server/flink_add.jsp','http://localhost:9999/Blog_Server/flink_add.jsp','http://localhost:9999/Blog_Server/flink_add.jsp',NULL,'11',0,NULL,'2018-11-06 18:31:42'),(11,'1','2','3','4',NULL,'1',5,NULL,'2018-11-06 18:33:16'),(12,'1','2','3','4',NULL,'1',5,NULL,'2018-11-06 18:33:16');

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '用户名',
  `account` varchar(255) DEFAULT NULL COMMENT '账号',
  `pwd` varchar(255) DEFAULT NULL COMMENT '密码',
  `tel` varchar(255) DEFAULT NULL COMMENT '联系方式',
  `createBy` varchar(255) DEFAULT NULL COMMENT '创建人',
  `createDate` datetime DEFAULT NULL COMMENT '创建时间',
  `status` varchar(255) DEFAULT NULL COMMENT '状态		0 停用	1启用',
  `type` varchar(255) DEFAULT NULL COMMENT '用户类型 1系统管理员		2普通管理员3一般用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

insert  into `user`(`id`,`name`,`account`,`pwd`,`tel`,`createBy`,`createDate`,`status`,`type`) values (1,'张三','zhangsan','123456','13723706204','admin','2018-11-04 15:36:11','1','1');

CREATE TABLE `user_login_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL COMMENT '用户id',
  `time` datetime DEFAULT NULL COMMENT '用户登录时间',
  `loginIp` varchar(255) DEFAULT NULL COMMENT '登录ip',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;





