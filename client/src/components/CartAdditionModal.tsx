import style from "./CartAdditionModal.module.css";
import MenuItem from "./MenuItem";

interface CartAdditionModalProps {
  menu: Menu | null;
  handleBackdropClick: () => void;
}

export default function CartAdditionModal({ menu, handleBackdropClick }: CartAdditionModalProps) {
  return (
    <div className={style.ModalContainer}>
      <div className={style.Backdrop} onClick={handleBackdropClick}></div>
      <div className={style.Modal}>
        <div className={style.MenuInfo}>
          <div className={style.MenuItemWrapper}>{menu && <MenuItem menu={menu} handleMenuItemClick={() => {}} />}</div>
          <div className={style.MenuItemOptions}>
            <div className={style.MenuItemOption}></div>
            <div className={style.MenuItemOption}></div>
            <div className={style.MenuItemOption}></div>
          </div>
        </div>
        {/* Add menu button */}
      </div>
    </div>
  );
}
