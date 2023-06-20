import CategoryTab from "./components/CategoryTab";
import MenuList from "./components/MenuList";
import style from './App.module.css';

export default function App() {
  return (
    <div className={style.App}>
      <CategoryTab />
      <MenuList />
    </div>
  );
}
