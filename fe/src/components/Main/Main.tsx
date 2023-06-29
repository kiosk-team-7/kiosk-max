import { useState } from "react";
import { AnimationClass } from "../../types/constants";
import Cart from "./Cart/Cart";
import MenuList from "./MenuList";
import MenuAddModal from "./MenuAddModal/MenuAddModal";

type MainProps = {
  menus: Menu[];
  animation: AnimationClass;
  changePage: (path: Path) => void;
};

export default function Main({ menus, animation, changePage }: MainProps) {
  const [isMenuAddModalOpen, setIsMenuAddModalOpen] = useState(false);
  const [selectedMenu, setSelectedMenu] = useState<Menu | null>(null);
  const [cartItems, setCartItems] = useState<CartItem[]>([]);

  const handleMenuItemClick = (menu: Menu) => {
    setSelectedMenu(menu);
    setIsMenuAddModalOpen(true);
  };

  const closeModal = () => {
    setIsMenuAddModalOpen(false);
  };

  const reduceCartItems = (cartItems: CartItem[]): CartItem[] => {
    return cartItems.reduce((acc: CartItem[], cartItem: CartItem) => {
      const sameMenu = acc.find(
        (item) =>
          item.id === cartItem.id &&
          JSON.stringify(item.options) === JSON.stringify(cartItem.options)
      );

      if (sameMenu) {
        sameMenu.count += cartItem.count;
      } else {
        acc.push(cartItem);
      }

      return acc;
    }, []);
  };

  const addMenuToCart = (item: CartItem) =>
    setCartItems(reduceCartItems([...cartItems, item]));

  const removeAllItems = () => {
    setCartItems([]);
  };

  const removeItem = (id: number) => {
    setCartItems(cartItems.filter((item) => item.id !== id));
  };

  return (
    <>
      <MenuList
        menus={menus}
        handleMenuItemClick={handleMenuItemClick}
        animation={animation}
      />
      {isMenuAddModalOpen && selectedMenu && (
        <MenuAddModal
          menu={selectedMenu}
          closeModal={closeModal}
          addMenuToCart={addMenuToCart}
        />
      )}
      {cartItems.length !== 0 && (
        <Cart
          cartItems={cartItems}
          removeItem={removeItem}
          removeAllItems={removeAllItems}
          changePage={changePage}
        />
      )}
    </>
  );
}
