import { ButtonStyle } from "./OptionButton";
import OptionType from "./OptionType";
import QuantityCounter from "./QuantityCounter";
import styles from "./OptionTypes.module.css";

export type OptionTypesProps = {
  optionTypes: {
    type: ButtonStyle;
    options: {
      text: string;
      isSelected: boolean;
      onClick: () => void;
    }[];
  }[];
  counter: {
    quantity: number;
    handlePlusButtonClick: () => void;
    handleMinusButtonClick: () => void;
  };
};

export default function OptionTypes({
  optionTypes,
  counter,
}: OptionTypesProps) {
  return (
    <>
      {optionTypes.map((optionType) => {
        return (
          <div className={styles.MenuItemOption} key={optionType.type}>
            <OptionType type={optionType.type} options={optionType.options} />
          </div>
        );
      })}
      <div className={styles.MenuItemOption}>
        <QuantityCounter
          quantity={counter.quantity}
          handlePlusButtonClick={counter.handlePlusButtonClick}
          handleMinusButtonClick={counter.handleMinusButtonClick}
        />
      </div>
    </>
  );
}
