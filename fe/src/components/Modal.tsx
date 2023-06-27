import Dim from "./Dim";
import styles from "./Modal.module.css";

interface ModalProps {
  closeModal?: () => void;
  children: JSX.Element;
}

export default function Modal({ closeModal, children }: ModalProps) {
  return (
    <Dim onClick={closeModal}>
      <div className={styles.Modal}>
        {closeModal && (
          <div className={styles.CloseButton} onClick={closeModal}>
            X
          </div>
        )}
        <div className={styles.ModalContent}>{children}</div>
      </div>
    </Dim>
  );
}
