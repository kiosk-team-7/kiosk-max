import style from "./CartAdditionModal.module.css";

interface CartAdditionModalProps {
  handleBackdropClick: () => void;
}

export default function CartAdditionModal({handleBackdropClick}: CartAdditionModalProps) {
  return (
    <div className={style.ModalContainer}>
      <div className={style.Backdrop} onClick={handleBackdropClick}></div>
      <div className={style.Modal}></div>
    </div>
  );
}
