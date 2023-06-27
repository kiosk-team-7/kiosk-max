import Dim from "./Dim";
import Modal from "./Modal";
import OptionButton from "./OptionButton";
import styles from "./Payment.module.css";

interface PaymentSelectionModalProps {
  closeModal: () => void;
  selectCardPayment: () => void;
  selectCashPayment: () => void;
}

export function PaymentSelectionModal({
  closeModal,
  selectCardPayment,
  selectCashPayment,
}: PaymentSelectionModalProps) {
  return (
    <Modal closeModal={closeModal}>
      <div className={styles.PaymentContainer}>
        <div className={styles.PaymentOption}>
          <div className={styles.PaymentIcon}>💳</div>
          <OptionButton type={"Payment"} text={"카드결제"} onClick={selectCardPayment} />
        </div>
        <div className={styles.PaymentOption}>
          <div className={styles.PaymentIcon}>💵</div>
          <OptionButton type={"Payment"} text={"현금결제"} onClick={selectCashPayment} />
        </div>
      </div>
    </Modal>
  );
}

interface PaymentSpinnerProps {
  requestPayment: () => void;
}

export function PaymentSpinner({ requestPayment }: PaymentSpinnerProps) {
  return (
    <Dim>
      <div className={styles.Spinner}></div>
      <div className={styles.SpinnerContent}>카드 결제중...</div>
    </Dim>
  );
}

interface CashPaymentModalProps {
  totalPrice: number;
  requestPayment: () => void;
}

export function CashPaymentModal({ totalPrice, requestPayment }: CashPaymentModalProps) {
  // 투입 얼마했는지 상태
}
