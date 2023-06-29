import { useEffect, useRef, useState } from "react";
import { API_URL } from "../../../constants";
import { PaymentType, Size, Temperature } from "../../../types/constants";
import { Spinner } from "../../Spinner";
import { CashPaymentModal, PaymentSelectionModal } from "../Payment";
import ClearConfirmModal from "./ClearConfirmModal";
import CartItem from "./CartItem";
import styles from "./Cart.module.css";

type CartProps = {
  cartItems: CartItem[];
  removeItem: (id: number) => void;
  removeAllItems: () => void;
  changePage: (path: Path, response: ResponseBody) => void;
};

interface PaymentRequestBody {
  menus: {
    id: number;
    count: number;
    size: Size;
    temperature: Temperature;
  }[];
  inputAmount: number;
  totalPrice: number;
  paymentType: PaymentType;
}

const WAITING_TIME = 60;

export default function Cart({
  cartItems,
  removeItem,
  removeAllItems,
  changePage,
}: CartProps) {
  const [isPaymentModalOpen, setIsPaymentModalOpen] = useState(false);
  const [isIndicatorVisible, setIsIndicatorVisible] = useState(false);
  const [isClearConfirmModalOpen, setIsClearConfirmModalOpen] = useState(false);
  const [isCashPaymentModalOpen, setIsCashPaymentModalOpen] = useState(false);
  const [remainingTime, setRemainingTime] = useState(WAITING_TIME);
  const [prevCartItems, setPrevCartItems] = useState<CartItem[]>(cartItems);
  const paymentTypeRef = useRef<PaymentType>();

  useEffect(() => {
    const interval = setInterval(() => {
      setRemainingTime((r) => r - 1);
    }, 1000);

    if (
      isPaymentModalOpen ||
      isCashPaymentModalOpen ||
      isClearConfirmModalOpen ||
      isIndicatorVisible
    ) {
      clearInterval(interval);
    }

    return () => {
      clearInterval(interval);
    };
  }, [
    isPaymentModalOpen,
    isIndicatorVisible,
    isClearConfirmModalOpen,
    isCashPaymentModalOpen,
  ]);

  useEffect(() => {
    if (remainingTime < 0) {
      removeAllItems();
    }
  }, [remainingTime, removeAllItems]);

  if (prevCartItems.length !== cartItems.length) {
    setRemainingTime(WAITING_TIME);
    setPrevCartItems(cartItems);
  }

  const openRemoveAllItemsModal = () => {
    setIsClearConfirmModalOpen(true);
  };

  const closeRemoveAllItemsModal = () => {
    setIsClearConfirmModalOpen(false);
  };

  const requestPayment = async (
    inputAmount?: number
  ): Promise<ResponseBody> => {
    const body: PaymentRequestBody = {
      menus: cartItems.map((item) => {
        return {
          id: item.id,
          count: item.count,
          size: item.options.size,
          temperature: item.options.temperature,
        };
      }),
      inputAmount: inputAmount || totalPrice,
      totalPrice,
      paymentType: paymentTypeRef.current!,
    };

    const options = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(body),
    };

    const res = await fetch(`${API_URL}/api/orders`, options);

    const parsedData = await res.json();
    const data = { status: res.status, body: parsedData };

    return data;
  };

  const reducedItems = cartItems.reduce(
    (acc: CartItem[], cartItem: CartItem) => {
      const sameItem = acc.find((item) => item.id === cartItem.id);

      if (sameItem) {
        sameItem.count += cartItem.count;
      } else {
        acc.push({ ...cartItem });
      }

      return acc;
    },
    []
  );

  const totalPrice = cartItems.reduce((acc, cartItem) => {
    return acc + cartItem.price * cartItem.count;
  }, 0);

  const openPaymentSelectionModal = () => {
    setIsPaymentModalOpen(true);
  };

  const closePaymentSelectionModal = () => {
    setIsPaymentModalOpen(false);
  };

  const selectCardPayment = async () => {
    paymentTypeRef.current = PaymentType.CARD;

    const response = await requestPaymentWithIndicator();

    changePage("/result", response);
  };

  const selectCashPayment = () => {
    paymentTypeRef.current = PaymentType.CASH;

    closePaymentSelectionModal();
    setIsCashPaymentModalOpen(true);
  };

  const closeCashPaymentModal = () => {
    setIsCashPaymentModalOpen(false);
  };

  const requestPaymentWithIndicator = async (inputAmount?: number) => {
    setIsIndicatorVisible(true);

    const response = await requestPayment(inputAmount);

    setIsIndicatorVisible(false);

    return response;
  };

  return (
    <section className={styles.Cart}>
      <div className={styles.ItemSection}>
        <div className={styles.ItemInfo}>
          <p>
            주문 수량: {reducedItems.reduce((acc, item) => acc + item.count, 0)}
          </p>
          <p>
            {reducedItems.reduce(
              (acc, item) => acc + item.price * item.count,
              0
            )}
            원
          </p>
        </div>
        <ul className={styles.ItemContainer}>
          {reducedItems.map((item) => (
            <li className={styles.Item} key={item.id}>
              <CartItem {...item} removeItem={removeItem} />
            </li>
          ))}
        </ul>
      </div>
      <div className={styles.ButtonSection}>
        <div className={styles.Timer}>{remainingTime}초 남음</div>
        <button
          className={styles.CancelAllButton}
          onClick={openRemoveAllItemsModal}
        >
          전체 취소
        </button>
        <button
          className={styles.PaymentButton}
          onClick={openPaymentSelectionModal}
        >
          결제하기
        </button>
      </div>
      {isClearConfirmModalOpen && (
        <ClearConfirmModal
          closeModal={closeRemoveAllItemsModal}
          removeAllItems={removeAllItems}
        />
      )}
      {isPaymentModalOpen && (
        <PaymentSelectionModal
          closeModal={closePaymentSelectionModal}
          selectCardPayment={selectCardPayment}
          selectCashPayment={selectCashPayment}
        />
      )}
      {isCashPaymentModalOpen && (
        <CashPaymentModal
          totalPrice={totalPrice}
          requestPaymentWithIndicator={requestPaymentWithIndicator}
          changePage={changePage}
          closeModal={closeCashPaymentModal}
        />
      )}
      {isIndicatorVisible && (
        <Spinner
          content={
            paymentTypeRef.current === PaymentType.CARD
              ? "카드 결제 중입니다..."
              : paymentTypeRef.current === PaymentType.CASH
              ? "현금 결제 중입니다..."
              : ""
          }
        />
      )}
    </section>
  );
}
