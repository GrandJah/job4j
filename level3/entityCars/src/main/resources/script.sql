create table drivers
(
	id integer primary key,
	name varchar(255)
);

create table engines
(
	id integer primary key,
	model varchar(255),
	serialnumber varchar(255)
);

create table cars
(
	id integer primary key,
	serialnumber varchar(255),
	engine_id not null unique references engines(id)
);

create table history_owner
(
	id integer primary key,
	endOwner timestamp,
	startOwner timestamp,
	car_id integer not null references cars(id),
	driver_id integer not null references drivers(id)
);

