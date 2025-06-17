export interface ProductVariant {
    id: number;
    variantId: string;
    slug: string;
    name: string;
    price: number;
    originalPrice: number;
    discount: number;
    soldQuantity: number;
    productVariantImages: ProductVariantImage[];
    status: 'ACTIVE' | 'INACTIVE' | 'DRAFT' | 'OUT_OF_STOCK' | 'ARRIVING';
}

export interface ProductVariantImage {
    id: number
    fileId: number
    position: number
    key: string
    name: string
    url: string
    isThumbnail: boolean
}