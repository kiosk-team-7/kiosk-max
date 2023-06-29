import Button from "../Button";
import styles from "./OptionButton.module.css";

type OptionButtonProps = {
  type: ButtonStyle;
  text: string;
  isSelected?: boolean;
  onClick: () => void;
};

export enum ButtonStyle {
  SIZE,
  TEMPERATURE,
  PAYMENT,
  CASH_INPUT,
}

export default function OptionButton({
  type,
  text,
  isSelected,
  onClick,
}: OptionButtonProps) {
  let className;

  switch (type) {
    case ButtonStyle.SIZE:
      className = styles.Size;
      break;
    case ButtonStyle.TEMPERATURE:
      className = styles.Temperature;
      break;
    case ButtonStyle.PAYMENT:
      className = styles.Payment;
      break;
    case ButtonStyle.CASH_INPUT:
      className = styles.CashInput;
      break;
  }

  if (isSelected) {
    className += " " + styles.Selected;
  }

  return <Button className={className} text={text} onClick={onClick} />;
}
