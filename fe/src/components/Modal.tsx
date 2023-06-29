import Dim from "./Dim";
import styles from "./Modal.module.css";
import CloseButton from "./CloseButton";

type ModalProps = {
  closeModal?: () => void;
  children: JSX.Element;
};

export default function Modal({ closeModal, children }: ModalProps) {
  return (
    <Dim onClick={closeModal}>
      <div className={styles.Modal}>
        {closeModal && <CloseButton onClick={closeModal} />}
        <div className={styles.ModalContent}>{children}</div>
      </div>
    </Dim>
  );
}
