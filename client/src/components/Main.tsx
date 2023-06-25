import { useState } from "react";
import MenuAddModal from "./MenuAddModal";
import MenuList from "./MenuList";
import { AnimationClass } from "../types/constants";

interface MainProps {
  menus: Menu[];
  animation: AnimationClass;
  changePage: (path: Path) => void;
}

export default function Main({ menus, animation, changePage }: MainProps) {
  const [isMenuAddModalOpen, setIsMenuAddModalOpen] = useState(false);
  const [selectedMenu, setSelectedMenu] = useState<Menu | null>(null);
  const [cartItems, setCartItems] = useState<CartItem[]>([]);

  const handleMenuItemClick = (menu: Menu) => {
    setSelectedMenu(menu);
    setIsMenuAddModalOpen(true);
  };

  const closeMenuAddModal = () => {
    setIsMenuAddModalOpen(false);
  };

  const reduceCartItems = (cartItems: CartItem[]): CartItem[] => {
    return cartItems.reduce((acc: CartItem[], cartItem: CartItem) => {
      const sameMenu = acc.find(
        (item) => item.id === cartItem.id && JSON.stringify(item.options) === JSON.stringify(cartItem.options)
      );

      if (sameMenu) {
        sameMenu.count += cartItem.count;
      } else {
        acc.push(cartItem);
      }

      return acc;
    }, []);
  };

  const addMenuToCart = (item: CartItem) => setCartItems(reduceCartItems([...cartItems, item]));

  return (
    <>
      <MenuList menus={menus} handleMenuItemClick={handleMenuItemClick} animation={animation} />
      {isMenuAddModalOpen && selectedMenu && (
        <MenuAddModal menu={selectedMenu} closeMenuAddModal={closeMenuAddModal} addMenuToCart={addMenuToCart} />
      )}
      {/* <Cart /> */}
    </>
  );
}
