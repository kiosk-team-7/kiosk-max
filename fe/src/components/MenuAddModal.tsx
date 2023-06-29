import { useEffect, useState } from "react";
import { Size, Temperature } from "../types/constants";
import styles from "./MenuAddModal.module.css";
import MenuItem from "./MenuItem";
import OptionButton from "./MenuAddModal/OptionButton";
import Modal from "./Modal";

type MenuAddModalProps = {
  menu: Menu;
  closeModal: () => void;
  addMenuToCart: (item: CartItem) => void;
};

export default function MenuAddModal({
  menu,
  closeModal,
  addMenuToCart,
}: MenuAddModalProps) {
  const [size, setSize] = useState<Size>(Size.UNCHECKED);
  const [temperature, setTemperature] = useState<Temperature>(
    Temperature.UNCHECKED
  );
  const [count, setCount] = useState(1);
  const [isAllOptionsSelected, setIsAllOptionsSelected] = useState(false);

  useEffect(() => {
    checkAllOptionsSelected(size, temperature);
  }, [size, temperature]);

  const handlePlusButtonClick = () => {
    setCount((q) => q + 1);
  };

  const handleMinusButtonClick = () => {
    if (count > 1) {
      setCount((q) => q - 1);
    }
  };

  const checkAllOptionsSelected = (size: Size, temperature: Temperature) => {
    if (size === Size.UNCHECKED || temperature === Temperature.UNCHECKED) {
      return;
    }

    setIsAllOptionsSelected(true);
  };

  const handleAddMenuButtonClick = () => {
    const newCartItem = {
      id: menu.id,
      name: menu.name,
      price: menu.price,
      imageSrc: menu.imageSrc,
      options: {
        size,
        temperature,
      },
      count,
    };

    addMenuToCart(newCartItem);
    closeModal();
  };

  return (
    <Modal closeModal={closeModal}>
      <div className={styles.Content}>
        <div className={styles.MenuInfo}>
          <div className={styles.MenuItemWrapper}>
            {menu && <MenuItem menu={menu} />}
          </div>
          <div className={styles.MenuItemOptions}>
            <div className={styles.MenuItemOption}>
              <OptionButton
                type={"Size"}
                text="큰거"
                isSelected={size === Size.BIG}
                onClick={() => setSize(Size.BIG)}
              />
              <OptionButton
                type={"Size"}
                text="작은거"
                isSelected={size === Size.SMALL}
                onClick={() => setSize(Size.SMALL)}
              />
            </div>
            <div className={styles.MenuItemOption}>
              <OptionButton
                type={"Temperature"}
                text="뜨거운것"
                isSelected={temperature === Temperature.HOT}
                onClick={() => setTemperature(Temperature.HOT)}
              />
              <OptionButton
                type={"Temperature"}
                text="차가운것"
                isSelected={temperature === Temperature.ICE}
                onClick={() => setTemperature(Temperature.ICE)}
              />
            </div>
            <div className={styles.MenuItemOption}>
              <QuantityCounter
                quantity={count}
                handlePlusButtonClick={handlePlusButtonClick}
                handleMinusButtonClick={handleMinusButtonClick}
              />
            </div>
          </div>
        </div>
        <AddMenuButton
          isAllOptionsSelected={isAllOptionsSelected}
          handleAddMenuButtonClick={handleAddMenuButtonClick}
        />
      </div>
    </Modal>
  );
}

type QuantityCounterProps = {
  quantity: number;
  handlePlusButtonClick: () => void;
  handleMinusButtonClick: () => void;
};

function QuantityCounter({
  quantity,
  handlePlusButtonClick,
  handleMinusButtonClick,
}: QuantityCounterProps) {
  return (
    <>
      <button
        className={styles.CounterController}
        onClick={handleMinusButtonClick}
      >
        -
      </button>
      <span className={styles.Counter}>{quantity}</span>
      <button
        className={styles.CounterController}
        onClick={handlePlusButtonClick}
      >
        +
      </button>
    </>
  );
}

type AddMenuButtonProps = {
  isAllOptionsSelected: boolean;
  handleAddMenuButtonClick: () => void;
};

function AddMenuButton({
  isAllOptionsSelected,
  handleAddMenuButtonClick,
}: AddMenuButtonProps) {
  return (
    <button
      className={styles.AddMenuButton}
      disabled={!isAllOptionsSelected}
      onClick={handleAddMenuButtonClick}
    >
      담기
    </button>
  );
}
