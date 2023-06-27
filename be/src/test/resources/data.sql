insert into category (name, create_at) values ('커피', now());
insert into category (name, create_at) values ('라떼', now());
insert into category (name, create_at) values ('티', now());
insert into category (name, create_at) values ('쥬스', now());
insert into category (name, create_at) values ('디카페인', now());

INSERT INTO menu (category_id, name, price, image_src, create_at) VALUES
    (1,'아메리카노', 3000, 'https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[94]_20210430103337006.jpg', '2023-06-23 15:40:15'),
    (1,'카페라떼', 3500, 'https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[41]_20210415133833725.jpg', '2023-06-23 15:40:15'),
    (1,'카페모카', 4000, 'https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[94]_20210430103337006.jpg', '2023-06-23 15:40:15'),
    (1,'바닐라라떼', 4000, 'https://image.istarbucks.co.kr/upload/store/skuimg/2021/02/[9200000001939]_20210225094313315.jpg', '2023-06-23 15:40:15'),
    (1,'카라멜마끼아또', 4000, 'https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[126197]_20210415154609863.jpg', '2023-06-23 15:40:15'),
    (2,'바닐라 빈 라떼', 4500, 'https://image.istarbucks.co.kr/upload/store/skuimg/2021/02/[9200000001939]_20210225094313315.jpg', '2023-06-23 15:40:15'),
    (2,'돌체 라떼', 5500, 'https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[128692]_20210426091933665.jpg', '2023-06-23 15:40:15'),
    (2,'말차 라떼', 5500,'https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000002496]_20210419131039350.jpg', '2023-06-23 15:40:15'),
    (2,'차이나 티 라떼', 5000, 'https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[135608]_20210415154244810.jpg', '2023-06-23 15:40:15'),
    (3,'민트 블렌드 티', 5500, 'https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[4004000000056]_20210415135215632.jpg', '2023-06-23 15:40:15'),
    (3,'블루밍 프루트 유스베리 티', 6000, 'https://image.istarbucks.co.kr/upload/store/skuimg/2022/09/[9200000004306]_20220902105252734.jpg', '2023-06-23 15:40:15'),
    (3,'얼 그레이', 6000, 'https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[4004000000036]_20210415143933425.jpg', '2023-06-23 15:40:15'),
    (3,'케모마일 블렌드 티', 5500, 'https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[4004000000076]_20210415154920731.jpg', '2023-06-23 15:40:15'),
    (3,'히비스커스 블렌드 티', 5500, 'https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[4004000000066]_20210415155836395.jpg', '2023-06-23 15:40:15'),
    (4,'망고주스', 5000, 'https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[5210008061]_20210426095229362.jpg', '2023-06-23 15:40:15'),
    (4,'케일 & 사과주스', 6000, 'https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[5210008072]_20210426100712780.jpg', '2023-06-23 15:40:15'),
    (4,'한라봉 주스', 5500, 'https://image.istarbucks.co.kr/upload/store/skuimg/2023/03/[5210008055]_20230331142558998.jpg', '2023-06-23 15:40:16'),
    (4,'햇사과 주스', 5500, 'https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9300000002565]_20210426102753285.jpg', '2023-06-23 15:40:16'),
    (5,'에스프레소 프라푸치노', 5000, 'https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[168007]_20210415144337428.jpg', '2023-06-23 15:40:16'),
    (5,'자바 칩 프라푸치노', 6000, 'https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[168016]_20210415154152122.jpg', '2023-06-23 15:40:16'),
    (5,'카라멜 프라푸치노', 6000, 'https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[168010]_20210415154711116.jpg', '2023-06-23 15:40:16'),
    (5,'화이트 초콜릿 모카 프라푸치노', 5500, 'https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[168013]_20210415155746379.jpg', '2023-06-23 15:40:16'),
    (5,'피스타치오 아보카도 초콜릿 프라푸치노', 5500, 'https://image.istarbucks.co.kr/upload/store/skuimg/2023/05/[9200000004713]_20230524144814035.jpg', '2023-06-23 15:40:16');

INSERT INTO menu_rank (menu_id, sell_at) VALUES
     (5, now()), (5, now()), (5, now()), (5, now()), (5, now()),
     (5, now()), (5, now()), (5, now()), (5, now()), (5, now()),
     (5, now()), (5, now()), (5, now()), (5, now()), (5, now()),
     (5, now()), (5, now()), (5, now()), (5, now()), (5, now()),
     (5, now()), (5, now()), (5, now()), (5, now()), (5, now()),
     (1, now()), (1, now()), (1, now()), (1, now()), (1, now()),
     (1, now()), (1, now()), (1, now()), (1, now()), (1, now()),
     (1, now()), (1, now()), (1, now()), (1, now()), (1, now()),
     (1, now()), (1, now()), (1, now()), (1, now()), (1, now()),
     (1, now()), (1, now()), (1, now()), (1, now()), (1, now()),
     (1, now()), (1, now()), (1, now()), (1, now()), (1, now()),
     (8, now()), (8, now()), (8, now()), (8, now()), (8, now()),
     (8, now()), (8, now()), (8, now()), (8, now()), (8, now()),
     (8, now()), (8, now()), (8, now()), (8, now()), (8, now()),
     (8, now()), (8, now()), (8, now()), (8, now()), (8, now()),
     (8, now()), (8, now()), (8, now()), (8, now()), (8, now()), (8, now());
