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

comment on table clog  is 'ȫ�����ݿ���־��Ϣ��';
comment on column CLOG.ID
  is '���к�';
comment on column CLOG.BID
  is '����־����̫����ʱ�򣨳���4000�ֽڣ�����־��ֺ��ҳ��';
comment on column CLOG.LVL
  is '��־���� TRC/DBG/INF/WRN/ERR/SAY';
comment on column CLOG.SYSNAME
  is '��¼��־����Ӧ��ϵͳ���ƣ��ײ㱣֤ȫ��д���˺���ȡ����appname��appname�ֶν����ڿ�����־����';
comment on column CLOG.USR
  is '��¼��־��Ӧ�ò���û���';
comment on column CLOG.CALLT
  is '�����ߵ����ͣ�Ӧ������������ʲô���������У�JAVA/PROCEDURE/PACKAGE/etc...';
comment on column CLOG.MODULE
  is '����ģ��';
comment on column CLOG.LINENO
  is '������־���õľ�������';
comment on column CLOG.CODE
  is '������ֶΣ�Ӧ������������ʲô';
comment on column CLOG.MSG
  is '��־����';
comment on column CLOG.TIME_STAMP
  is 'ʱ���';
comment on column CLOG.ENVINF
  is '������Ϣ��Ӧ������������ʲô';
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
  is '���ô���־����ʱ��';
comment on column CLOG.ORA_SID
  is '��־���ڵ����ݿ�ʵ������'; 

grant select on clog to PUBLIC;
create index idx_clog_timestamp on clog (time_stamp) local;