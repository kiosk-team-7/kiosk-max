import style from "./MenuItem.module.css";

interface MenuProps {
  menu: Menu;
}

export default function MenuItem({ menu }: MenuProps) {
  return (
    <li className={style.MenuItem}>
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
