import { useEffect, useRef, useState } from "react";
import { API_URL } from "../constants";
import { PaymentType, Size, Temperature } from "../types/constants";
import styles from "./Cart.module.css";
import Modal from "./Modal";
import {
  CashPaymentModal,
  PaymentSelectionModal,
  PaymentSpinner,
} from "./Payment";
import CloseButton from "./CloseButton";

interface CartProps {
  cartItems: CartItem[];
  removeItem: (id: number) => void;
  removeAllItems: () => void;
  changePage: (path: Path, response: ResponseBody) => void;
}

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
  const [isRemoveAllItemsModalOpen, setIsRemoveAllItemsModalOpen] =
    useState(false);
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
      isRemoveAllItemsModalOpen ||
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
    isRemoveAllItemsModalOpen,
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
    setIsRemoveAllItemsModalOpen(true);
  };

  const closeRemoveAllItemsModal = () => {
    setIsRemoveAllItemsModalOpen(false);
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
    setIsIndicatorVisible(true);

    const response = await requestPayment();

    setIsIndicatorVisible(false);
    changePage("/result", response);
  };

  const selectCashPayment = () => {
    closePaymentSelectionModal();
    setIsCashPaymentModalOpen(true);
  };

  const closeCashPaymentModal = () => {
    setIsCashPaymentModalOpen(false);
  };

  return (
    <section className={styles.Cart}>
      <div className={styles.ItemSection}>
        <div className={styles.ItemInfo}>
          <p>주문 수량: 5</p>
          <p>20000원</p>
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
      {isRemoveAllItemsModalOpen && (
        <RemoveAllItemsConfirmationModal
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
          requestPayment={requestPayment}
          changePage={changePage}
          closeModal={closeCashPaymentModal}
        />
      )}
      {isIndicatorVisible && <PaymentSpinner />}
    </section>
  );
}
interface CartItemProps {
  id: number;
  name: string;
  price: number;
  imageSrc: string;
  count: number;
  removeItem: (id: number) => void;
}

function CartItem({
  id,
  name,
  imageSrc,
  count,
  price,
  removeItem,
}: CartItemProps) {
  return (
    <>
      <div className={styles.ItemContent}>
        <img className={styles.ItemImage} src={imageSrc} alt={name} />
        <div>{name}</div>
        <div>{price}</div>
      </div>
      <div className={styles.ItemCount}>{count}</div>
      <CloseButton onClick={() => removeItem(id)} />
    </>
  );
}

interface RemoveAllItemsConfirmationModalProps {
  closeModal: () => void;
  removeAllItems: () => void;
}

function RemoveAllItemsConfirmationModal({
  closeModal,
  removeAllItems,
}: RemoveAllItemsConfirmationModalProps) {
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
