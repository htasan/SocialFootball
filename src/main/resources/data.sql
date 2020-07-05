insert into player(id, name, age) values (NEXTVAL('HIBERNATE_SEQUENCE'), 'Mbappe', 20);
insert into player(id, name, age) values (NEXTVAL('HIBERNATE_SEQUENCE'), 'Ronaldo', 34);
insert into player(id, name, age) values (NEXTVAL('HIBERNATE_SEQUENCE'), 'Zlatan', 35);
insert into team(id, name, currency_code) values  (NEXTVAL('HIBERNATE_SEQUENCE'), 'Galatasaray', 'TRY');
insert into team(id, name, currency_code) values  (NEXTVAL('HIBERNATE_SEQUENCE'), 'Real Madrid', 'EUR');
insert into team(id, name, currency_code) values  (NEXTVAL('HIBERNATE_SEQUENCE'), 'Barcelona', 'EUR');
insert into team(id, name, currency_code) values  (NEXTVAL('HIBERNATE_SEQUENCE'), 'Manchester City', 'GBP');
insert into contract(id, start_date, end_date, player_id, team_id) values (NEXTVAL('HIBERNATE_SEQUENCE'), '2008-02-03 04:05:06', '2010-02-03 04:05:06', 1, 4);
insert into contract(id, start_date, end_date, player_id, team_id) values (NEXTVAL('HIBERNATE_SEQUENCE'), '2006-02-03 04:05:06', '2011-02-03 04:05:06', 3, 4);
insert into contract(id, start_date, end_date, player_id, team_id) values (NEXTVAL('HIBERNATE_SEQUENCE'), '2007-02-03 04:05:06', '2009-02-03 04:05:06', 2, 5);
insert into contract(id, start_date, end_date, player_id, team_id) values (NEXTVAL('HIBERNATE_SEQUENCE'), '2009-02-03 04:05:06', '2010-02-03 04:05:06', 2, 4);
insert into contract(id, start_date, end_date, player_id, team_id) values (NEXTVAL('HIBERNATE_SEQUENCE'), '2010-02-03 04:05:06', '2013-02-03 04:05:06', 2, 6);