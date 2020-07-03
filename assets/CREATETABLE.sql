/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020/7/3 18:20:56                            */
/*==============================================================*/


drop table if exists Coupon;

drop table if exists LTpromotion;

drop table if exists admin;

drop table if exists comments;

drop table if exists f_g_connect;

drop table if exists fresh_type;

drop table if exists full_discount_msg;

drop table if exists goods_msg;

drop table if exists location;

drop table if exists menu_msg;

drop table if exists orders;

drop table if exists orders_detail;

drop table if exists purchase;

drop table if exists recommand;

drop table if exists users;

/*==============================================================*/
/* Table: Coupon                                                */
/*==============================================================*/
create table Coupon
(
   cp_id                int not null,
   cp_desc              varchar(30) not null,
   cp_amount            int not null,
   cp_discount          int not null,
   cp_start_date        date not null,
   cp_end_date          date not null,
   primary key (cp_id)
);

/*==============================================================*/
/* Table: LTpromotion                                           */
/*==============================================================*/
create table LTpromotion
(
   ltp_id               int not null,
   g_id                 int not null,
   ltp_price            int not null,
   ltp_count            int not null,
   ltp_start_date       date not null,
   ltp_end_date         date not null,
   primary key (ltp_id)
);

/*==============================================================*/
/* Table: admin                                                 */
/*==============================================================*/
create table admin
(
   a_id                 int not null,
   a_name               varchar(20) not null,
   a_pwd                varchar(20) not null,
   primary key (a_id)
);

/*==============================================================*/
/* Table: comments                                              */
/*==============================================================*/
create table comments
(
   u_id                 int not null,
   g_id                 int not null,
   com_msg              varchar(30) not null,
   com_date             date not null,
   com_star             int not null,
   com_pic              longblob
);

/*==============================================================*/
/* Table: f_g_connect                                           */
/*==============================================================*/
create table f_g_connect
(
   fd_id                int not null,
   g_id                 int not null,
   start_date           date not null,
   end_date             date not null,
   primary key (fd_id, g_id)
);

/*==============================================================*/
/* Table: fresh_type                                            */
/*==============================================================*/
create table fresh_type
(
   type_id              int not null,
   type_name            varchar(20) not null,
   type_desc            varchar(30),
   primary key (type_id)
);

/*==============================================================*/
/* Table: full_discount_msg                                     */
/*==============================================================*/
create table full_discount_msg
(
   fd_id                int not null,
   fd_desc              varchar(30) not null,
   fd_needcount         int not null,
   fd_data              int not null,
   fd_start_date        date not null,
   fd_end_date          date not null,
   primary key (fd_id)
);

/*==============================================================*/
/* Table: goods_msg                                             */
/*==============================================================*/
create table goods_msg
(
   g_id                 int not null,
   type_id              int not null,
   g_name               varchar(20) not null,
   g_price              int not null,
   g_vipprice           int not null,
   g_count              int not null,
   g_specifications     varchar(50),
   g_desc               varchar(50),
   primary key (g_id)
);

/*==============================================================*/
/* Table: location                                              */
/*==============================================================*/
create table location
(
   location_id          int not null,
   u_id                 int not null,
   province             varchar(20) not null,
   city                 varchar(20) not null,
   area                 varchar(30) not null,
   location_desc        varchar(50) not null,
   linkman              varchar(10) not null,
   phonenumber          numeric(11,0) not null,
   primary key (location_id)
);

/*==============================================================*/
/* Table: menu_msg                                              */
/*==============================================================*/
create table menu_msg
(
   m_id                 int not null,
   m_name               varchar(20) not null,
   m_desc               varchar(50),
   m_step               varchar(100),
   m_pic                longblob,
   primary key (m_id)
);

/*==============================================================*/
/* Table: orders                                                */
/*==============================================================*/
create table orders
(
   o_id                 int not null,
   location_id          int,
   u_id                 int,
   o_old_price          int not null,
   o_new_price          int not null,
   o_coupon             int,
   o_gettime            date not null,
   o_status             varchar(20) not null,
   primary key (o_id)
);

/*==============================================================*/
/* Table: orders_detail                                         */
/*==============================================================*/
create table orders_detail
(
   goods_id             int not null,
   goods_count          int not null,
   goods_price          int not null,
   order_id             int not null,
   fd_data              int,
   fulld_id             int,
   primary key (order_id)
);

/*==============================================================*/
/* Table: purchase                                              */
/*==============================================================*/
create table purchase
(
   pur_id               int not null,
   a_id                 int not null,
   pur_num              int not null,
   pur_status           varchar(20) not null,
   primary key (pur_id)
);

/*==============================================================*/
/* Table: recommand                                             */
/*==============================================================*/
create table recommand
(
   g_id                 int not null,
   m_id                 int not null,
   rec_desc             varchar(50) not null,
   primary key (g_id, m_id)
);

/*==============================================================*/
/* Table: users                                                 */
/*==============================================================*/
create table users
(
   u_id                 int not null,
   u_name               varchar(20) not null,
   u_sex                varchar(10) not null,
   u_pwd                varchar(20) not null,
   u_phone              numeric(13,0) not null,
   u_email              varchar(30) not null,
   u_city               varchar(20) not null,
   u_createdate         date not null,
   u_isvip              varchar(10) not null,
   u_vip_end_date       date,
   primary key (u_id)
);

alter table LTpromotion add constraint FK_Relationship_4 foreign key (g_id)
      references goods_msg (g_id) on delete restrict on update restrict;

alter table comments add constraint FK_Relationship_2 foreign key (g_id)
      references goods_msg (g_id) on delete restrict on update restrict;

alter table comments add constraint FK_Relationship_3 foreign key (u_id)
      references users (u_id) on delete restrict on update restrict;

alter table f_g_connect add constraint FK_f_g_connect foreign key (fd_id)
      references full_discount_msg (fd_id) on delete restrict on update restrict;

alter table f_g_connect add constraint FK_f_g_connect2 foreign key (g_id)
      references goods_msg (g_id) on delete restrict on update restrict;

alter table goods_msg add constraint FK_Relationship_1 foreign key (type_id)
      references fresh_type (type_id) on delete restrict on update restrict;

alter table location add constraint FK_Relationship_5 foreign key (u_id)
      references users (u_id) on delete restrict on update restrict;

alter table orders add constraint FK_Relationship_6 foreign key (u_id)
      references users (u_id) on delete restrict on update restrict;

alter table orders add constraint FK_Relationship_8 foreign key (location_id)
      references location (location_id) on delete restrict on update restrict;

alter table purchase add constraint FK_Relationship_9 foreign key (a_id)
      references admin (a_id) on delete restrict on update restrict;

alter table recommand add constraint FK_recommand foreign key (g_id)
      references goods_msg (g_id) on delete restrict on update restrict;

alter table recommand add constraint FK_recommand2 foreign key (m_id)
      references menu_msg (m_id) on delete restrict on update restrict;

