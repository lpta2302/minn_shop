import { useQuery } from "@tanstack/react-query";
import { getAll } from "../api/crud";
import { GET_ALL_DISPLAYED_PRODUCT_VARIANTS, PRODUCT_WITH_FULL_VARIANTS_BY_ID } from "../keys/productKeys";
import type { Product } from "@/types/product";


// ----- PRODUCT -----
export function useGetProductWithFullVariantsById(productId: number){
    return useQuery<Product>({
        queryKey: [PRODUCT_WITH_FULL_VARIANTS_BY_ID, productId],
        queryFn: ()=>getAll('/products/'+productId),
        enabled: !!productId
    })
}

// ----- PRODUCT VARIANT -----
export function useGetAllDisplayedProductVariants() {
    return useQuery({
        queryKey: [GET_ALL_DISPLAYED_PRODUCT_VARIANTS],
        queryFn: ()=>getAll('/product-variants'),
    })
}