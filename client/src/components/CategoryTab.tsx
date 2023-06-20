import { useRef, useState } from "react";
import style from "./CategoryTab.module.css";

export default function CategoryTab() {
  const [currentCategory, setCurrentCategory] = useState("커피");
  const [scrollPosition, setScrollPosition] = useState(0);
  const isDraggingRef = useRef(false);
  const startXRef = useRef(0);
  const tabContainerRef = useRef<HTMLUListElement>(null);

  const categories = ["커피", "라떼", "티", "쥬스", "디카페인"];

  const handleTabItemClick = (category: string) => {
    setCurrentCategory(category);
  };

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
        {categories.map((category) => {
          const isCurrentCategory = category === currentCategory;

          return (
            <TabItem
              key={category}
              name={category}
              isCurrentCategory={isCurrentCategory}
              handleTabItemClick={handleTabItemClick}
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
  handleTabItemClick: (category: string) => void;
}

function TabItem({ name, isCurrentCategory, handleTabItemClick }: TabItemProps) {
  return (
    <li className={isCurrentCategory ? style.CurrentTabItem : style.TabItem} onClick={() => handleTabItemClick(name)}>
      {name}
    </li>
  );
}
