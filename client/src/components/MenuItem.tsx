import style from "./MenuItem.module.css";

interface MenuProps {
  menu: Menu;
  handleMenuItemClick: () => void;
}

export default function MenuItem({ menu, handleMenuItemClick }: MenuProps) {
  return (
    <li className={style.MenuItem} onClick={handleMenuItemClick}>
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
