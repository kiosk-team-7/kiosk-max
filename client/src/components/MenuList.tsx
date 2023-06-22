interface MenuListProps {
  menus: Menu[];
}

export default function MenuList({ menus }: MenuListProps) {
  return (
    <main>
      <ul>
        {menus.map((menu) => (
          <MenuItem key={menu.id} menu={menu} />
        ))}
      </ul>
    </main>
  );
}

interface MenuProps {
  menu: Menu;
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
