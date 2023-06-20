export default function MenuList() {
  const menus = [
    {
      id: 1,
      name: "아메리카노",
      price: 3000,
      isPopular: true,
      src: "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[94]_20210430103337006.jpg",
    },
    {
      id: 2,
      name: "카페라떼",
      price: 3500,
      isPopular: true,
      src: "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[41]_20210415133833725.jpg",
    },
    {
      id: 3,
      name: "카페모카",
      price: 4000,
      isPopular: true,
      src: "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[46]_20210415134438165.jpg",
    },
    {
      id: 4,
      name: "바닐라라떼",
      price: 4000,
      isPopular: false,
      src: "https://image.istarbucks.co.kr/upload/store/skuimg/2021/02/[9200000001939]_20210225094313315.jpg",
    },
    {
      id: 5,
      name: "카라멜마끼아또",
      price: 4000,
      isPopular: false,
      src: "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[126197]_20210415154609863.jpg",
    },
  ];

  return (
    <main>
      <ul>
        {menus.map((menu) => (
          <MenuItem menu={menu} />
        ))}
      </ul>
    </main>
  );
}

interface MenuProps {
  menu: {
    id: number;
    name: string;
    price: number;
    isPopular: boolean;
    src: string;
  };
}

function MenuItem({ menu }: MenuProps) {
  return (
    <li>
      <img src={menu.src} alt={menu.name} />
      <span>{menu.name}</span>
      <span>{menu.price}</span>
      {menu.isPopular && <PopularMark />}
    </li>
  );
}

function PopularMark() {
  return (
    <div>
      <span>인기</span>
    </div>
  );
}