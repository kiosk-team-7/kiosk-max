import styles from "./TabItem.module.css";

type TabItemProps = {
  name: string;
  isCurrentCategory: boolean;
  handleCategoryChange: () => void;
};

export default function TabItem({
  name,
  isCurrentCategory,
  handleCategoryChange,
}: TabItemProps) {
  const tabItemClass = isCurrentCategory
    ? styles.CurrentTabItem
    : styles.TabItem;
  return (
    <li className={tabItemClass} onClick={handleCategoryChange}>
      {name}
    </li>
  );
}
