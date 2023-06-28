import { useEffect, useState } from "react";
import styles from "./Result.module.css";
import { PaymentType } from "../types/constants";

interface ResultPageProps {
  changePage: (path: Path) => void;
  response: ResponseBody | undefined;
}

export default function Result({ changePage, response }: ResultPageProps) {
  const isSuccess = response?.status === 200;

  return (
    <>
      {isSuccess ? (
        <Receipt changePage={changePage} response={response.body as ReceiptResponseBody} />
      ) : (
        <ErrorPage changePage={changePage} />
      )}
    </>
  );
}

interface ReceiptProps {
  changePage: (path: Path) => void;
  response: ReceiptResponseBody;
}

function Receipt({ changePage, response }: ReceiptProps) {
  const [count, setCount] = useState(10);

  useEffect(() => {
    const timer = setInterval(() => {
      setCount((count) => {
        return count - 1;
      });
    }, 1000);

    return () => clearInterval(timer);
  }, []);

  useEffect(() => {
    if (count === 0) {
      changePage("/");
    }
  }, [count]);

  return (
    <div className={styles.Display}>
      <main className={styles.ReceiptContainer}>
        <h2 className={styles.ReceiptHeader}>
          주문번호 <span className={styles.OrderNumber}>{String(response.orderNumber).padStart(2, "0")}</span>
        </h2>
        <ul className={styles.OrderMenus}>
          {response.menus.map((menu) => {
            return (
              <li key={menu.name}>
                {menu.name} {menu.count}
              </li>
            );
          })}
        </ul>
        <div className={styles.ReceiptInfo}>
          <div>
            결제방식 :{" "}
            {response.paymentType === PaymentType.CASH
              ? "현금"
              : response.paymentType === PaymentType.CARD
              ? "카드"
              : ""}
          </div>
          <div>투입금액 : {response.inputAmount}원</div>
          <div>총 결제금액 : {response.totalPrice}원</div>
          <div>잔돈 : {response.remain}원</div>
        </div>
      </main>
      <div className={styles.ReceiptWarningTextContainer}>
        <div className={styles.ReceiptWarningText}>{"(주의 : 이 화면은 10초 뒤에 자동으로 사라집니다)"}</div>
        <div>{count}초</div>
      </div>
    </div>
  );
}

interface ErrorPageProps {
  changePage: (path: Path) => void;
}

function ErrorPage(props: ErrorPageProps) {
  return <></>;
}
