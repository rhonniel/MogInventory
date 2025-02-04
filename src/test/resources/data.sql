insert into item_type(id,name) values(1,'Weapon');
insert into item_type(id,name) values(2,'Armor');
insert into item_type(id,name) values(3,'Headgear');
insert into item_type(id,name) values(4,'FootWear');
insert into item_type(id,name) values(5,'Accessory');
insert into item_type(id,name) values(6,'Objects');
insert into item_type(id,name) values(7,'Magic Tome');



insert into item_sub_type(id,item_type_id,name) values(1,1,'Sword');

insert into item(name,description,sub_type_id,quantity,enable) values('Sword of Pachas','Pacha power hydro',1,1,true);


