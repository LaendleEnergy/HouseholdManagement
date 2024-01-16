insert into incentive (incentive_id, description, end_date) values ('incentive1', 'Einen Monat lang keine Wäsche machen.', '2100-09-30');
insert into incentive (incentive_id, description, end_date) values ('incentive2', 'Ein gratis Kinobesuch', '2100-09-30');

insert into energy_saving_target (saving_target_id, timeframe, percentage) values ('savingtarget1', 'Vormonat', 30);
insert into energy_saving_target (saving_target_id, timeframe, percentage) values ('savingtarget2', 'Vorjahr', 10);

insert into household (household_id, incentive_id, saving_target_id) values ('h1', 'incentive1', 'savingtarget1');
insert into household (household_id, incentive_id, saving_target_id) values ('h2', 'incentive2', 'savingtarget2');

insert into household_member (id, name, numberOfCreatedTags, household_id) values ('m3', 'Charlie', '0', 'h1');
insert into household_member (id, name, numberOfCreatedTags, household_id) values ('m2', 'Bob', '0', 'h2');
insert into household_member (id, name, numberOfCreatedTags, household_id) values ('1', 'Alice', '0', 'h1');
insert into household_member (id, name, numberOfCreatedTags, household_id) values ('2', 'Felix Lahnsteiner', '0', 'h2');

INSERT INTO public.devicecategory (category_name) VALUES ('fridge');
INSERT INTO public.devicecategory (category_name) VALUES ('microwave');