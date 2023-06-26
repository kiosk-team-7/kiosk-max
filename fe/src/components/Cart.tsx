import styles from './Cart.module.css';

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
        <button className={styles.CancelAllButton}>전체 취소</button>
        <button className={styles.PaymentButton}>결제하기</button>
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
    <div className={styles.ItemContent}>
      <img className={styles.ItemImage} src={imageSrc} alt={name} />
      <div>{name}</div>
      <div>{price}</div>
      <div className={styles.ItemCount}>{count}</div>
      <button className={styles.CloseButton}>X</button>
    </div>
  );
}
