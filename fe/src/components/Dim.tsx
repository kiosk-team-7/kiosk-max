import styles from "./Dim.module.css";

interface DimProps {
  children: JSX.Element;
  onClick?: () => void;
}

export default function Dim({ children, onClick }: DimProps) {
  return (
    <div className={styles.DimContainer}>
      <div className={styles.Dim} onClick={onClick}>
        {children}
      </div>
    </div>
  );
}
