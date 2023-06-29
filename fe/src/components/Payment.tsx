import { useState } from "react";
import Dim from "./Dim";
import Modal from "./Modal";
import OptionButton from "./OptionButton";
import styles from "./Payment.module.css";

type PaymentSelectionModalProps = {
  closeModal: () => void;
  selectCardPayment: () => void;
  selectCashPayment: () => void;
};

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
          <OptionButton
            type={"Payment"}
            text={"카드결제"}
            onClick={selectCardPayment}
          />
        </div>
        <div className={styles.PaymentOption}>
          <div className={styles.PaymentIcon}>💵</div>
          <OptionButton
            type={"Payment"}
            text={"현금결제"}
            onClick={selectCashPayment}
          />
        </div>
      </div>
    </Modal>
  );
}

export function PaymentSpinner() {
  return (
    <Dim>
      <div className={styles.Spinner}></div>
      <div className={styles.SpinnerContent}>카드 결제중...</div>
    </Dim>
  );
}

type CashPaymentModalProps = {
  totalPrice: number;
  closeModal: () => void;
  requestPayment: (inputAmount: number) => Promise<ResponseBody>;
  changePage: (path: Path, response: ResponseBody) => void;
};

const INPUT_OPTIONS = [100, 500, 1000, 5000, 10000, 50000];

export function CashPaymentModal({
  totalPrice,
  closeModal,
  requestPayment,
  changePage,
}: CashPaymentModalProps) {
  const [inputAmount, setInputAmount] = useState(0);

  const increaseInputAmount = (amount: number) => {
    setInputAmount((i) => i + amount);
  };

  const handleConfirmButtonClick = async () => {
    const response = await requestPayment(inputAmount);

    changePage("/result", response);
  };

  const isPaymentButtonActive = inputAmount >= totalPrice;

  return (
    <Modal>
      <>
        <div className={styles.InputOptionContainer}>
          {INPUT_OPTIONS.map((option) => (
            <div key={option} className={styles.InputOption}>
              <OptionButton
                type={"CashInput"}
                text={option + "원"}
                onClick={() => increaseInputAmount(option)}
              />
            </div>
          ))}
        </div>
        <div className={styles.OrderPriceContainer}>
          <div className={styles.OrderPrice}>
            주문 금액 : <span>{totalPrice}원</span>
          </div>
          <div className={styles.OrderPrice}>
            투입 금액 : <span>{inputAmount}원</span>
          </div>
        </div>
        <div className={styles.ConfirmButtonContainer}>
          <button
            className={`${styles.ConfirmButton} ${styles.CashPaymentCancelButton}`}
            onClick={closeModal}
          >
            결제 취소
          </button>
          <button
            className={`${styles.ConfirmButton} ${styles.CashPaymentConfirmButton}`}
            onClick={handleConfirmButtonClick}
            disabled={!isPaymentButtonActive}
          >
            현금 결제하기
          </button>
        </div>
      </>
    </Modal>
  );
}
