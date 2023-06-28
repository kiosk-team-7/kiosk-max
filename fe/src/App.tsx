import { useRef, useState } from "react";
import Home from "./components/Home";
import Result from "./components/Result";

export default function App() {
  const [currentPath, setCurrentPath] = useState(window.location.pathname);
  const response = useRef<ResponseBody>();

  const changePage = (path: Path, res?: ResponseBody) => {
    setCurrentPath(path);
    response.current = res;
  };

  const renderPage = () => {
    switch (currentPath) {
      case "/":
        return <Home changePage={changePage} />;
      case "/result":
        return <Result changePage={changePage} response={response.current} />;
      default:
        return <Home changePage={changePage} />;
    }
  };

  return <>{renderPage()}</>;
}
