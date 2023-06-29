import OptionButton, { ButtonStyle } from "./OptionButton";

export type OptionTypeProps = {
  type: ButtonStyle;
  options: {
    text: string;
    isSelected: boolean;
    onClick: () => void;
  }[];
};

export default function OptionType({ type, options }: OptionTypeProps) {
  return (
    <>
      {options.map((option, index) => (
        <OptionButton
          key={index}
          type={type}
          text={option.text}
          isSelected={option.isSelected}
          onClick={option.onClick}
        />
      ))}
    </>
  );
}
