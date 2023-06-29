import { AnimationClass } from "../types/constants";
import MenuItem from "./MenuItem";
import styles from "./MenuList.module.css";

type MenuListProps = {
  menus: Menu[];
  handleMenuItemClick: (menu: Menu) => void;
  animation: AnimationClass;
};

export default function MenuList({
  menus,
  handleMenuItemClick,
  animation,
}: MenuListProps) {
  return (
    <main className={`${styles.MenuList} ${styles[animation]}`}>
      <ul className={styles.MenuListContainer}>
        {menus.map((menu) => (
          <li
            className={styles.MenuItem}
            key={menu.id}
            onClick={() => handleMenuItemClick(menu)}
          >
            <MenuItem menu={menu} />
          </li>
        ))}
      </ul>
    </main>
  );
}
