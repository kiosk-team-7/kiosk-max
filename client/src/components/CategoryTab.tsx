import { useRef, useState } from "react";
import style from "./CategoryTab.module.css";

interface CategoryTabProps {
  categories: string[];
  currentCategoryIndex: number;
  onClick: (index: number) => void;
}

export default function CategoryTab({ categories, currentCategoryIndex, onClick }: CategoryTabProps) {
  const [scrollPosition, setScrollPosition] = useState(0);
  const isDraggingRef = useRef(false);
  const startXRef = useRef(0);
  const tabContainerRef = useRef<HTMLUListElement>(null);

  const handleMouseDown = (event: React.MouseEvent<HTMLElement, MouseEvent>) => {
    isDraggingRef.current = true;
    startXRef.current = event.pageX - scrollPosition;
  };

  const handleMouseUp = () => {
    isDraggingRef.current = false;
  };

  const handleMouseMove = (event: React.MouseEvent<HTMLElement, MouseEvent>) => {
    if (!isDraggingRef.current) {
      return;
    }

    const { scrollWidth, clientWidth } = tabContainerRef.current!;
    const maxScrollPosition = scrollWidth - clientWidth;
    const newPosition = event.pageX - startXRef.current;

    const isPassedLeftEdge = newPosition > 0;
    const isPassedRightEdge = newPosition < -maxScrollPosition;

    if (isPassedLeftEdge) {
      setScrollPosition(0);
      return;
    }

    if (isPassedRightEdge) {
      setScrollPosition(-maxScrollPosition);
      return;
    }

    setScrollPosition(newPosition);
  };

  const handleMouseLeave = () => {
    isDraggingRef.current = false;
  };

  const scrollableStyle = {
    transform: `translateX(${scrollPosition}px)`,
  };

  return (
    <nav
      className={style.Tab}
      onMouseDown={handleMouseDown}
      onMouseUp={handleMouseUp}
      onMouseMove={handleMouseMove}
      onMouseLeave={handleMouseLeave}
    >
      <ul className={style.TabContainer} style={scrollableStyle} ref={tabContainerRef}>
        {categories.map((category, index) => {
          const isCurrentCategory = index === currentCategoryIndex;

          return (
            <TabItem
              key={category}
              name={category}
              isCurrentCategory={isCurrentCategory}
              onClick={() => {onClick(index)}}
            />
          );
        })}
      </ul>
    </nav>
  );
}

interface TabItemProps {
  name: string;
  isCurrentCategory: boolean;
  onClick: () => void;
}

function TabItem({ name, isCurrentCategory, onClick }: TabItemProps) {
  const tabItemClass = isCurrentCategory ? style.CurrentTabItem : style.TabItem;
  return (
    <li className={tabItemClass} onClick={onClick}>
      {name}
    </li>
  );
}
