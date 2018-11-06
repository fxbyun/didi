/*==============================================================*/
/* Table: user_extension 用户扩展信息表，用于ExtensionFeatures测试                          */
/*==============================================================*/
create table user_extension
(
	id   					bigint not null auto_increment,
	name					varchar(20) not null,
	password				varchar(20) not null,
	features             	varchar(1024) not null default '{}' comment '扩展字段,要求:json结构',
	features_version		int not null default 0,
	primary key (id)
)engine=InnoDB default charset=utf8;