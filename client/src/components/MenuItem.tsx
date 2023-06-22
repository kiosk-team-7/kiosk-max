interface MenuProps {
  menu: {
    id: number;
    name: string;
    price: number;
    isPopular: boolean;
    src: string;
  };
}

export default function MenuItem({ menu }: MenuProps) {
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
