import styles from "./QuantityCounter.module.css";

type QuantityCounterProps = {
  quantity: number;
  handlePlusButtonClick: () => void;
  handleMinusButtonClick: () => void;
};

export default function QuantityCounter({
  quantity,
  handlePlusButtonClick,
  handleMinusButtonClick,
}: QuantityCounterProps) {
  return (
    <div className={styles.CounterContainer}>
      <button
        className={styles.CounterController}
        onClick={handleMinusButtonClick}
      >
        -
      </button>
      <span className={styles.Counter}>{quantity}</span>
      <button
        className={styles.CounterController}
        onClick={handlePlusButtonClick}
      >
        +
      </button>
    </div>
  );
}
