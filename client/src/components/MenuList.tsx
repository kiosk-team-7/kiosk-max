import { AnimationClass } from "../App";
import MenuItem from "./MenuItem";
import style from "./MenuList.module.css";

interface MenuListProps {
  menus: Menu[];
  handleMenuItemClick: (menu: Menu) => void;
  animation: AnimationClass;
}

export default function MenuList({ menus, handleMenuItemClick, animation }: MenuListProps) {
  return (
    <main className={`${style.MenuList} ${style[animation]}`}>
      <ul className={style.MenuListContainer}>
        {menus.map((menu) => (
          <li className={style.MenuItem} key={menu.id} onClick={() => handleMenuItemClick(menu)}>
            <MenuItem menu={menu} />
          </li>
        ))}
      </ul>
    </main>
  );
}
