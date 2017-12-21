create table User
(
	id bigint auto_increment
		primary key,
	name varchar(50) not null,
	password varchar(41) not null,
	dateLastLogin datetime null,
	version bigint default 0 not null,
	constraint User_name_uindex
		unique (name)
)
;

