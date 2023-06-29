import styles from './MenuList.module.css';

interface MenuProps {
  menu: Menu;
}

export default function MenuItem({ menu }: MenuProps) {
  return (
    <>
      <img src={menu.imageSrc} alt={menu.name} />
      <span>{menu.name}</span>
      <span>{menu.price}</span>
      {menu.isPopular && <PopularMark />}
    </>
  );
}

function PopularMark() {
  return (
    <>
      <div className={styles.PopularMark}><span>인기</span></div>
    </>
  );
}
