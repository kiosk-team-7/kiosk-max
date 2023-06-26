import { useEffect, useState } from "react";
import styles from "./Home.module.css";
import CategoryTab from "./CategoryTab";
import Main from "./Main";
import { AnimationClass } from "../types/constants";
import { API_URL } from "../constants";

interface HomeProps {
  changePage: (path: Path) => void;
}

interface KioskData {
  category: string;
  menus: Menu[];
}

export default function Home({ changePage }: HomeProps) {
  const [kioskData, setKioskData] = useState<KioskData[]>([]);
  const [currentCategoryIndex, setCurrentCategoryIndex] = useState(0);
  const [animation, setAnimation] = useState<AnimationClass>(AnimationClass.ANIMATE_IN);

  useEffect(() => {
    let ignore = false;

    (async function fetchKioskData() {
      try {
        const res = await fetch(`${API_URL}/api/categories`);
        const data = await res.json();

        if (!ignore) {
          setKioskData(data);
        }
      } catch (error) {
        console.error("키오스크 데이터를 불러오는데 실패했습니다.", error);
      }
    })();

    return () => {
      ignore = true;
    };
  }, []);

  const categories = kioskData.map((beverage) => beverage.category);
  const currentMenus = kioskData[currentCategoryIndex]?.menus ?? [];

  const handleCategoryChange = (index: number) => {
    setAnimation(AnimationClass.ANIMATE_OUT);

    setTimeout(() => {
      setAnimation(AnimationClass.ANIMATE_IN);
      setCurrentCategoryIndex(index);
    }, 400);
  };

  return (
    <div className={styles.Home}>
      <CategoryTab
        categories={categories}
        currentCategoryIndex={currentCategoryIndex}
        handleCategoryChange={handleCategoryChange}
      />
      <Main menus={currentMenus} animation={animation} changePage={changePage} />
    </div>
  );
}
