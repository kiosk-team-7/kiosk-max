import Modal from "../../Modal";
import styles from "./ClearConfirmModal.module.css";

type ClearConfirmModalProps = {
  closeModal: () => void;
  removeAllItems: () => void;
};

export default function ClearConfirmModal({
  closeModal,
  removeAllItems,
}: ClearConfirmModalProps) {
  const handleConfirmButtonClick = () => {
    removeAllItems();
    closeModal();
  };

  return (
    <Modal closeModal={closeModal}>
      <>
        <div className={styles.ModalContent}>
          장바구니에 담긴 상품 모두 삭제하시겠습니까?
        </div>
        <div className={styles.ButtonContainer}>
          <button
            className={styles.ConfirmButton}
            onClick={handleConfirmButtonClick}
          >
            예
          </button>
          <button className={styles.CancelButton} onClick={closeModal}>
            아니오
          </button>
        </div>
      </>
    </Modal>
  );
}
