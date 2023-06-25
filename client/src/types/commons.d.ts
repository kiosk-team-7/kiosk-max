type Path = "/" | "/result";

interface Menu {
  id: number;
  name: string;
  price: number;
  isPopular: boolean;
  imageSrc: string;
}

interface CartItem {
  id: number;
  name: string;
  price: number;
  options: {
    size: Size;
    temperature: Temperature;
  };
  count: number;
}
