import { useState } from "react";
import { PaymentSelectionModal } from "./Payment";

interface CartProps {
  cartItems: CartItem[];
  removeItem: (id: number) => void;
  removeAllItems: (id: number) => void;
  changePage: (path: Path) => void;
}

export default function Cart({ cartItems, removeItem, removeAllItems, changePage }: CartProps) {
  const [isPaymentModalOpen, setIsPaymentModalOpen] = useState(true);

  // 결제 수단 고르는 모달
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
