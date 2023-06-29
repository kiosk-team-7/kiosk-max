import styles from "./CloseButton.module.css";

export default function CloseButton({ onClick }: { onClick: () => void }) {
  return <div className={styles.CloseButton} onClick={onClick}></div>;
}
