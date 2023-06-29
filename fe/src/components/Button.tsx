import styles from "./Button.module.css";

type buttonProps = {
  className?: string;
  text: string;
  onClick: () => void;
};

export default function Button({ className, text, onClick }: buttonProps) {
  return (
    <div className={className} onClick={onClick}>
      <span className={styles.Text}>{text}</span>
    </div>
  );
}
