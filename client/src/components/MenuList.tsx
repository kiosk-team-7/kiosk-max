import MenuItem from "./MenuItem";
import style from "./MenuList.module.css";

interface MenuListProps {
  menus: Menu[];
  handleMenuItemClick: () => void;
}

export default function MenuList({ menus, handleMenuItemClick }: MenuListProps) {
  return (
    <main className={`${style.MenuList} ${style.AnimateIn}`}>
      <ul className={style.MenuListContainer}>
        {menus.map((menu) => (
          <MenuItem key={menu.id} menu={menu} handleMenuItemClick={handleMenuItemClick}/>
        ))}
      </ul>
    </main>
  );
}
