insert into users(id, first_name, last_name, ssn, registration_id, is_active)
values(1, 'First Name', 'Last Name', 100, 1234567890, 1);
insert into accounts(id, pin, balance, is_active)
values(1, 1234, 100.00, 1);
commit;