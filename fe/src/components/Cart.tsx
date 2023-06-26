import { useState } from "react";
import { PaymentSelectionModal } from "./Payment";
import styles from "./Cart.module.css";
import Modal from "./Modal";

interface CartProps {
  cartItems: CartItem[];
  removeItem: (id: number) => void;
  removeAllItems: () => void;
  changePage: (path: Path) => void;
}

export default function Cart({ cartItems, removeItem, removeAllItems, changePage }: CartProps) {
  const [isPaymentModalOpen, setIsPaymentModalOpen] = useState(false);
  const [isRemoveAllItemsModalOpen, setIsRemoveAllItemsModalOpen] = useState(false);

  const openRemoveAllItemsModal = () => {
    setIsRemoveAllItemsModalOpen(true);
  };

  const closeRemoveAllItemsModal = () => {
    setIsRemoveAllItemsModalOpen(false);
  };

  // 카드결제 눌렀을 때 로딩인디케이터 띄우는 함수
  // 현금결제 눌렀을 때 현금결제 모달 띄우는 함수

  const openPaymentSelectionModal = () => {
    setIsPaymentModalOpen(true);
  };

  const closePaymentSelectionModal = () => {
    setIsPaymentModalOpen(false);
  };

  const selectCardPayment = () => {};

  const selectCashPayment = () => {};

  return (
    <>
      {isPaymentModalOpen && (
        <PaymentSelectionModal
          closeModal={closePaymentSelectionModal}
          selectCardPayment={selectCardPayment}
          selectCashPayment={selectCashPayment}
        />
      )}
      {isRemoveAllItemsModalOpen && (
        <RemoveAllItemsConfirmationModal closeModal={closeRemoveAllItemsModal} removeAllItems={removeAllItems} />
      )}
    </>
  );
}

interface CartItemProps {
  id: number;
  name: string;
  imageSrc: string;
  count: number;
  price: number;
  removeItem: (id: number) => void;
}

function CartItem({ id, name, imageSrc, count, price, removeItem }: CartItemProps) {
  // return (
  //   <div>
  //     <img src="" alt="" />
  //     <div>{item.name}</div>
  //     <div>{item.count}</div>
  //   </div>
  // );
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
