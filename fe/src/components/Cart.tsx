interface CartProps {
  cartItems: CartItem[];
  removeItem: (id: number) => void;
  removeAllItems: (id: number) => void;
  changePage: (path: Path) => void;
}

export default function Cart({ cartItems, removeItem, removeAllItems, changePage }: CartProps) {
  // 결제 수단 고르는 모달
  // 카드결제 눌렀을 때 로딩인디케이터 띄우는 함수
  // 현금결제 눌렀을 때 현금결제 모달 띄우는 함수
  return (
    <section>
      <div>
        <ul>
          {cartItems.map((cartItem) => (
            <li key={cartItem.id}>
              <CartItem {...cartItem} removeItem={removeItem} />
            </li>
          ))}
        </ul>
      </div>
      <div>
        <div>8초 남음</div>
        <button>전체 취소</button>
        <button>결제하기</button>
      </div>
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
    <>
      <img src={imageSrc} alt={name} />
      <div>{name}</div>
      <div>{price}</div>
      <div>{count}</div>
      <button>X</button>
    </>
  );
}
