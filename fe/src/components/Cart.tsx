import { useState } from "react";
import styles from "./Cart.module.css";

interface CartProps {
  cartItems: CartItem[];
  removeItem: (id: number) => void;
  removeAllItems: () => void;
  changePage: (path: Path) => void;
}

export default function Cart({ cartItems, removeItem, removeAllItems, changePage }: CartProps) {
  const [isRemoveAllItemsModalOpen, setIsRemoveAllItemsModalOpen] = useState(false);

  const openRemoveAllItemsModal = () => {
    setIsRemoveAllItemsModalOpen(true);
  };

  const closeRemoveAllItemsModal = () => {
    setIsRemoveAllItemsModalOpen(false);
  };

  // 결제 수단 고르는 모달
  // 카드결제 눌렀을 때 로딩인디케이터 띄우는 함수
  // 현금결제 눌렀을 때 현금결제 모달 띄우는 함수

  const reducedItems = cartItems.reduce((acc: CartItem[], cartItem: CartItem) => {
    const sameItem = acc.find((item) => item.id === cartItem.id);

    if (sameItem) {
      sameItem.count += cartItem.count;
    } else {
      acc.push({...cartItem});
    }

    return acc;
  } , []);

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
        <button className={styles.CancelAllButton} onClick={openRemoveAllItemsModal}>전체 취소</button>
        <button className={styles.PaymentButton}>결제하기</button>
      </div>
      {isRemoveAllItemsModalOpen && (
        <RemoveAllItemsConfirmationModal closeModal={closeRemoveAllItemsModal} removeAllItems={removeAllItems} />
      )}
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
      <button className={styles.RemoveButton}>X</button>
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
        <div>장바구니에 담긴 상품 모두 삭제하시겠습니까?</div>
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

interface ModalProps {
  closeModal: () => void;
  children: JSX.Element;
}

function Modal({ closeModal, children }: ModalProps) {
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
