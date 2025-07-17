type ProductOption = {
  id: number;
  name: string;
};

type ProductOptionValue = {
  id: number;
  name: string;
  availableStock: number;
};

type ProductVariant = {
  id: number;
  variantId: string;
  slug: string;
  name: string;
  finalPrice: number;
  thumbnail: string | undefined;
  productOption: ProductOption;
  status?: 'ACTIVE' | 'INACTIVE';
};

export interface CartItem {
    id: number,
    quantity: number,
    productVariant: ProductVariant
    stockOptionValue: ProductOptionValue
    isSelected?: boolean
}

export interface CartItemRequest{
    id?: number,
    productVariant: {
        id: number
    }
    stockOptionValue: {
        id: number
    }
    quantity: number
}

export interface Cart{
    totalItem: number
    items: CartItem[]
}