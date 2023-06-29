import { useEffect, useState } from "react";
import { Size, Temperature } from "../../../types/constants";
import Modal from "../../Modal";
import OptionTypes from "./OptionTypes";
import { ButtonStyle } from "./OptionButton";
import MenuItem from "../MenuItem";
import styles from "./MenuAddModal.module.css";

type MenuAddModalProps = {
  menu: Menu;
  closeModal: () => void;
  addMenuToCart: (item: CartItem) => void;
};

type MenuOption = { size: Size; temperature: Temperature; quantity: number };

export default function MenuAddModal({
  menu,
  closeModal,
  addMenuToCart,
}: MenuAddModalProps) {
  const [menuOption, setMenuOption] = useState<MenuOption>({
    size: Size.UNCHECKED,
    temperature: Temperature.UNCHECKED,
    quantity: 1,
  });
  const [isAllOptionsSelected, setIsAllOptionsSelected] = useState(false);

  useEffect(() => {
    checkAllOptionsSelected(menuOption);
  }, [menuOption]);

  const handlePlusButtonClick = () => {
    setMenuOption((prev) => {
      return {
        ...prev,
        quantity: prev.quantity + 1,
      };
    });
  };

  const handleMinusButtonClick = () => {
    setMenuOption((prev) => {
      if (prev.quantity === 1) {
        return prev;
      }

      return {
        ...prev,
        quantity: prev.quantity - 1,
      };
    });
  };

  const checkAllOptionsSelected = (menuOption: MenuOption) => {
    if (
      menuOption.size === Size.UNCHECKED ||
      menuOption.temperature === Temperature.UNCHECKED
    ) {
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
        size: menuOption.size,
        temperature: menuOption.temperature,
      },
      count: menuOption.quantity,
    };

    addMenuToCart(newCartItem);
    closeModal();
  };

  const optionTypes = optionTypesGenerator(menuOption, setMenuOption);
  const counter = {
    quantity: menuOption.quantity,
    handlePlusButtonClick,
    handleMinusButtonClick,
  };

  return (
    <Modal closeModal={closeModal}>
      <div className={styles.Content}>
        <div className={styles.MenuInfo}>
          <div className={styles.MenuItemWrapper}>
            {menu && <MenuItem menu={menu} />}
          </div>
          <div className={styles.MenuItemOptions}>
            <OptionTypes optionTypes={optionTypes} counter={counter} />
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

const optionTypesGenerator = (
  menuOption: MenuOption,
  setMenuOption: React.Dispatch<React.SetStateAction<MenuOption>>
) => {
  const sizeData = [
    { text: "큰거", value: Size.BIG },
    { text: "작은거", value: Size.SMALL },
  ];
  const temperatureData = [
    { text: "뜨거운 것", value: Temperature.HOT },
    { text: "차가운 것", value: Temperature.ICE },
  ];

  const sizeResult = sizeData.map((item) => {
    return {
      text: item.text,
      isSelected: menuOption.size === item.value,
      onClick: () =>
        setMenuOption({
          ...menuOption,
          size: item.value,
        }),
    };
  });

  const temperatureResult = temperatureData.map((item) => {
    return {
      text: item.text,
      isSelected: menuOption.temperature === item.value,
      onClick: () =>
        setMenuOption({
          ...menuOption,
          temperature: item.value,
        }),
    };
  });

  return [
    {
      type: ButtonStyle.SIZE,
      options: sizeResult,
    },
    {
      type: ButtonStyle.TEMPERATURE,
      options: temperatureResult,
    },
  ];
};
