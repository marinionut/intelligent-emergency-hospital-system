create schema if not exists `intelligent-emergency-hospital-system` collate utf8_general_ci;

create table if not exists alert
(
	id int auto_increment,
	username varchar(20) null,
	message varchar(2500) null,
	alert_type varchar(20) null,
	timestamp timestamp null,
	roomNumber int null,
	status int default 0 null,
	uid varchar(100) null,
	constraint alerts_id_uindex
		unique (id)
);

alter table alert
	add primary key (id);

create table if not exists doctor
(
	id int auto_increment,
	first_name varchar(20) null,
	last_name varchar(20) null,
	email varchar(30) not null,
	phone_number varchar(20) not null,
	specialization varchar(30) not null,
	user_id int null,
	constraint doctor_id_uindex
		unique (id)
);

alter table doctor
	add primary key (id);

create table if not exists doctor_staff
(
	id int auto_increment,
	first_name varchar(20) null,
	last_name varchar(20) null,
	email varchar(20) null,
	phone_number varchar(20) null,
	type varchar(20) null,
	doctor_id int null,
	constraint doctor_staff_id_uindex
		unique (id),
	constraint doctor_staff_doctor_id_fk
		foreign key (doctor_id) references doctor (id)
);

alter table doctor_staff
	add primary key (id);

create table if not exists medical_equipment
(
	id int auto_increment
		primary key,
	name varchar(100) null,
	roomNumber int null,
	floor int null,
	status tinyint(1) default 0 null
);

create table if not exists medication
(
	id int auto_increment,
	description varchar(20) null,
	start_date datetime null,
	end_date datetime null,
	constraint medication_id_uindex
		unique (id)
);

alter table medication
	add primary key (id);

create table if not exists patient
(
	id int auto_increment,
	first_name varchar(20) null,
	last_name varchar(20) null,
	phone_number varchar(20) null,
	address varchar(100) null,
	age int null,
	gender varchar(20) null,
	cnp mediumtext null,
	medication_id int null,
	constraint patient_id_uindex
		unique (id),
	constraint patient_medication_id_fk
		foreign key (medication_id) references medication (id)
);

alter table patient
	add primary key (id);

create table if not exists room
(
	id int auto_increment,
	description varchar(50) null,
	number int null,
	bed_number int null,
	constraint room_id_uindex
		unique (id),
	constraint room_number_uindex
		unique (number)
);

alter table room
	add primary key (id);

create table if not exists appointment
(
	id int auto_increment,
	patient_id int null,
	doctor_id int null,
	room_id int null,
	timestamp datetime null,
	bed_number int null,
	constraint appointment_id_uindex
		unique (id),
	constraint appointment_doctor_id_fk
		foreign key (doctor_id) references doctor (id),
	constraint appointment_patient_id_fk
		foreign key (patient_id) references patient (id),
	constraint appointment_room_id_fk
		foreign key (room_id) references room (id)
);

alter table appointment
	add primary key (id);

create table if not exists users
(
	id int auto_increment,
	username varchar(20) null,
	password varchar(2000) null,
	role varchar(20) null,
	constraint users_id_uindex
		unique (id)
);

alter table users
	add primary key (id);

