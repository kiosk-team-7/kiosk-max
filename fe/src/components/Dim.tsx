import styles from "./Dim.module.css";

type DimProps = {
  children: React.ReactNode;
  onClick?: () => void;
};

export default function Dim({ children, onClick }: DimProps) {
  return (
    <div className={styles.DimContainer}>
      <div className={styles.Dim} onClick={onClick}></div>
      {children}
    </div>
  );
}
