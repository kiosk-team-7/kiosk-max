import CloseButton from "../../CloseButton";
import styles from "./CartItem.module.css";

type CartItemProps = {
  id: number;
  name: string;
  price: number;
  imageSrc: string;
  count: number;
  removeItem: (id: number) => void;
};

export default function CartItem({
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
