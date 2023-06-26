import styles from "./Modal.module.css";

interface ModalProps {
  closeModal: () => void;
  children: JSX.Element;
}

export default function Modal({ closeModal, children }: ModalProps) {
  return (
    <div className={styles.ModalContainer}>
      <div className={styles.Backdrop} onClick={closeModal}></div>
      <div className={styles.Modal}>
        <div className={styles.CloseButton} onClick={closeModal}>
          X
        </div>
        <div className={styles.ModalContent}>{children}</div>
      </div>
    </div>
  );
}
