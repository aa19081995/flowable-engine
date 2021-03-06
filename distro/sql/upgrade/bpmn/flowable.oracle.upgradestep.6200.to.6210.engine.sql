update ACT_GE_PROPERTY set VALUE_ = '6.2.1.0' where NAME_ = 'job.schema.version';

alter table ACT_RU_JOB add CUSTOM_VALUES_ID_ varchar(64);
alter table ACT_RU_TIMER_JOB add CUSTOM_VALUES_ID_ varchar(64);
alter table ACT_RU_SUSPENDED_JOB add CUSTOM_VALUES_ID_ varchar(64);
alter table ACT_RU_DEADLETTER_JOB add CUSTOM_VALUES_ID_ varchar(64);
alter table ACT_RU_HISTORY_JOB add CUSTOM_VALUES_ID_ varchar(64);

create index ACT_IDX_JOB_CUSTOM_VAL_ID on ACT_RU_JOB(CUSTOM_VALUES_ID_);
create index ACT_IDX_TJOB_CUSTOM_VAL_ID on ACT_RU_TIMER_JOB(CUSTOM_VALUES_ID_);
create index ACT_IDX_SJOB_CUSTOM_VAL_ID on ACT_RU_SUSPENDED_JOB(CUSTOM_VALUES_ID_);
create index ACT_IDX_DJOB_CUSTOM_VAL_ID on ACT_RU_DEADLETTER_JOB(CUSTOM_VALUES_ID_);

alter table ACT_RU_JOB
    add constraint ACT_FK_JOB_CUSTOM_VAL
    foreign key (CUSTOM_VALUES_ID_)
    references ACT_GE_BYTEARRAY (ID_);

alter table ACT_RU_TIMER_JOB
    add constraint ACT_FK_TJOB_CUSTOM_VAL
    foreign key (CUSTOM_VALUES_ID_)
    references ACT_GE_BYTEARRAY (ID_);

alter table ACT_RU_SUSPENDED_JOB
    add constraint ACT_FK_SJOB_CUSTOM_VAL
    foreign key (CUSTOM_VALUES_ID_)
    references ACT_GE_BYTEARRAY (ID_);

alter table ACT_RU_DEADLETTER_JOB
    add constraint ACT_FK_DJOB_CUSTOM_VAL
    foreign key (CUSTOM_VALUES_ID_)
    references ACT_GE_BYTEARRAY (ID_);