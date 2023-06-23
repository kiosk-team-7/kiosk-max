insert into category (name, create_at) values ('커피', now());
insert into category (name, create_at) values ('라뗴', now());
insert into category (name, create_at) values ('티', now());
insert into category (name, create_at) values ('쥬스', now());
insert into category (name, create_at) values ('디카페인', now());
insert into category (name, create_at) values ('스무디', now());
insert into category (name, create_at) values ('프라푸치노', now());
insert into category (name, create_at) values ('탄산음료', now());
insert into category (name, create_at) values ('생수', now());

insert into menu (category_id, name, price, image_src, create_at) values (1L, '아메리카노', 4000, 'https://image', now());
insert into menu (category_id, name, price, image_src, create_at) values (1L, '카페모카', 4500, 'https://image', now());
insert into menu (category_id, name, price, image_src, create_at) values (6L, '초코스무디', 4500, 'https://image', now());
insert into menu (category_id, name, price, image_src, create_at) values (6L, '밀크스무디', 4000, 'https://image', now());
insert into menu (category_id, name, price, image_src, create_at) values (9L, '에비앙', 100000, 'https://image', now());
insert into menu (category_id, name, price, image_src, create_at) values (2L, '1등급 우유를 넣은 라떼', 20000, 'https://google.com', now());
insert into menu (category_id, name, price, image_src, create_at) values (8L, '김빠진 펩시콜라', 5000, 'https://google.com', now());
insert into menu (category_id, name, price, image_src, create_at) values (2L, '카페라떼', 10000, 'https://google.com', now());
insert into menu (category_id, name, price, image_src, create_at) values (3L, '보이차', 1000000, 'https://google.com', now());


insert into menu_rank (menu_id, sell_at) values (1L, now());
insert into menu_rank (menu_id, sell_at) values (1L, now());
insert into menu_rank (menu_id, sell_at) values (1L, now());
insert into menu_rank (menu_id, sell_at) values (2L, now());
insert into menu_rank (menu_id, sell_at) values (2L, now());
insert into menu_rank (menu_id, sell_at) values (2L, now());
insert into menu_rank (menu_id, sell_at) values (2L, now());


