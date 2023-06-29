import Dim from "./Dim";
import styles from "./Spinner.module.css";

export function Spinner({ content }: { content: string }) {
  return (
    <Dim>
      <div className={styles.Spinner}></div>
      <div className={styles.SpinnerContent}>{content}</div>
    </Dim>
  );
}
