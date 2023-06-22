import { useEffect, useState } from "react";
import style from "./App.module.css";
import CategoryTab from "./components/CategoryTab";
import MenuList from "./components/MenuList";

interface Beverage {
  category: string;
  menus: Menu[];
}

export default function App() {
  const [beverages, setBeverages] = useState<Beverage[]>([]);
  const [currentCategoryIndex, setCurrentCategoryIndex] = useState(0);

  useEffect(() => {
    let ignore = false;

    async function fetchBeveragesData() {
      try {
        const res = await fetch("http://localhost:3001/beverages");
        const data = await res.json();

        if (!ignore) {
          setBeverages(data);
        }
      } catch (error) {
        console.error("beverages 데이터를 불러오는데 실패했습니다.", error);
      }
    }

    fetchBeveragesData();

    return () => {
      ignore = true;
    };
  }, []);

  const categories = beverages.map((beverage) => beverage.category) ?? [];
  const currentMenus = beverages[currentCategoryIndex]?.menus ?? [];

  const handleCategoryChange = (index: number) => {
    setCurrentCategoryIndex(index);
  };

  return (
    <div className={style.App}>
      <CategoryTab //
        categories={categories}
        currentCategoryIndex={currentCategoryIndex}
        onClick={handleCategoryChange}
      />
      <MenuList menus={currentMenus} />
    </div>
  );
}
