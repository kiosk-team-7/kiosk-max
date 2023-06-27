import { useRef, useState } from "react";
import { PaymentSelectionModal, PaymentSpinner } from "./Payment";
import styles from "./Cart.module.css";
import Modal from "./Modal";
import { API_URL } from "../constants";
import { PaymentType, Size, Temperature } from "../types/constants";

interface CartProps {
  cartItems: CartItem[];
  removeItem: (id: number) => void;
  removeAllItems: () => void;
  changePage: (path: Path) => void;
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

export default function Cart({ cartItems, removeItem, removeAllItems, changePage }: CartProps) {
  const [isPaymentModalOpen, setIsPaymentModalOpen] = useState(false);
  const [isIndicatorVisible, setIsIndicatorVisible] = useState(false);
  const [isRemoveAllItemsModalOpen, setIsRemoveAllItemsModalOpen] = useState(false);
  const paymentTypeRef = useRef<PaymentType>();

  const openRemoveAllItemsModal = () => {
    setIsRemoveAllItemsModalOpen(true);
  };

  const closeRemoveAllItemsModal = () => {
    setIsRemoveAllItemsModalOpen(false);
  };

  const requestPayment = async (inputAmount?: number) => {
    const totalPrice = cartItems.reduce((acc, item) => acc + item.price * item.count, 0);

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
      totalPrice: totalPrice,
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
    const data = await res.json();

    return data;
  };

  const reducedItems = cartItems.reduce((acc: CartItem[], cartItem: CartItem) => {
    const sameItem = acc.find((item) => item.id === cartItem.id);

    if (sameItem) {
      sameItem.count += cartItem.count;
    } else {
      acc.push({ ...cartItem });
    }

    return acc;
  }, []);

  const openPaymentSelectionModal = () => {
    setIsPaymentModalOpen(true);
  };

  const closePaymentSelectionModal = () => {
    setIsPaymentModalOpen(false);
  };

  const selectCardPayment = () => {
    paymentTypeRef.current = PaymentType.CARD;
    setIsIndicatorVisible(true);
  };

  const selectCashPayment = () => {};

  return (
    <section className={styles.Cart}>
      <div className={styles.ItemSection}>
        <ul className={styles.ItemContainer}>
          {reducedItems.map((item) => (
            <li className={styles.Item} key={item.id}>
              <CartItem {...item} removeItem={removeItem} />
            </li>
          ))}
        </ul>
      </div>
      <div className={styles.ButtonSection}>
        <div className={styles.Timer}>8초 남음</div>
        <button className={styles.CancelAllButton} onClick={openRemoveAllItemsModal}>
          전체 취소
        </button>
        <button className={styles.PaymentButton} onClick={openPaymentSelectionModal}>
          결제하기
        </button>
      </div>
      {isRemoveAllItemsModalOpen && (
        <RemoveAllItemsConfirmationModal closeModal={closeRemoveAllItemsModal} removeAllItems={removeAllItems} />
      )}
      {isPaymentModalOpen && (
        <PaymentSelectionModal
          closeModal={closePaymentSelectionModal}
          selectCardPayment={selectCardPayment}
          selectCashPayment={selectCashPayment}
        />
      )}
      {isIndicatorVisible && <PaymentSpinner requestPayment={requestPayment} />}
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

function CartItem({ id, name, imageSrc, count, price, removeItem }: CartItemProps) {
  return (
    <div className={styles.ItemContent}>
      <img className={styles.ItemImage} src={imageSrc} alt={name} />
      <div>{name}</div>
      <div>{price}</div>
      <div className={styles.ItemCount}>{count}</div>
      <button className={styles.RemoveButton} onClick={() => removeItem(id)}>
        X
      </button>
    </div>
  );
}

interface RemoveAllItemsConfirmationModalProps {
  closeModal: () => void;
  removeAllItems: () => void;
}

function RemoveAllItemsConfirmationModal({ closeModal, removeAllItems }: RemoveAllItemsConfirmationModalProps) {
  const handleConfirmButtonClick = () => {
    removeAllItems();
    closeModal();
  };

  return (
    <Modal closeModal={closeModal}>
      <>
        <div className={styles.ModalContent}>장바구니에 담긴 상품 모두 삭제하시겠습니까?</div>
        <div className={styles.ButtonContainer}>
          <button className={styles.ConfirmButton} onClick={handleConfirmButtonClick}>
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
