create table clog(
  ID         NUMBER not null,
  BID        NUMBER,
  SYSNAME    VARCHAR2(100),
  LVL        VARCHAR2(3),
  TIME_STAMP DATE default sysdate not null,
  MSG        VARCHAR2(4000),
  LINENO     NUMBER,
  MODULE     VARCHAR2(222),
  ENVINF     VARCHAR2(4000),
  USR        VARCHAR2(30),
  CALLT      VARCHAR2(40),
  CODE       VARCHAR2(333),
  SES_IP     VARCHAR2(30),
  SES_HOST   VARCHAR2(100),
  SES_TERM   VARCHAR2(50),
  SES_MODULE VARCHAR2(50),
  SES_USR    VARCHAR2(30),
  LOG_TIME   TIMESTAMP(6),
  ORA_SID    VARCHAR2(30)
)
partition by range (time_stamp)
(
  partition PMINVAL values less than(TO_DATE('2016-01-01 00:00:00','SYYYY-MM-DD HH24:MI:SS')),
  partition P20160201 values less than (TO_DATE('2016-02-01 00:00:00', 'SYYYY-MM-DD HH24:MI:SS')),
  partition P20160301 values less than (TO_DATE('2016-03-01 00:00:00', 'SYYYY-MM-DD HH24:MI:SS')),
  partition P20160401 values less than (TO_DATE('2016-04-01 00:00:00', 'SYYYY-MM-DD HH24:MI:SS')),
  partition P20160501 values less than (TO_DATE('2016-05-01 00:00:00', 'SYYYY-MM-DD HH24:MI:SS')),
  partition P20160601 values less than (TO_DATE('2016-06-01 00:00:00', 'SYYYY-MM-DD HH24:MI:SS')),
  partition P20160701 values less than (TO_DATE('2016-07-01 00:00:00', 'SYYYY-MM-DD HH24:MI:SS')),
  partition P20160801 values less than (TO_DATE('2016-08-01 00:00:00', 'SYYYY-MM-DD HH24:MI:SS')),
  partition P20160901 values less than (TO_DATE('2016-09-01 00:00:00', 'SYYYY-MM-DD HH24:MI:SS')),
  partition P20161001 values less than (TO_DATE('2016-10-01 00:00:00', 'SYYYY-MM-DD HH24:MI:SS')),
  partition P20161101 values less than (TO_DATE('2016-11-01 00:00:00', 'SYYYY-MM-DD HH24:MI:SS')),
  partition P20161201 values less than (TO_DATE('2016-12-01 00:00:00', 'SYYYY-MM-DD HH24:MI:SS')),
  partition PMAXVAL values   less than (MAXVALUE)
)
;

comment on table clog  is '全局数据库日志信息表';
comment on column CLOG.ID
  is '序列号';
comment on column CLOG.BID
  is '当日志内容太长的时候（超过4000字节），日志拆分后的页数';
comment on column CLOG.LVL
  is '日志级别 TRC/DBG/INF/WRN/ERR/SAY';
comment on column CLOG.SYSNAME
  is '记录日志所对应的系统名称，底层保证全大写，此含义取代了appname，appname字段仅用于控制日志级别';
comment on column CLOG.USR
  is '记录日志的应用层的用户名';
comment on column CLOG.CALLT
  is '调用者的类型，应用自主决定放什么，常见的有：JAVA/PROCEDURE/PACKAGE/etc...';
comment on column CLOG.MODULE
  is '程序模块';
comment on column CLOG.LINENO
  is '发起日志调用的具体行数';
comment on column CLOG.CODE
  is '万金油字段，应用自主决定放什么';
comment on column CLOG.MSG
  is '日志内容';
comment on column CLOG.TIME_STAMP
  is '时间戳';
comment on column CLOG.ENVINF
  is '环境信息，应用自主决定放什么';
comment on column CLOG.SES_IP
  is 'session ip';
comment on column CLOG.SES_HOST
  is 'session host';
comment on column CLOG.SES_TERM
  is 'session terminal';
comment on column CLOG.SES_MODULE
  is 'session module';
comment on column CLOG.SES_USR
  is 'session user';
comment on column CLOG.LOG_TIME
  is '调用处日志生成时点';
comment on column CLOG.ORA_SID
  is '日志所在的数据库实例名称'; 

grant select on clog to PUBLIC;
create index idx_clog_timestamp on clog (time_stamp) local;