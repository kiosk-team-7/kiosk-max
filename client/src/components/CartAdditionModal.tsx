import { useState } from "react";
import MenuItem from "./MenuItem";
import style from "./CartAdditionModal.module.css";

interface CartAdditionModalProps {
  menu: Menu | null;
  handleBackdropClick: () => void;
}

export default function CartAdditionModal({ menu, handleBackdropClick }: CartAdditionModalProps) {
  const [quantity, setQuantity] = useState(1);

  const handlePlusButtonClick = () => {
    setQuantity((q) => q + 1);
  };

  const handleMinusButtonClick = () => {
    if (quantity > 1) {
      setQuantity((q) => q - 1);
    }
  };

  return (
    <div className={style.ModalContainer}>
      <div className={style.Backdrop} onClick={handleBackdropClick}></div>
      <div className={style.Modal}>
        <div className={style.MenuInfo}>
          <div className={style.MenuItemWrapper}>{menu && <MenuItem menu={menu} handleMenuItemClick={() => {}} />}</div>
          <div className={style.MenuItemOptions}>
            <div className={style.MenuItemOption}></div>
            <div className={style.MenuItemOption}></div>
            <div className={style.MenuItemOption}><QuantityCounter quantity={quantity} handlePlusButtonClick={handlePlusButtonClick} handleMinusButtonClick={handleMinusButtonClick} /></div>
          </div>
        </div>
        {/* Add menu button */}
      </div>
    </div>
  );
}

interface QuantityCounterProps {
  quantity: number;
  handlePlusButtonClick: () => void;
  handleMinusButtonClick: () => void;
}

function QuantityCounter({ quantity, handlePlusButtonClick, handleMinusButtonClick}: QuantityCounterProps) {
  return (
    <>
      <button className={style.CounterController} onClick={handleMinusButtonClick}>-</button>
      <span className={style.Counter}>{quantity}</span>
      <button className={style.CounterController} onClick={handlePlusButtonClick}>+</button>
    </>
  );
}
