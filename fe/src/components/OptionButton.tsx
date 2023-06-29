import styles from "./OptionButton.module.css";

type OptionButtonProps = {
  type: "Size" | "Temperature" | "Payment" | "CashInput";
  text: string;
  isSelected?: boolean;
  onClick: () => void;
};

export default function OptionButton({
  type,
  text,
  isSelected,
  onClick,
}: OptionButtonProps) {
  let className;
  switch (type) {
    case "Size":
      className = styles.Size;
      break;
    case "Temperature":
      className = styles.Temperature;
      break;
    case "Payment":
      className = styles.Payment;
      break;
    case "CashInput":
      className = styles.CashInput;
      break;
  }

  if (isSelected) {
    className += " " + styles.Selected;
  }

  return (
    <div className={className} onClick={onClick}>
      <span className={styles.Text}>{text}</span>
    </div>
  );
}
