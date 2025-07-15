type ProductOption = {
  id: number;
  name: string;
};

type ProductOptionValue = {
  id: number;
  name?: string;
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
    productOptionValue: ProductOptionValue
}

export interface CartItemRequest{
    productVariant: {
        id: number
    }
    productOptionValue: {
        id: number
    }
    quantity: number
}

export interface Cart{
    totalItem: number
    items: CartItem[]
}