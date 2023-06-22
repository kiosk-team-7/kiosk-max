import MenuItem from "./MenuItem";
import style from "./MenuList.module.css";

interface MenuListProps {
  menus: Menu[];
}

export default function MenuList({ menus }: MenuListProps) {
  return (
    <main className={style.AnimateIn}>
      <ul>
        {menus.map((menu) => (
          <MenuItem key={menu.id} menu={menu} />
        ))}
      </ul>
    </main>
  );
}
