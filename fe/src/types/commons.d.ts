type Path = "/" | "/result";

interface Menu {
  id: number;
  categoryId: number;
  name: string;
  price: number;
  imageSrc: string;
  isPopular: boolean;
}

interface CartItem {
  id: number;
  name: string;
  price: number;
  imageSrc: string;
  options: {
    size: Size;
    temperature: Temperature;
  };
  count: number;
}
