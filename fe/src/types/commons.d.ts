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

interface ResponseBody {
  status: number;
  body: ReceiptResponseBody | ErrorResponseBody;
}

interface ReceiptResponseBody {
  orderNumber: number;
  menus: { name: string; count: number }[];
  paymentType: number;
  inputAmount: number;
  totalPrice: number;
  remain: number;
}

interface ErrorResponseBody {
  message: string;
  statusCode: number;
}
