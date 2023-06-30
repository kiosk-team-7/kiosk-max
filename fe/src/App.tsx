import { useEffect, useRef, useState } from "react";
import Home from "./Pages/Home";
import Result from "./Pages/Result";

export default function App() {
  const [currentPath, setCurrentPath] = useState(window.location.pathname);
  const response = useRef<ResponseBody>();

  useEffect(() => {
    const handlePopState = () => {
      setCurrentPath(window.location.pathname);
    };

    window.addEventListener("popstate", handlePopState);
    return () => {
      window.removeEventListener("popstate", handlePopState);
    };
  }, []);

  const changePage = (path: Path, res?: ResponseBody) => {
    setCurrentPath(path);
    window.history.pushState(null, "", path);
    response.current = res;
  };

  return (
    <RenderPage
      currentPath={currentPath}
      response={response.current}
      changePage={changePage}
    />
  );
}

type RenderPageProps = {
  currentPath: string;
  response: ResponseBody | undefined;
  changePage: (path: Path, res?: ResponseBody) => void;
};

function RenderPage({ currentPath, response, changePage }: RenderPageProps) {
  switch (currentPath) {
    case "/":
      return <Home changePage={changePage} />;
    case "/result":
      return <Result response={response} changePage={changePage} />;
    default:
      return <Home changePage={changePage} />;
  }
}
