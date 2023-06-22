import MenuItem from "./MenuItem";
import style from "./MenuList.module.css";

interface MenuListProps {
  menus: Menu[];
}

export default function MenuList({ menus }: MenuListProps) {
  return (
    <main className={`${style.MenuList} ${style.AnimateIn}`}>
      <ul className={style.MenuListContainer}>
        {menus.map((menu) => (
          <MenuItem key={menu.id} menu={menu} />
        ))}
      </ul>
    </main>
  );
}
